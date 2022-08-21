package com.randomMovie.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

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

	@SuppressWarnings("unchecked")
	public Set<RandomMovie> getRandomMovie(String vote, String genre) {

		// return randomMovie.getRandomTenMovies();

		SampleOperation matchStage = Aggregation.sample(1);

		Aggregation aggregation = Aggregation.newAggregation(matchStage);

		Aggregation aggregation1 = Aggregation.newAggregation(Aggregation.sample(10)

		);
		
		
		if (Objects.nonNull(vote) && !vote.equals("undefined")
				|| Objects.nonNull(genre) && !genre.equals("undefined") && !genre.equals("")) {
//			MatchOperation matchStage1 = Aggregation.match(Criteria.where("votes_ln").gte(vote).orOperator(Criteria.where("genre").is(genre.split(",")[0])));
//			//SampleOperation sampleStage = Aggregation.sample(1);
//			Aggregation aggOp = Aggregation.newAggregation(matchStage1);
//
//			AggregationResults<RandomMovie> output = mongoTemplate.aggregate(aggOp, "randomMovie", RandomMovie.class);
//			
//			output.forEach(x ->{
//				System.out.println(x);
//			});

//			TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(
//					genre);
//
//			TextQuery query = TextQuery.queryText(criteria).sortByScore();
			
			Sort sort = Sort.by("score");
			List<RandomMovie> random = new ArrayList<>();
			List<RandomMovie> list = new ArrayList<>();
			if (!genre.equals("undefined") && !genre.equals("")) {
				TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(genre);
				TextQuery query = TextQuery.queryText(criteria);

				random = mongoTemplate.find(query, RandomMovie.class);
				list = random;
//				list = random.stream().filter(x -> x.getVotes_ln() != null)
//						.filter(x -> x.getVotes_ln() >= Long.valueOf(vote)).collect(Collectors.toList());
			} 
			if (!vote.equals("undefined")) {
				random = randomMovie.randomGte7(7, 2000);
				list = random.stream().filter(x -> x.getVotes_ln() != null)
						.filter(x -> x.getVotes_ln() >= Long.valueOf(vote)).collect(Collectors.toList());
			}

//		    List<RandomMovie> random = mongoTemplate.find(query, RandomMovie.class);
			// System.out.println(random);
			// random = randomMovie.randomGte7(7, 2000);
			Random r = new Random();
			Set<RandomMovie> setOfMovies = new HashSet<>();

//			if (!vote.equals("undefined")) {
			
			// List<RandomMovie> list = randomMovie.findAll();
			if (list.size() <= 10) {
				return new HashSet<RandomMovie>(list);
			} else {
				while (setOfMovies.size() != 10) {
					setOfMovies.add(list.get(r.nextInt(list.size())));
				}
			}

//			}

			return setOfMovies;
		}

		Random r = new Random();

		List<RandomMovie> list = randomMovie.randomGte7(7, 2000);
		System.out.println(list.size());
		Set<RandomMovie> setOfMovies = new HashSet<>();
		// List<RandomMovie> list = randomMovie.findAll();
		while (setOfMovies.size() != 10) {
			setOfMovies.add(list.get(r.nextInt(list.size())));
		}
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

	public List<RandomMovie> getByTitle(String name) {
		return randomMovie.findByQuery(name);
	}

	public void saveTypeChange() {
		randomMovie.findAll().forEach(x -> {
			System.out.println(x.getVotes());
			if (x.getVotes_ln() == null) {
				if (x.getVotes() == "null") {
					System.out.println(x.getVotes());
					x.setVotes_ln(0l);
					randomMovie.save(x);
				} else if (x.getVotes() != null) {
					x.setVotes_ln(Long.valueOf(String.valueOf(x.getVotes()).replace(",", "")));
					randomMovie.save(x);
				} else {
					x.setVotes_ln(0l);
					randomMovie.save(x);
				}
			}

		});
		;
	}

}
