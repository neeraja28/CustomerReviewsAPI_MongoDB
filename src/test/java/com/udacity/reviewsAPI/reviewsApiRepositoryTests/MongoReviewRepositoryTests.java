package com.udacity.reviewsAPI.reviewsApiRepositoryTests;

import com.udacity.reviewsAPI.entity.MongoReview;
import com.udacity.reviewsAPI.repository.MongoReviewRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;


@RunWith(SpringRunner.class)
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class MongoReviewRepositoryTests {
    @Autowired
    private MongoReviewRepository mongoReviewRepository;

    @DisplayName("given object to save"
            + "when save object using MongoDB Template"
            + "then object is saved")

    @Before
    public void setUp() {
        //given
        MongoReview mongoReview = new MongoReview();
        mongoReview.setProductId(1);
        mongoReview.setReviewId(1);
        mongoReview.setReview("This is a good product");
        mongoReviewRepository.save(mongoReview);
    }

    /**
     * Test to retrieve the review by reviewId and matches the review in the repository
     * @throws Exception
     */
    @Test
    public void testFindByReviewId() throws Exception {
        Optional<MongoReview> mongoReview1 = mongoReviewRepository.findByReviewId(1);
        Assert.assertNotNull(mongoReview1);
        Assert.assertEquals(mongoReview1.get().getReview(), "This is a good product");
    }

    /**
     * Test to retrieve the reviews by productId
     * @throws Exception
     */
    @Test
    public void testFindAllByProductId() throws Exception {
        ArrayList<MongoReview> mongoReview1 = mongoReviewRepository.findAllByProductId(1);
        Assert.assertNotNull(mongoReview1);
        Assert.assertEquals(mongoReview1.get(0).getReview(), "This is a good product");
    }
}
