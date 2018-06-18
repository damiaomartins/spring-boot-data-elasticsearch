package com.example.springelasticsearchdemo.repository;

import com.example.springelasticsearchdemo.domain.Rating;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RatingRepository extends ElasticsearchRepository<Rating, String> {
}
