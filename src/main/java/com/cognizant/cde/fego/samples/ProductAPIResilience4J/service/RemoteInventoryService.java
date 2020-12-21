package com.cognizant.cde.fego.samples.ProductAPIResilience4J.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RemoteInventoryService {
    @Autowired
    private RestTemplate restTemplate;

    public RemoteInventoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isInventoryAvailable(Long productId) {
        int inventoryCnt = restTemplate.getForObject("https://remoteInventoryService.com", Integer.class);
        return inventoryCnt > 0;
    }
}
