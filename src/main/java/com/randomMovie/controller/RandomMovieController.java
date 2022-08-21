package com.randomMovie.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<Set<RandomMovie>> getRandomMovies(@RequestParam("vote") String vote, @RequestParam("genre") String genre) {
		if(genre == "null") {
			genre = "undefined";
		}
		if(vote == "null") {
			vote = "undefined";
		}
		Set<RandomMovie> movies = randomMovie.getRandomMovie(vote,genre);

		return new ResponseEntity<Set<RandomMovie>>(movies, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "movie/{movie}", method = RequestMethod.GET)
	public ResponseEntity<List<RandomMovie>> getMovie(@PathVariable String movie) {

		List<RandomMovie> movies = randomMovie.getQuery(movie);

		return new ResponseEntity<List<RandomMovie>>(movies, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "stars/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<RandomMovie>> getByStars(@PathVariable String name) {

		List<RandomMovie> movies = randomMovie.getByStars(name);

		return new ResponseEntity<List<RandomMovie>>(movies, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "title/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<RandomMovie>> getByTitle(@PathVariable String name) {

		List<RandomMovie> movies = randomMovie.getByTitle(name);

		return new ResponseEntity<List<RandomMovie>>(movies, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "typeChange", method = RequestMethod.GET)
	public ResponseEntity<String> typeChange() {

		randomMovie.saveTypeChange();

		return new ResponseEntity<String>("", new HttpHeaders(), HttpStatus.OK);
	}
}
