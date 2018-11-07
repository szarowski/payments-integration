package com.payments.feign;

import feign.MethodMetadata;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class FeignContractThatDoesntDecodeSlashes extends SpringMvcContract {

    @Override
    protected void processAnnotationOnMethod(final MethodMetadata data, final Annotation methodAnnotation, final Method method) {
        super.processAnnotationOnMethod(data, methodAnnotation, method);
        data.template().decodeSlash(false);
    }
}