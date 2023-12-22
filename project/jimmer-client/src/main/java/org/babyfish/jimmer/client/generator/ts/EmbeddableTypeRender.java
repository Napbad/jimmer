package org.babyfish.jimmer.client.generator.ts;

import org.babyfish.jimmer.client.generator.SourceWriter;
import org.babyfish.jimmer.client.generator.Render;
import org.babyfish.jimmer.client.runtime.NullableType;
import org.babyfish.jimmer.client.runtime.ObjectType;
import org.babyfish.jimmer.client.runtime.Property;

public class EmbeddableTypeRender implements Render {

    private final String name;

    private final ObjectType type;

    public EmbeddableTypeRender(String name, ObjectType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public void export(SourceWriter writer) {
        writer.code("export type {").code(name).code("} from './").code(name).code("';\n");
    }

    @Override
    public void render(SourceWriter writer) {
        TypeScriptContext ctx = writer.getContext();
        writer.code("export interface ").code(name).code(' ');
        writer.scope(SourceWriter.ScopeType.OBJECT, "", true, () -> {
            for (Property property : type.getProperties().values()) {
                DocUtils.doc(property, type.getDoc(), writer);
                writer
                        .codeIf(!ctx.isMutable(), "readonly ")
                        .code(property.getName())
                        .codeIf(property.getType() instanceof NullableType, '?')
                        .code(": ")
                        .typeRef(property.getType())
                        .code("\n");
            }
        });
        writer.code('\n');
    }
}
