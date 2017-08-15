package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ForeignKey;

// This class has one to one mapping with pojo class's variable user name (specifying who posted)
@XmlRootElement
@Entity
public class Post {
	
	private String post;
		
	@Id
	@GeneratedValue
	private int postid;
	
	@OneToOne
	@JoinColumn
	@ForeignKey( name = "postedBy")
	Pojo postedBy;
	
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}

	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	
	public Pojo getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(Pojo postedBy) {
		this.postedBy = postedBy;
	}
	
	@Override
	public String toString() {
		return "Post [post=" + post +   ", postid=" + postid
				+ ", postedBy=" + postedBy + "]";
	}
	
}
