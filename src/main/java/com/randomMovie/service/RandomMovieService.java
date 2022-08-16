package com.randomMovie.service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

	public Set<RandomMovie> getRandomMovie() {

		// return randomMovie.getRandomTenMovies();

		SampleOperation matchStage = Aggregation.sample(1);

		Aggregation aggregation = Aggregation.newAggregation(matchStage);

		Aggregation aggregation1 = Aggregation.newAggregation(Aggregation.sample(10)

		);

		MatchOperation matchStage1 = Aggregation.match(Criteria.where("rating").gte(7).and("votes_ln").gt(5000));
		SampleOperation sampleStage = Aggregation.sample(1);
		Aggregation aggOp = Aggregation.newAggregation(matchStage1, sampleStage);

		AggregationResults<RandomMovie> output = mongoTemplate.aggregate(aggOp, "randomMovie", RandomMovie.class);
		Random r = new Random();

		List<RandomMovie> list = randomMovie.randomGte7(7, 5000);

		Set<RandomMovie> setOfMovies = new HashSet<>();
		// List<RandomMovie> list = randomMovie.findAll();
		while (setOfMovies.size() == 10) {
			setOfMovies.add(list.get(r.nextInt(list.size())));
		}
		System.out.println(list);
//		randomMovie.findAll().forEach(x ->{
//			System.out.println(x);
//		});
		return setOfMovies;
	}

	public List<RandomMovie> getQuery(String name) {
		return randomMovie.findByQuery(name);
	}

	public List<RandomMovie> getByStars(String name) {
		return randomMovie.findByQueryByStars(name);
	}

	public void saveTypeChange() {
		randomMovie.findAll().forEach(x -> {
			if (x.getVotes() != null) {
				x.setVotes_ln(Long.valueOf(String.valueOf(x.getVotes()).replace(",", "")));
				randomMovie.save(x);
			} else {
				x.setVotes_ln(0l);
				randomMovie.save(x);
			}

		});
		;
	}

}
