package org.jdbi.v3.sqlobject.internal;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BatchableObject
{
    private final AtomicBoolean inBatch = new AtomicBoolean();
    private final AtomicReference<PreparedBatch> currentBatch = new AtomicReference<>();

    public void beginBatch()
    {
        if (inBatch.getAndSet(true)) {
            throw new IllegalStateException("Already in batch");
        }
        currentBatch.set(null);
    }

    public void setSql(Handle handle, String sql)
    {
        if (!inBatch.get()) {
            throw new IllegalStateException("Not in batch");
        }
    }

    public void endBatch()
    {
        if (!inBatch.getAndSet(false)) {
            throw new IllegalStateException("Not in batch");
        }

        PreparedBatch batch = currentBatch.getAndSet(null);
        if (batch == null) {
            return; // no statements for batch
        }
        batch.execute();
    }
}
