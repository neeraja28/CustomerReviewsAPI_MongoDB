package com.udacity.reviewsAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.udacity.reviewsAPI.entity.MongoReview;
import com.udacity.reviewsAPI.entity.Product;
import com.udacity.reviewsAPI.entity.Review;
import com.udacity.reviewsAPI.repository.MongoReviewRepository;
import com.udacity.reviewsAPI.repository.ProductRepository;
import com.udacity.reviewsAPI.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoReviewRepository mongoReviewRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @Valid @RequestBody ObjectNode objectNode) {
        Optional<Product> searchProduct = productRepository.findById(productId);
        if (!searchProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        else {
            Product product = searchProduct.get();
            // Used the Object mapper to map json to a generic object which can be used by both Review and MongoReview
            ObjectMapper objectMapper = new ObjectMapper();
            Review review = objectMapper.convertValue(objectNode, Review.class);
            review.setProduct(product);
            review.setReviewId(review.getReviewId());
            reviewRepository.save(review);

            MongoReview mongoReview = objectMapper.convertValue(objectNode, MongoReview.class);
            mongoReview.setProductId(productId);
            mongoReview.setReviewId(review.getReviewId());
            mongoReview.setComments(mongoReview.addComment());
            mongoReviewRepository.save(mongoReview);

            return new ResponseEntity<>(review,HttpStatus.OK);
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        Optional<Product> searchProduct = productRepository.findById(productId);
        if (searchProduct.isPresent()) {
            ArrayList<MongoReview> mongoReviews = mongoReviewRepository.findAllByProductId(productId);
            if (mongoReviews.equals(null)) {
                return ResponseEntity.notFound().build();
            } else {
                ArrayList<MongoReview> reviews = new ArrayList<MongoReview>();
                for (MongoReview item : mongoReviews) {
                    reviews.add(item);
                }

                return new ResponseEntity<>(reviews,HttpStatus.OK);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
