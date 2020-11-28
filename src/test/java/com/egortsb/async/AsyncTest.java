package com.egortsb.async;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AsyncTest {

    private Async async = new Async(10);

    @Spy
    private DummyService dummyService = new DummyService();

    @Test
    public void shouldInvoke(){
        async.submit(() -> dummyService.perform());

        verify(dummyService, times(1)).perform();
    }

    @Test
    public void shouldInvokeWithResult() throws Exception{
        Result<String> f =  async.submit(() -> dummyService.retrieve());

        assertThat(f.get()).isEqualTo("result");
    }

    @Test
    public void shouldSetInterval() throws Exception{
        async.submitWithInterval(()-> dummyService.perform(), 10L);

        Thread.sleep(100);
        verify(dummyService, atLeast(5)).perform();
    }

    @Test
    public void shouldSetTimeout() throws Exception{
        async.submitWithTimeout(()-> dummyService.perform(), 50L);

        verifyZeroInteractions(dummyService);

        Thread.sleep(100);

        verify(dummyService, times(1)).perform();
    }

    @Test
    public void shouldSetTimeoutAndReturn() throws Exception{
        Result<String> result = async.submitWithTimeout(()-> dummyService.retrieve(), 50L);

        verifyZeroInteractions(dummyService);

        Thread.sleep(100);

        verify(dummyService, times(1)).retrieve();
        assertThat(result.get()).isEqualTo("result");

    }

    @Test
    public void testThroughput(){

        long start = System.currentTimeMillis();
        for(int i = 0; i<1000; i++){
            Result<String> f =  async.submit(() -> dummyService.retrieve());

            assertThat(f.get()).isEqualTo("result");
        }

        long taken = System.currentTimeMillis() - start;
        assertThat(taken < 1000);
    }





}
