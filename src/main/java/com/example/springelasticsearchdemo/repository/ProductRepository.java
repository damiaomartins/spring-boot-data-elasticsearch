package com.example.springelasticsearchdemo.repository;

import com.example.springelasticsearchdemo.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
}
