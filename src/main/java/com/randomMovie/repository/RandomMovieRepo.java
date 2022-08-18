package com.randomMovie.repository;

import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomMovieRepo extends MongoRepository<RandomMovie, String> {
	@Aggregation(pipeline = { "$sample: { size: 10 } }" })
	AggregationResults<RandomMovie> getRandomTenMovies();
	
	
	@Query(value = "{'title': {$regex : ?0, $options: 'i'}}")
    List<RandomMovie> findByQuery(String title);
	
	
	@Query(value = "{'stars': {$regex : ?0, $options: 'i'}}")
    List<RandomMovie> findByQueryByStars(String stars);
	
	
    @Query("{$and :[{rating: {$gte: ?0}},{votes_ln: {$gte: ?1}}] }")
    List<RandomMovie> randomGte7(int rating,int votes);
    
    
    

    @Query("{$text:{$search: ?0} ")
    Set<RandomMovie> filterMatch(String genre , int vote);
	
}
