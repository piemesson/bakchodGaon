package com.model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PostVoteComment {

	private String post;
	private int totalUpvote;
	private int totalDownvote;
	private String previouUpvote;
	private String previousDownvote;
	private String postedBy;
	
	private String oneComment;
	
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public int getTotalUpvote() {
		return totalUpvote;
	}
	public void setTotalUpvote(int totalUpvote) {
		this.totalUpvote = totalUpvote;
	}
	public int getTotalDownvote() {
		return totalDownvote;
	}
	public void setTotalDownvote(int totalDownvote) {
		this.totalDownvote = totalDownvote;
	}
	
	
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	public String getOneComment() {
		return oneComment;
	}
	public void setOneComment(String oneComment) {
		this.oneComment = oneComment;
	}
	@Override
	public String toString() {
		return "PostVoteComment [post=" + post + ", totalUpvote=" + totalUpvote
				+ ", totalDownvote=" + totalDownvote + ", postedBy=" + postedBy
				+ ", oneComment=" + oneComment + "]";
	}
	public String getPreviousDownvote() {
		return previousDownvote;
	}
	public void setPreviousDownvote(String previousDownvote) {
		this.previousDownvote = previousDownvote;
	}
	public String getPreviouUpvote() {
		return previouUpvote;
	}
	public void setPreviouUpvote(String previouUpvote) {
		this.previouUpvote = previouUpvote;
	}

	
	
	
	
	
}
