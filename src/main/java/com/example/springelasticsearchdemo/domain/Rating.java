package com.example.springelasticsearchdemo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = "rating", type = "rating", shards = 1, replicas = 0, refreshInterval = "-1")
public class Rating {

    @Id
    private String id;

    private String user;
    private Long timestamp;
    private String customer;
    private String campaign;
    private String eventType;
    private String sku;

    public Rating() {
    }

    public String getId() {
        return id;
    }

    public Rating setId(String id) {
        this.id = id;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Rating setUser(String user) {
        this.user = user;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Rating setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public Rating setCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public String getCampaign() {
        return campaign;
    }

    public Rating setCampaign(String campaign) {
        this.campaign = campaign;
        return this;
    }

    public String getEventType() {
        return eventType;
    }

    public Rating setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public Rating setSku(String sku) {
        this.sku = sku;
        return this;
    }
}
