package com.example.springelasticsearchdemo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "product", type = "product", shards = 1, replicas = 0, refreshInterval = "-1")
public class Product {

    @Id
    private String id;

    private String sku;
    private String gender;
    private String category;
    private Double price;

    public Product() {
    }

    public String getId() {
        return id;
    }

    public Product setId(String id) {
        this.id = id;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public Product setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Product setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Product setPrice(Double price) {
        this.price = price;
        return this;
    }
}
