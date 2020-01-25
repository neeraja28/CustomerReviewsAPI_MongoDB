package com.udacity.reviewsAPI.entity;

/**
 * Object to save nested comment in the Mongo review document
 */
public class MongoComment {

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
