package org.jdbi.v3.sqlobject;

/**
 * A mixin interface to expose batching methods on the SQL object.
 */
public interface Batchable
{
    /**
     * Begin a SQL batch that will send multiple statements to the database
     * at once. After this is called, a single
     * {@link org.jdbi.v3.sqlobject.statement.SqlUpdate @SqlUpdate}
     * method may be invoked one or more times to add statements to the batch.
     * The batch should then be completed with {@link #endBatch()}.
     */
    void beginBatch();

    /**
     * End a batch that was started with {@link #beginBatch()}.
     */
    void endBatch();
}
