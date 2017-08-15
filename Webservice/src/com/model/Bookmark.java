package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Entity
public class Bookmark {
	
	
	@Id
	@GeneratedValue
	private int id;
	
	private String bookMarkedBy;
	
	
	@OneToOne
	@JoinColumn
	Post bookmarkedPost;
	
	
	/*@OneToOne
	@JoinColumn
	Pojo bookMarkedBy;*/
	
	
	
	
	public String getPostedBy() {
		return bookMarkedBy;
	}
	public void setPostedBy(String postedBy) {
		this.bookMarkedBy = postedBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
		

	public Post getBookmarkedPost() {
		return bookmarkedPost;
	}
	public void setBookmarkedPost(Post bookmarkedPost) {
		this.bookmarkedPost = bookmarkedPost;
	}

	
	
	
	
	// some fields will be added later

}
