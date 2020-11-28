package com.egortsb.async;


import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Trivial library to run asynchronous task. Wraps ScheduledExecutorService
 */
public class Async {

    ScheduledExecutorService executorService;

    private static final int NUMBER_OF_THREADS = 2;

    /**
     * Creates Async with thread pool
     * @param numberOfThreads - size of thread pool
     */
    public Async(int numberOfThreads){
        executorService = Executors.newScheduledThreadPool(numberOfThreads);
    }

    /**
     * Default constructor. Thread pool will be created with default capacity
     */
    public Async(){
        executorService = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);
    }

    /**
     * Submit task for execution
     * @param r - task to run
     */
    public void submit(Runnable r){
       executorService.submit(r);
    }

    /**
     * Submit task for execution with timeout. task will be executed after timeout.
     * @param r - task to execute
     * @param delay - timeout
     */
    public void submitWithTimeout(Runnable r, long delay){
        executorService.schedule(r, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Submit task for periodical execution with interval
     * @param r
     * @param delay
     */
    public void submitWithInterval(Runnable r, long delay){
        executorService.scheduleAtFixedRate(r, delay, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Submit task for execution
     * @param c - Callable task to execute
     * @param <T> - type of the result
     * @return - Result object to get result of execution
     */
    public <T> Result<T> submit(Callable<T> c){
        return  new Result(executorService.submit(c));
    }

    /**
     * Submit task for execution with timeout. task will be executed after timeout.
     * @param c - Callable task to execute
     * @param delay - delay
     * @param <T> - type of the result
     * @return - Result object to get result of execution
     */
    public <T> Result<T> submitWithTimeout(Callable<T> c, long delay){
        return new Result<>(executorService.schedule(c, delay, TimeUnit.MILLISECONDS));
    }

    public void shutdown(){
        executorService.shutdown();
    }

}
