package com.payments.feign;

import java.util.concurrent.Callable;

public class UpstreamServiceHelper {

    public static <T> T withUpstreamErrorHandling(final Callable<T> closure) {
        try {
            return closure.call();
        } catch (UpstreamServiceException use) {
            throw use;
        } catch (Exception e) {
            if (e.getCause() instanceof UpstreamServiceException) {
                throw (UpstreamServiceException) e.getCause();
            }
            throw new RuntimeException(e);
        }
    }

    public static void withUpstreamErrorHandling(final Runnable closure) {
        try {
            closure.run();
        } catch (UpstreamServiceException use) {
            throw use;
        } catch (Exception e) {
            if (e.getCause() instanceof UpstreamServiceException) {
                throw (UpstreamServiceException) e.getCause();
            }
            throw new RuntimeException(e);
        }
    }
}
