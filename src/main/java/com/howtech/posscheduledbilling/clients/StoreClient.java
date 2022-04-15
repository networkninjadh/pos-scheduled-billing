package com.howtech.posscheduledbilling.clients;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.howtech.posscheduledbilling.models.Store;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StoreClient {

    private final RestTemplate restTemplate;

    private final String URL = "http://localhost:8084";

    public StoreClient() {
        restTemplate = new RestTemplate();
    }

    public List<Store> getAll() {
        String FIND_ALL = "/store-api/stores";
        ResponseEntity<Store[]> response = restTemplate
                .getForEntity(URL + FIND_ALL, Store[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public Store getById(Long storeId) {
        String STORE = "/store-api/store/";
        ResponseEntity<Store> response = restTemplate
                .getForEntity(URL + STORE + storeId, Store.class);
        return response.getBody();

    }
}
