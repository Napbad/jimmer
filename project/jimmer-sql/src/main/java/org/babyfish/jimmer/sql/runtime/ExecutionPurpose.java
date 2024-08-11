package org.babyfish.jimmer.sql.runtime;

import org.babyfish.jimmer.sql.ast.impl.mutation.save.QueryReason;

public interface ExecutionPurpose {

    ExecutionPurpose QUERY = new SimpleExecutionPurpose(Type.QUERY);
    ExecutionPurpose UPDATE = new SimpleExecutionPurpose(Type.UPDATE);
    ExecutionPurpose DELETE = new SimpleExecutionPurpose(Type.DELETE);
    ExecutionPurpose LOAD = new SimpleExecutionPurpose(Type.LOAD);
    ExecutionPurpose EXPORT = new SimpleExecutionPurpose(Type.EXPORT);
    ExecutionPurpose MUTATE = new SimpleExecutionPurpose(Type.MUTATE);
    ExecutionPurpose EVICT = new SimpleExecutionPurpose(Type.EVICT);

    static Command command(QueryReason queryReason) {
        return new CommandExecutionPurpose(queryReason);
    }

    Type getType();

    interface Command extends ExecutionPurpose {
        QueryReason getQueryReason();
    }

    enum Type {
        QUERY,
        UPDATE,
        DELETE,
        LOAD,
        EXPORT,
        MUTATE,
        EVICT,
        COMMAND
    }
}

class SimpleExecutionPurpose implements ExecutionPurpose {

    private final Type type;

    SimpleExecutionPurpose(Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.name();
    }
}

class CommandExecutionPurpose implements ExecutionPurpose.Command {

    private final QueryReason queryReason;

    CommandExecutionPurpose(QueryReason queryReason) {
        this.queryReason = queryReason;
    }

    @Override
    public Type getType() {
        return Type.COMMAND;
    }

    public QueryReason getQueryReason() {
        return queryReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandExecutionPurpose cep = (CommandExecutionPurpose) o;
        return queryReason == cep.queryReason;
    }

    @Override
    public int hashCode() {
        return queryReason.hashCode();
    }

    @Override
    public String toString() {
        return "Command:" + queryReason.name();
    }
}
