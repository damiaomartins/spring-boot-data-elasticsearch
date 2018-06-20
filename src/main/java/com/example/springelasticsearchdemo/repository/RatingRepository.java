package com.example.springelasticsearchdemo.repository;

import com.example.springelasticsearchdemo.domain.UserPurchases;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RatingRepository extends ElasticsearchRepository<UserPurchases, String> {



}
