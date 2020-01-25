package com.udacity.reviewsAPI.repository;

import com.udacity.reviewsAPI.entity.MongoReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface MongoReviewRepository extends MongoRepository<MongoReview, Integer> {

    ArrayList<MongoReview> findAllByProductId(Integer productId);

    Optional<MongoReview> findByReviewId(Integer reviewId);

}
