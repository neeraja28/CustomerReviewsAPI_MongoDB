package com.udacity.reviewsAPI.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.ArrayList;

/**
 * Mongo Review document
 */

@Document(collection = "mongo_review")
public class MongoReview {
    @Id
    @Field("_id")
    private String Id;

    @Field("review_id")
    private Integer reviewId;

    @Field("review")
    private String review;

    @Field("product_id")
    private Integer productId;

    @Field("comments")
    private ArrayList<MongoComment> comments;

    /**
     * A method to add nested comment
     */
    public ArrayList<MongoComment> addComment() {
        MongoComment mongoComment = new MongoComment();
        mongoComment.setComment("Nested Comment");
        ArrayList<MongoComment> mongoComments = new ArrayList<MongoComment>();
        mongoComments.add(mongoComment);
        return mongoComments;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public ArrayList<MongoComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<MongoComment> comments) {
        this.comments = comments;
    }
}
