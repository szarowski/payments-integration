package com.payments.config;

import com.payments.feign.FeignContractThatDoesntDecodeSlashes;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Feign.class)
public class FeignConfig {

    @Bean
    public FeignContractThatDoesntDecodeSlashes feignContractThatDoesntDecodeSlashes() {
        return new FeignContractThatDoesntDecodeSlashes();
    }
}
