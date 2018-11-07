package com.payments.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockClassRule extends com.github.tomakehurst.wiremock.junit.WireMockClassRule {

    public WireMockClassRule(Options options) {
        super(options);
    }

    public WireMockClassRule(int port, Integer httpsPort) {
        this(wireMockConfig().port(port).httpsPort(httpsPort));
    }

    public WireMockClassRule(int port) {
        this(wireMockConfig().port(port));
    }

    public WireMockClassRule() {
        this(wireMockConfig());
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                if (!isRunning()) {
                    start();
                    WireMock.configureFor("localhost", port());
                }

                try {
                    before();
                    base.evaluate();
                } finally {
                    after();
                    client.resetMappings();
                }
            }
        };
    }
}