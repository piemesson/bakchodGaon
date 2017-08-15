package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ForeignKey;

@XmlRootElement
@Entity
public class CommentOnPost {
		
	private String comments;
	private String commentBy;
	@Id
	@GeneratedValue
	private int id;
	
	
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn
	@ForeignKey(name = "commentPostId")
	Post commentOnPost;


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Post getCommentOnPost() {
		return commentOnPost;
	}


	public void setCommentOnPost(Post commentOnPost) {
		this.commentOnPost = commentOnPost;
	}


	@Override
	public String toString() {
		return "Comment [comments=" + comments + ", id=" + id
				+ ", commentOnPost=" + commentOnPost + "]";
	}


	public String getCommentBy() {
		return commentBy;
	}


	public void setCommentBy(String commentBy) {
		this.commentBy = commentBy;
	}
	
	
	
}
