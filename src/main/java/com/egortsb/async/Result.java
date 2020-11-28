package com.egortsb.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Represents result of asynchronous operation. Wrapper for Future class
 * @param <T>
 */
public class Result<T> {

    private Future<T>future;

    public Result(Future<T> future) {
        this.future = future;
    }

    /**
     * Get result of operation. Blocking
     * @return
     */
    public T get(){
        try {
            return future.get();
        } catch (InterruptedException| ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Is operation completed
     * @return
     */
    public boolean isReady(){
        return future.isDone();
    }

}
