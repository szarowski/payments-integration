package com.payments.controller;


import com.payments.config.TestRestConfig;
import com.payments.model.internal.UserData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.payments.util.IntegrationTestHelper.apiUrl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestRestConfig.class)
public class UserControllerITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldGetUsers() {
        List<UUID> userIds = jdbcTemplate.queryForList("SELECT id FROM users", UUID.class);

        ResponseEntity<List<UserData>> response = restTemplate.exchange(
                RequestEntity.get(apiUrl("/v1/users", port)).header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE).build(),
                new ParameterizedTypeReference<List<UserData>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting("id")
                .containsExactlyInAnyOrder(userIds.get(0), userIds.get(1));
        int dataCount  = jdbcTemplate.queryForObject("SELECT count(*) FROM users", int.class);
        assertThat(dataCount).isEqualTo(2);
    }
}
