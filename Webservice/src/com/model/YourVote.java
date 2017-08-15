package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;


import org.hibernate.annotations.ForeignKey;


	@XmlRootElement
	@Entity
	public class YourVote {
	
	private String upvote = "upvote";
	private String donwvote = "downvote";
	private String votedBy;
	
	@Transient
	private int totalUpvote;
	@Transient
	private int toatlDownvote;
	
	@Id
	@GeneratedValue
	private int voteiD;
	
	
	
	// mapped variable      |::::::::::::::::::::::::::::::::::::::
	
	@OneToOne
	@JoinColumn
	@ForeignKey(name = "voteUpDownId")
	Post yourVotedId;


	public String getVotedBy() {
		return votedBy;
	}
	public void setVotedBy(String votedBy) {
		this.votedBy = votedBy;
	}
	
//	@XmlTransient
	public Post getYourVotedId() {
		return yourVotedId;
	}
	public void setYourVotedId(Post yourVotedId) {
		this.yourVotedId = yourVotedId;
	}
	@Override
	public String toString() {
		return "YourVote [upvote=" + upvote + ", donwvote=" + donwvote
				+ ", votedBy=" + votedBy + ", totalUpvote=" + totalUpvote
				+ ", toatlDownvote=" + toatlDownvote + ", voteiD=" + voteiD
				+ ", voteUpDownId=" + yourVotedId + "]";
	}
	public String getUpvote() {
		return upvote;
	}
	public void setUpvote(String upvote) {
		this.upvote = upvote;
	}
	public String getDonwvote() {
		return donwvote;
	}
	public void setDonwvote(String donwvote) {
		this.donwvote = donwvote;
	}



	public String getBy() {
		return votedBy;
	}



	public void setBy(String by) {
		this.votedBy = by;
	}





	public int getVoteiD() {
		return voteiD;
	}



	public void setVoteiD(int voteiD) {
		this.voteiD = voteiD;
	}
	public int getTotalUpvote() {
		return totalUpvote;
	}
	public void setTotalUpvote(int totalUpvote) {
		this.totalUpvote = totalUpvote;
	}
	public int getToatlDownvote() {
		return toatlDownvote;
	}
	public void setToatlDownvote(int toatlDownvote) {
		this.toatlDownvote = toatlDownvote;
	}
		
	
}
