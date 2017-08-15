 package com.User.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import com.User.SignUpActivity.EncryptingPassword;
import com.User.SignUpActivity.WebServiceImp;
import com.model.Bookmark;
import com.model.CommentOnPost;
import com.model.Pojo;
import com.model.Post;
import com.model.PostVoteComment;
import com.model.WebServiceDao;

@XmlRootElement
@Path("callupserver")
public class Service {
	
	private WebServiceDao webServiceDaoObj= new WebServiceImp();
	
	
	
	
	
	@Path("signmeup")
	@POST
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Pojo signUpMethod(Pojo pojoObject) {
		
	pojoObject.setPassword(new EncryptingPassword().encrypt(pojoObject.getPassword()));
		
	return (Pojo) webServiceDaoObj.signUp(pojoObject) ;
	}
	
	
	
	
	
	@Path("loginuser")   // this method will take the user name and password , first decrypt the password and then match it with the entered string
	@POST
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public String loginUser(Pojo pojoLoginObject)
	{	
		return  webServiceDaoObj.loginUser(pojoLoginObject);
		
	}
	
	
	
	
	
	@Path("forgotPassword/{username}") 
	@GET
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public String fogotPassword(@PathParam("username") String username)
	{	
		System.out.println("email id for which otp is to be generated is ::  "+ username);
		
		return  webServiceDaoObj.forgotPassword(username);	
	}
	
	
	
	
	
	@Path("updatePassword/{otp}")  
	@POST
	@Produces
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public String updatePassword(Pojo updatePass ,  @PathParam("otp") String otp)
	{		
		System.out.println("************** "+ updatePass +  "******** fucked up otp is here  "+otp);	
		return webServiceDaoObj.updatePassword(updatePass, otp);	
	}
	
	
	
	
	@Path("makeAPost/{postedBy}")   // this method will take the user name and password , first decrypt the password and then match it with the entered string
	@POST
	@Consumes
	@Produces({/*MediaType.APPLICATION_JSON,*/MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public String makeAPost(Post postObject ,  @PathParam("postedBy") String postedBy)
	{	
		
		System.out.println("************** "+ postObject +  "******** HERE IS THE POST "+postedBy);
		webServiceDaoObj.post(postObject, postedBy);
		return "ALL COOL FOR NOW "; /*webServiceDaoObj.updatePassword(postObject, postedBy);*/
	
	}
	
	
	
	
	@Path("showAllPosts/{email}")   // this method will take the user name and password , first decrypt the password and then match it with the entered string
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<PostVoteComment> showAllPosts(@PathParam("email")String email)
	{	
		
		
		return webServiceDaoObj.showAllPost(email); /*webServiceDaoObj.updatePassword(postObject, postedBy);*/
	
	}
	
	
	
	
	@Path("deleteMyPost/{postId}")   // this method will take the user name and password , first decrypt the password and then match it with the entered string
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public String deleteMyPost(@PathParam("postId") int postId )
	{	
		return webServiceDaoObj.removeMypost(postId);
	}
	
	
	
	
	
	@Path("bookmark/{bookid}/{postedBy}")   // this method will take the user name and password , first decrypt the password and then match it with the entered string
	@GET
	
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public String bookmarkThisPost(@PathParam("bookid") int bookmark , @PathParam("postedBy") String postedBy)
	{	
	
		
		
		System.out.println("-------------------------------------------------------------------" + postedBy);
		
		return webServiceDaoObj.bookmark(bookmark , postedBy ); 
	
	}
	
	
	
	
	
	@Path("getMybookmarks/{bookMarkedBy}")   // this method will take the user name and password , first decrypt the password and then match it with the entered string
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public List<Bookmark> getMybookmark(@PathParam("bookMarkedBy") String bookMarkedBy)
	{	
	
		
		
		System.out.println("-------------------------------------------------------------------" );
		
		return webServiceDaoObj.mybookmarks(bookMarkedBy);
	
	}


	
	@Path("removeBookMark/{bookMarkId}/{bookMarkedBy}")   // this method will take the user name and password , first decrypt the password and then match it with the entered string
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public String removeBookMark(@PathParam("bookMarkId") int removeBookMarkId, @PathParam ("bookMarkedBy") String bookMarkedBy)
	{	
	
		
		
		System.out.println("-------------------------------------------------------------------" );
		
		return webServiceDaoObj.removeBookMark(removeBookMarkId , bookMarkedBy);
	
	}
	
	
	
	@Path("upvoteDownvote/{postId}/{by}/{voteType}")   // this method will take the user name and password , first decrypt the password and then match it with the entered string
	@GET
	@Produces
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public String upvoteDownvote(@PathParam("postId") int postId , @PathParam("by") String by,@PathParam("voteType") String voteType)
	{	
	
		
		
		System.out.println("----------------------------------VOTE UP / DOWN--------------------------------- " +postId +" "+voteType+" "+by  );
		
		return webServiceDaoObj.yourVote(postId, by , voteType);
	
	}
	
	
	
	
	@Path("comment/{postId}")   // this method will take the user name and password , first decrypt the password and then match it with the entered string
	@POST
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public void comment(CommentOnPost mycomment , @PathParam("postId") int postId )
	{	
		 webServiceDaoObj.commentOnPost(mycomment, postId);
	}
	
	
	
	
	
	@Path("getAllComments/{postId}")   // this method will take the user name and password , first decrypt the password and then match it with the entered string
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public List<CommentOnPost> getCommentOnThatPost(@PathParam("postId") int postId )
	{	
		return webServiceDaoObj.getAllComments(postId);
	}
	
	
	
	
	
	
	
	
	/*@Path("getAllComments/{postId}")   // this method will take the user name and password , first decrypt the password and then match it with the entered string
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	public List<CommentOnPost> getCommentOnThatPost(@PathParam("postId") int postId )
	{	
		return webServiceDaoObj.getAllComments(postId);
	}
	
	*/
	
	
	
	
	
	
	

}


