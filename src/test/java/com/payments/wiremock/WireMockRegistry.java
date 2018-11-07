package com.payments.wiremock;

import java.util.HashMap;
import java.util.Map;

public class WireMockRegistry {
    private static final Map<Integer, WireMockClassRule> registry = new HashMap<>();

    public static WireMockClassRule wireMockClassRule(final int port) {
        if (!registry.containsKey(port)) {
            final WireMockClassRule rule = new WireMockClassRule(port) {
                @Override
                public void stop() {
                    // noop
                }
            };
            Runtime.getRuntime().addShutdownHook(new Thread(rule::stop));
            registry.put(port, rule);
        }
        return registry.get(port);
    }
}
