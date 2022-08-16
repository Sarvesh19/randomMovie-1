package com.randomMovie.repository;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "randomMovie")
public class RandomMovie {

	@Id
	private ObjectId _id;
	
	private String image;
	private String genre;
	private String title;
	private String detail;
	private String rating;
	private String stars;
	private String votes;
	private Integer hitCount;
	private Integer check;

	public ObjectId getId() {
		return _id;
	}

	public void setId(ObjectId _id) {
		this._id = _id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	public String getVotes() {
		return votes;
	}

	public void setVotes(String votes) {
		this.votes = votes;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	@Override
	public String toString() {
		return "RandomMovie [id=" + id + ", image=" + image + ", genre=" + genre + ", title=" + title + ", detail="
				+ detail + ", rating=" + rating + ", stars=" + stars + ", votes=" + votes + ", hitCount=" + hitCount
				+ "]";
	}

}
