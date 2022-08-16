package com.randomMovie.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.randomMovie.repository.RandomMovie;
import com.randomMovie.repository.RandomMovieRepo;
import com.randomMovie.service.RandomMovieService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class RandomMovieController {

	@Autowired
	private RandomMovieService randomMovie;

	@RequestMapping(value = "randomMovies", method = RequestMethod.GET)
	public ResponseEntity<AggregationResults<RandomMovie>> getRandomMovies() {

		AggregationResults<RandomMovie> movies = randomMovie.getRandomMovie();

		return new ResponseEntity<AggregationResults<RandomMovie>>(movies, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "movie/{movie}", method = RequestMethod.GET)
	public ResponseEntity<List<RandomMovie>> getMovie(@PathVariable String movie) {

		List<RandomMovie> movies = randomMovie.getQuery(movie);

		return new ResponseEntity<List<RandomMovie>>(movies, new HttpHeaders(), HttpStatus.OK);
	}
}
