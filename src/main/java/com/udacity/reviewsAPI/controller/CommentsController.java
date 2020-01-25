package com.udacity.reviewsAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.udacity.reviewsAPI.entity.Comment;
import com.udacity.reviewsAPI.entity.MongoReview;
import com.udacity.reviewsAPI.entity.Review;
import com.udacity.reviewsAPI.repository.CommentRepository;
import com.udacity.reviewsAPI.repository.MongoReviewRepository;
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
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoReviewRepository mongoReviewRepository;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @Valid @RequestBody ObjectNode objectNode) {

        Optional<Review> searchReview = reviewRepository.findById(reviewId);
        if (!searchReview.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            Review review = searchReview.get();
            ObjectMapper objectMapper = new ObjectMapper();
            Comment comment = objectMapper.convertValue(objectNode, Comment.class);
            comment.setReview(review);
            commentRepository.save(comment);
            return new ResponseEntity<>(comment,HttpStatus.OK);
        }
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        List<?> emptyList = new ArrayList();
        Optional<Review> searchReview = reviewRepository.findById(reviewId);
        if (!searchReview.isPresent()) {
            return emptyList;
        }
        Optional<MongoReview> searchMongoreview = mongoReviewRepository.findByReviewId(reviewId);
        if (!searchMongoreview.isPresent()) {
            return emptyList;
        }else{
            MongoReview mongoReview = searchMongoreview.get();
            ArrayList comments = new ArrayList<>();
            // Getting comments from both Review and MongoReview
            comments.addAll(searchReview.get().getComments());
            comments.addAll(mongoReview.getComments());
            return comments;
        }
    }
}
