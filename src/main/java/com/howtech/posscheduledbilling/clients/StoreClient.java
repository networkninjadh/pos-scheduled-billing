package com.howtech.posscheduledbilling.clients;

import java.util.Arrays;
import java.util.List;

import com.howtech.posscheduledbilling.models.Store;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StoreClient {

    private RestTemplate restTemplate;

    private String URL = "http://localhost:8084";
    private String FIND_ALL = "/store-api/stores";
    private String STORE = "/store-api/store/";

    public StoreClient() {
        restTemplate = new RestTemplate();
    }

    public List<Store> getAll() {
        ResponseEntity<Store[]> response = restTemplate
                .getForEntity(URL + FIND_ALL, Store[].class);
        return Arrays.asList(response.getBody());
    }

    public Store getById(Long storeId) {
        ResponseEntity<Store> response = restTemplate
                .getForEntity(URL + STORE + storeId, Store.class);
        return response.getBody();

    }
}
