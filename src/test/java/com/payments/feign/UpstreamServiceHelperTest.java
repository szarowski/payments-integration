package com.payments.feign;

import com.payments.util.Random;
import org.junit.Test;

import java.util.concurrent.Callable;

import static com.payments.feign.UpstreamServiceHelper.withUpstreamErrorHandling;
import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UpstreamServiceHelperTest {

    @Test
    public void shouldReturnResultOfCallableIfNoExceptionIsThrown() {
        int result = Random.intVal();
        Callable<Integer> callable = () -> result;

        assertThat(withUpstreamErrorHandling(callable)).isEqualTo(result);
    }

    @Test
    public void shouldRethrowUpstreamServiceExceptionForCallable() {
        UpstreamServiceException upstreamError = new UpstreamServiceException(Random.string(), 400, Random.string(), emptyMap());
        Callable<Void> upstreamErrorThrowingCallable = () -> { throw upstreamError; };

        assertThatThrownBy(() -> withUpstreamErrorHandling(upstreamErrorThrowingCallable))
                .isEqualTo(upstreamError);
    }

    @Test
    public void shouldRethrowWrappedUpstreamServiceExceptionForCallable() {
        UpstreamServiceException upstreamError = new UpstreamServiceException(Random.string(), 400, Random.string(), emptyMap());
        Callable<Void> upstreamErrorThrowingCallable = () -> { throw new RuntimeException(upstreamError); };

        assertThatThrownBy(() -> withUpstreamErrorHandling(upstreamErrorThrowingCallable))
                .isEqualTo(upstreamError);
    }

    @Test
    public void shouldWrapOtherExceptionsInRuntimeExceptionForCallable() {
        IllegalStateException exception = new IllegalStateException(Random.string());
        Callable<Void> errorThrowingCallable = () -> { throw exception; };

        assertThatThrownBy(() -> withUpstreamErrorHandling(errorThrowingCallable))
                .isInstanceOf(RuntimeException.class)
                .hasCause(exception);
    }

    @Test
    public void shouldRethrowUpstreamServiceExceptionForRunnable() {
        UpstreamServiceException upstreamError = new UpstreamServiceException(Random.string(), 400, Random.string(), emptyMap());
        Runnable upstreamErrorThrowingCallable = () -> { throw upstreamError; };

        assertThatThrownBy(() -> withUpstreamErrorHandling(upstreamErrorThrowingCallable))
                .isEqualTo(upstreamError);
    }

    @Test
    public void shouldRethrowWrappedUpstreamServiceExceptionForRunnable() {
        UpstreamServiceException upstreamError = new UpstreamServiceException(Random.string(), 400, Random.string(), emptyMap());
        Runnable upstreamErrorThrowingCallable = () -> { throw new RuntimeException(upstreamError); };

        assertThatThrownBy(() -> withUpstreamErrorHandling(upstreamErrorThrowingCallable))
                .isEqualTo(upstreamError);
    }

    @Test
    public void shouldWrapOtherExceptionsInRuntimeExceptionForRunnable() {
        IllegalStateException exception = new IllegalStateException(Random.string());
        Runnable errorThrowingCallable = () -> { throw exception; };

        assertThatThrownBy(() -> withUpstreamErrorHandling(errorThrowingCallable))
                .isInstanceOf(RuntimeException.class)
                .hasCause(exception);
    }
}