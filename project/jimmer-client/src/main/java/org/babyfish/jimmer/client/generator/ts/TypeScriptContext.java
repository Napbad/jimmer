package org.babyfish.jimmer.client.generator.ts;

import org.babyfish.jimmer.client.generator.SourceWriter;
import org.babyfish.jimmer.client.generator.Context;
import org.babyfish.jimmer.client.runtime.Metadata;
import org.babyfish.jimmer.client.source.Source;
import org.babyfish.jimmer.client.source.SourceManager;

public class TypeScriptContext extends Context {

    private final boolean isMutable;

    private final String apiName;

    public TypeScriptContext(Metadata metadata) {
        this(metadata, "    ", false, null);
    }

    public TypeScriptContext(Metadata metadata, String indent, boolean isMutable, String apiName) {
        super(metadata, indent);
        if (!metadata.isGenericSupported()) {
            throw new IllegalArgumentException(
                    "TypeScriptContext only accept metadata which support generic"
            );
        }
        this.isMutable = isMutable;
        this.apiName = apiName != null && !apiName.isEmpty() ? apiName : "Api";
    }

    public boolean isMutable() {
        return isMutable;
    }

    public String getApiName() {
        return apiName;
    }

    @Override
    protected SourceManager createSourceManager() {
        return new TypeScriptSourceManager(this);
    }

    @Override
    protected SourceWriter createCodeWriter(Context context, Source source) {
        return new TypeScriptWriter(context, source);
    }

    @Override
    protected boolean isIndexRequired() {
        return true;
    }

    @Override
    protected String getFileExtension() {
        return "ts";
    }
}
