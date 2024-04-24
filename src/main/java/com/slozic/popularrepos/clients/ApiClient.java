package com.slozic.popularrepos.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.web.client.RestClient;

public abstract class ApiClient {

    protected final RestClient restClient;
    protected final ObjectMapper objectMapper;

    public ApiClient(final RestClient.Builder builder) {
        restClient = builder.build();
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }
}
