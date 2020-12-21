package com.cognizant.cde.fego.samples.ProductAPIResilience4J.service;

import com.cognizant.cde.fego.samples.ProductAPIResilience4J.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    private List<Product> products = new ArrayList() {{
        add(new Product(1L, "Barbie Doll", "toys"));
        add(new Product(2L, "Hulk Figure", "toys"));
        add(new Product(3L, "Thor Figure", "toys"));
    }};

    @Autowired
    private RemoteInventoryService remoteInventoryService;

    public List<Product> getProducts() {
        List<Product> inventoryAvailableProducts = new ArrayList<>();
        for(Product product: this.products) {
            if (remoteInventoryService.isInventoryAvailable(product.getId())) {
                inventoryAvailableProducts.add(product);
            }
        }
        return inventoryAvailableProducts;
    }

    public void createProduct(Product product) {
        this.products.add(product);
    }

    public void updateProduct(Product product) {
        Optional<Product> matchingProduct = products.stream().filter(existingProduct -> existingProduct.getId().equals(product.getId()))
                        .findFirst();
        if (matchingProduct.isPresent()){
            Product match = matchingProduct.get();
            match.setName(product.getName());
            match.setCategory(product.getCategory());
        }
    }

    public void deleteProduct(long id) {
        Product product = new Product(id, null, null);
        products.remove(product);
    }
}
