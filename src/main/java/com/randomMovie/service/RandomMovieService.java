package com.randomMovie.service;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
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
		
		//return randomMovie.getRandomTenMovies();

		SampleOperation matchStage = Aggregation.sample(1);
		
		Aggregation aggregation = Aggregation.newAggregation(matchStage);
		
		
//		 Aggregation aggregation1 = Aggregation.newAggregation(
//	                Aggregation.sample(2)
//	                
//	               
//	        );
		
		AggregationResults<RandomMovie> output = mongoTemplate.aggregate(aggregation, "randomMovie", RandomMovie.class);

		return output;
	}
	
	public List<RandomMovie> getQuery(String name){
		return randomMovie.findByQuery(name);
	}
	

	public List<RandomMovie> getByStars(String name){
		return randomMovie.findByQueryByStars(name);
	}

}
