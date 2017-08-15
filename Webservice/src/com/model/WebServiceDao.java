package com.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public interface WebServiceDao {
	
	List<Pojo> signUp(Pojo signUpPojo);
	
	String loginUser(Pojo loginPojo);
	
	String forgotPassword(String username);
	
	String updatePassword(Pojo updatePass , String otp);
	
	Post post(Post postObject , String postedBy);
	
	List<PostVoteComment> showAllPost(String email);
	
	String removeMypost(int postid);
	
	String bookmark(int id,String postedBy );
	 
	List <Bookmark> mybookmarks(String bookMarkedBy);
	
	String removeBookMark(int bookMarkId,String bookMarkedBy);
	
	String yourVote(int postId , String commentor, String voteType);
	
	String commentOnPost(CommentOnPost comment , int postId);
	
	List<CommentOnPost> getAllComments(int postId);
	
}
