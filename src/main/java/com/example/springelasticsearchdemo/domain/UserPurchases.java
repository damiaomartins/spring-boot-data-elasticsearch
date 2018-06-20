package com.example.springelasticsearchdemo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Document(indexName = "user_purchases", type = "user_purchases", shards = 1, replicas = 0, refreshInterval = "30s")
@Setting(settingPath = "/settings/user_purchases.json")
@Mapping(mappingPath = "/mappings/user_purchases.json")
public class UserPurchases {

    @Id
    private String user;

    private List<String> skus;

    public UserPurchases() {
        this.skus = new ArrayList<>();
    }

    public String getUser() {
        return user;
    }

    public UserPurchases setUser(String user) {
        this.user = user;
        return this;
    }

    public List<String> getSkus() {
        return skus;
    }

    public UserPurchases setSkus(List<String> skus) {
        this.skus = skus;
        return this;
    }

    public UserPurchases addSku(String sku) {
        this.skus.add(sku);
        return this;
    }
}
