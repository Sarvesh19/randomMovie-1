package com.randomMovie.service;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.randomMovie.repository.RandomMovie;
import com.randomMovie.repository.RandomMovieRepo;

@Service
public class RandomMovieService {

	@Autowired
	private RandomMovieRepo randomMovie;

	private final MongoTemplate mongoTemplate;

	public RandomMovieService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public AggregationResults<RandomMovie> getRandomMovie() {

		// return randomMovie.getRandomTenMovies();

		SampleOperation matchStage = Aggregation.sample(10);

		Aggregation aggregation = Aggregation.newAggregation(matchStage);

		Aggregation aggregation1 = Aggregation.newAggregation(Aggregation.sample(10)

		);

		MatchOperation matchStage1 = Aggregation.match(Criteria.where("tags"));
		SampleOperation sampleStage = Aggregation.sample(10);
		Aggregation aggOp = Aggregation.newAggregation(matchStage1, sampleStage);

		AggregationResults<RandomMovie> output = mongoTemplate.aggregate(aggregation1, "randomMovie",
				RandomMovie.class);

		return output;
	}

	public List<RandomMovie> getQuery(String name) {
		return randomMovie.findByQuery(name);
	}

	public List<RandomMovie> getByStars(String name) {
		return randomMovie.findByQueryByStars(name);
	}

}
