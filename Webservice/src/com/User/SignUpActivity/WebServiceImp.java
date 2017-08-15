package com.User.SignUpActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.model.CommentOnPost;
import com.model.MyUtil;
import com.model.Pojo;
import com.model.OTP;
import com.model.Post;
import com.model.Bookmark;
import com.model.PostVoteComment;
import com.model.YourVote;
import com.model.WebServiceDao;




@XmlRootElement
public class WebServiceImp implements WebServiceDao {
	
	@Override
	public List<Pojo> signUp(Pojo pojo) {
				
		SessionFactory sFactory=MyUtil.getSessionFactory();
		Session session=sFactory.openSession();
		
		Transaction tx=session.beginTransaction();
		pojo.setEmail(pojo.getEmail().toLowerCase());
		session.save(pojo);
		tx.commit();
		return null;

	}

	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String loginUser(Pojo loginPojo) {

		SessionFactory sFactory=MyUtil.getSessionFactory();
		Session session=sFactory.openSession();
	//	Transaction tx=session.beginTransaction();

		Query query=session.createQuery(" from yoyoyoyo where email=:email");
	    
	    query.setString("email", loginPojo.getEmail().toLowerCase());
	    
	    
	    List<Pojo> feedList=null;
	    try
	    {
	     feedList=query.list();
	        
		    String decryptpass=	new EncryptingPassword().decrypt(feedList.get(0).getPassword());
				 
		    if(decryptpass.equals(loginPojo.getPassword()))
		    {
		    	return "Yes";
		    }	 
				
	    }catch(Exception ex)
	    {
	    	System.out.println("invalid user ");
	    }
	     
			    		   
		 return "No";	 
	
	}

	
	
	
	
	@Override
	public String forgotPassword(String username) {
		
		int otp_id=0;
	
	    // code to generate random order ID 
		String otpPassword=null;
		otpPassword=UUID.randomUUID().toString();   
		otpPassword=otpPassword.substring(0,6)+"!";
		
		System.out.println("!@$#%@^%#@WQ%&W^*#&%*^&%^$%&%^$%^%$^^U&%^$!%^%&*^&#^&%^!#$^#%&@$@%^!$#%&@$*^@&%^!%&@*^#&@%^ "+otpPassword);
			
		// otp generation time in long format	
		String timeStamp = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss").format(new Date());
		System.out.println(timeStamp);
		timeStamp = timeStamp.replaceAll(":", "");
		String timeStamp1 = timeStamp.replaceAll(":", "");
		long sysdate = Long.parseLong(timeStamp1);
		
		//JPA
		SessionFactory sFactory=MyUtil.getSessionFactory();
		Session session=sFactory.openSession();
		Transaction tx=session.beginTransaction();
		
		// checking for pre existing otp		
		    Query query=session.createQuery(" from OTP where email=:username");	    
		    query.setString("username", username );	    
		    
		    try
		    {
		    List<OTP> feedList=(List<OTP>)query.list();	
		    
		    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{  "+feedList.size());
		    for(OTP getOtpId : feedList)		   	    
		    {
		    otp_id = getOtpId.getId();
		    }
		    
		    
			if (feedList.size() > 0) {
				OTP fetchOtp = (OTP) session.get(OTP.class, otp_id);
				fetchOtp.setOtp(otpPassword);
				fetchOtp.setOtpTimeStamp(sysdate);
				session.save(fetchOtp);
				tx.commit();
			}
		    
			else {

				Pojo fetchUser = (Pojo) session.get(Pojo.class, username);

				if (fetchUser != null) {

					OTP otpObject = new OTP();
					otpObject.setOtp(otpPassword);
					otpObject.setOtpTimeStamp(sysdate);

					otpObject.setEmail_id(fetchUser);
					session.saveOrUpdate(otpObject);
					session.getTransaction().commit();
					session.flush();
					session.close();

				} else {
					System.err.println("#$!@%$!@%$!@%$!@$%!$@5 GALAT GALAT GALAT");
				}
			}
			System.err.println("@@@@@@@@@@@@@@@@@ " + otp_id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}







	@Override
	public String updatePassword(Pojo updatePass , String otp) {
		
		
		int otp_id=0; // to remove the otp row from the table once password is updated
		
		String timeStamp = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss").format(new Date()); // getting current timestamp
		// converting time stamp into long
		long otpExpirationDate=0;											
		timeStamp = timeStamp.replaceAll(":", "");
		String timeStamp1 = timeStamp.replaceAll(":", "");
		
		long sysdate = Long.parseLong(timeStamp1);
				
		SessionFactory sFactory=MyUtil.getSessionFactory();
		Session session=sFactory.openSession();
		Transaction tx=session.beginTransaction();
			
		 Query query=session.createQuery(" from OTP where email=:username and otp=:otp");
		    
		    query.setString("username", updatePass.getEmail() );	   
		    query.setString("otp", otp );	
		    
		    try
		    {
		    List<OTP> feedList=(List<OTP>)query.list();
		    
		    for(OTP otpDetails : feedList)
		   	    
		    {
		    	otpExpirationDate = otpDetails.getOtpTimeStamp();
		    	otp_id = otpDetails.getId();
		    	
		    	if(sysdate > otpExpirationDate+1000 )
				{					
					return "OTP expired , click on forgot password again";					
				}
				else {
					
					System.out.println("PRINTING UPDATED PASSWORD :::::::::::::::::: ");
					String updatePassword = new EncryptingPassword().encrypt(updatePass.getPassword());
					Pojo fetchuser = (Pojo)session.get(Pojo.class, updatePass.getEmail());
					
					OTP removeOtp = (OTP) session.get(OTP.class, otp_id);
					
					session.delete(removeOtp);
					
					fetchuser.setPassword(updatePassword);
					session.update(fetchuser);
					tx.commit();
					
					
					
					return "password updated successfully";
				}
		    	
		    }
		    		
		    }catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		return "NA NA NA NA NA NA NA";
	}



	
	
	@Override
	public Post post(Post postObject , String postedBy) {
		SessionFactory sFactory=MyUtil.getSessionFactory();
		Session session=sFactory.openSession();
		Transaction tx=session.beginTransaction();
		
		Pojo fetchUser = (Pojo) session.get(Pojo.class, postedBy);
		
		Post postObject1 = new Post();
		postObject1.setPost(postObject.getPost());
//		postObject1.setUpvote(postObject.getUpvote());
//		postObject1.setDownvote(postObject.getDownvote());
//		postObject1.setComments(postObject.getComments());
//		
		postObject1.setPostedBy(fetchUser);

		session.save(postObject1);
		session.getTransaction().commit();
//		tx.commit();
		session.flush();
		session.close();
		
		
		return null;
	}







	@Override
	public List<PostVoteComment> showAllPost(String email)  {
		
		ArrayList<PostVoteComment> alist=new ArrayList<PostVoteComment>();
		Connection con = null;
	    Statement st=null;
	  	   
	    String upvoteQuery   = "select yourvotedid_postid, count(upvote) count from YourVote where upvote = 'Y' and yourvotedid_postid=? group by upvote ,yourvotedid_postid";
	    
	    String donwvoteQuery = "select yourvotedid_postid, count(donwvote) count from YourVote where donwvote = 'N' and yourvotedid_postid=? group by donwvote ,yourvotedid_postid";
	    
	    String commentQuery  = "SELECT * FROM   CommentOnPost where rownum = 1 and commentonpost_postid = ?";
	    
	    String previousVoteQuery="select upvote,donwvote from YourVote where yourvotedid_postid=? and votedBy=?";
	    
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}   catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	try{
		ResultSet rs1=((java.sql.Statement) st).executeQuery("select post.post,post.postid,post.postedby_email,vote.upvote,vote.donwvote from Post post left outer join YourVote vote on post.postid = vote.yourvotedid_postid");
		ResultSet upvoteResultSet   = null;
		ResultSet downvoteResultSet = null;
		ResultSet commentResultSet  = null;
		ResultSet previouslyVotedResultSet  = null;
		java.sql.PreparedStatement upvotePrepSt = null;
		java.sql.PreparedStatement downvotePrepSt =null;
		java.sql.PreparedStatement psComment =null;
		java.sql.PreparedStatement previouslyVotedPs =null;
		
		int tempid =0;
		
		while(rs1.next())
		{
			
			tempid = rs1.getInt("postid"); // getting the post id of the post . And then using it to fetch other result
				
			System.out.println("temp id currently is ::::::::::::::::   >>>>>>>>>>>>>>> "+ tempid);
			
			PostVoteComment postvotecomment =  new PostVoteComment();			
			upvotePrepSt = con.prepareStatement(upvoteQuery  );
			upvotePrepSt.setInt(1,tempid);
			upvoteResultSet = upvotePrepSt.executeQuery();
			
			while (upvoteResultSet.next()) {
				
				postvotecomment.setTotalUpvote(upvoteResultSet.getInt("count"));				
			}
			
			
			downvotePrepSt =  con.prepareStatement(donwvoteQuery);
			downvotePrepSt.setInt(1,tempid);
			downvoteResultSet = downvotePrepSt.executeQuery();
			
			while (downvoteResultSet.next()) {
				
				postvotecomment.setTotalDownvote(downvoteResultSet.getInt("count"));
				
			}	
			
			psComment = con.prepareStatement(commentQuery);
			psComment.setInt(1, tempid);
			commentResultSet = psComment.executeQuery();
			
			while (commentResultSet.next()) {
											
				postvotecomment.setOneComment(commentResultSet.getString("comments"));				
			}
			
			
			previouslyVotedPs = con.prepareStatement(previousVoteQuery);
			previouslyVotedPs.setInt(1, tempid);
			previouslyVotedPs.setString(2, email);
			previouslyVotedResultSet = previouslyVotedPs.executeQuery();
			
			while (previouslyVotedResultSet.next()) {
											
				if(previouslyVotedResultSet.getString("upvote").equalsIgnoreCase("Y"))
				{
					postvotecomment.setPreviouUpvote("Already Voted UP");
				}
				if(previouslyVotedResultSet.getString("donwvote").equalsIgnoreCase("N"))
				{
					postvotecomment.setPreviouUpvote("Already Voted down");
				}
					
			}
			
			
		
				postvotecomment.setPost(rs1.getString("post"));
				postvotecomment.setPostedBy(rs1.getString("postedby_email"));
				alist.add(postvotecomment);
				//commentList.clear(); // clearing the list 
			}
		
		} catch (Exception e) {
			e.printStackTrace();

		}
	
	
		System.out.println("<<<<<<<<<<<    PRINTING THE PATIENT MEDICAL HISTORY   >>>>>>>>>>>>> " + alist);
			return alist;
		
		
}


	
	@Override
	public String bookmark(int id,String postedBy) {
		
		
		SessionFactory sFactory=MyUtil.getSessionFactory();
		Session session=sFactory.openSession();
		Transaction tx=session.beginTransaction();
				
		Post post = (Post) session.get(Post.class,id);	
		Bookmark profile = new Bookmark();
		profile.setPostedBy(postedBy);
		profile.setBookmarkedPost(post);

		session.save(profile);
		tx.commit();
		session.close();
		
		return null;
	}







	@Override
	public List<Bookmark> mybookmarks(String bookMarkedBy) {

			SessionFactory sFactory=MyUtil.getSessionFactory();
			Session session=sFactory.openSession();
			Transaction tx=session.beginTransaction();
			
			 Query query=session.createQuery(" from Bookmark where bookMarkedBy=:bookMarkedBy");			    
			 query.setString("bookMarkedBy", bookMarkedBy );			  
			 		List<Bookmark> bookMarkList=(List<Bookmark>)query.list();
			 		System.out.println("\n \n \n \n \n");
			 	for(Bookmark getOtpId : bookMarkList)
			   	    
			    {
			    	System.out.println(getOtpId);	
			    }
			    System.out.println("\n \n \n \n \n");
			
			return bookMarkList;
		}







	@Override
	public String removeBookMark(int bookMarkId,String bookMarkedBy) {

		System.out.println("INSIDER INSIDER INSIDER ");
		
		SessionFactory sFactory=MyUtil.getSessionFactory();
		Session session=sFactory.openSession();
		Transaction tx=session.beginTransaction();
		
		int remoBookMarkid =0;	
		
		
		 Query query=session.createQuery(" from Bookmark where bookMarkedBy=:bookMarkedBy and bookmarkedpost_postid=:bookMarkId");			    
		 query.setString("bookMarkedBy", bookMarkedBy );
		 query.setInteger("bookMarkId", bookMarkId );
		
		 List<Bookmark> removeBookMark=(List<Bookmark>)query.list(); 

		 for(Bookmark remBookMark : removeBookMark)
		   	    
		    {
		    	remoBookMarkid = remBookMark.getId();	
		    }
		 
		 Bookmark getBookMarked = (Bookmark) session.get(Bookmark.class, remoBookMarkid);
		 		 
		session.delete(getBookMarked);
		
		tx.commit();		
		return null;
	}







	@Override
	public String yourVote(int postId, String votedBy , String voteType) {
				
		
		SessionFactory sFactory=MyUtil.getSessionFactory();
		Session session=sFactory.openSession();
		Transaction tx=session.beginTransaction();
				
		Post getPostId = (Post) session.get(Post.class, postId);	
		
		Query query=session.createQuery("from YourVote where yourVotedId_postid=:postId and votedby=:votedby");
		    
		 query.setInteger("postId", postId);	
		 query.setString("votedby", votedBy);
	
		List<YourVote> upvoteDownvoteList=(List<YourVote>) query.list();

		if (upvoteDownvoteList.size() == 0) {

			YourVote upvotedownvoteObj = new YourVote();

			upvotedownvoteObj.setBy(votedBy);
			upvotedownvoteObj.setYourVotedId(getPostId);

			if (voteType.equalsIgnoreCase("upvote")) {

				upvotedownvoteObj.setUpvote("Y");

			}

			else if (voteType.equalsIgnoreCase("downvote")) {
				upvotedownvoteObj.setDonwvote("N");
			}
			
			upvotedownvoteObj.setYourVotedId(getPostId);
			session.save(upvotedownvoteObj);
			tx.commit();
		}

		// checking if already upvoted or not

		else if (upvoteDownvoteList.size() > 0)

		{
			System.out.println("calling reverse my vote method");
			reverseMyVote(postId, votedBy, voteType, upvoteDownvoteList,upvoteDownvoteList.size());

		}
		    		
		
		return null;
	}

		
	 		public void reverseMyVote(int postId , String votedBy , String voteType , List<YourVote> voteList , int listSize)
	 		{
	 			
	 			SessionFactory sFactory=MyUtil.getSessionFactory();
	 			Session session=sFactory.openSession();
	 			Transaction tx=session.beginTransaction();
	 			 			
	 			int yourVoteId =0; 			
	 			String upvote = "";	 	    	
	 	    	String downvote = "";	 			
	 			
	 			for(YourVote yourVoteList : voteList)
		    	{
	 				
		    		 yourVoteId = yourVoteList.getVoteiD();			    	
			    	 upvote = yourVoteList.getUpvote();			    				    	
			    	 downvote = yourVoteList.getDonwvote();		    	 
			    	 System.out.println(" **************    " +yourVoteId + " upvote -> "+upvote +" downvote -> "+downvote);	    		
		    	}
	 			
	 			YourVote getYourVote = (YourVote) session.get(YourVote.class, yourVoteId);	
	 			
	 			

	 			// checking the incoming vote type i.e either upvote or downvote
	 			if(voteType.equalsIgnoreCase("upvote")) // then if a post is already upvoted , remove its upvote | if downvoted remove its downvote and upvote it and vice-versa
	 			
	 			{
	 						
	 				
			if (upvote.equalsIgnoreCase("Y")) // case when the post is already
												// upvoted
			{

				getYourVote.setDonwvote("downvote");
				getYourVote.setUpvote("upvote");
			}

			else

			if (upvote.equalsIgnoreCase("upvote")) // when it was not upvoted
			{

				getYourVote.setDonwvote("downvote");
				getYourVote.setUpvote("Y");
			}

			session.update(getYourVote);
			tx.commit();

	 			}	
	 			
		if (voteType.equalsIgnoreCase("downvote"))

			{
			if (downvote.equalsIgnoreCase("N")) // case when the post is already												// downvoted
			{
				getYourVote.setDonwvote("downvote");
				getYourVote.setUpvote("upvote");
			}

			else

			if (downvote.equalsIgnoreCase("downvote")) // when it was not downvoted
			{
				getYourVote.setDonwvote("N");
				getYourVote.setUpvote("upvote");
			}
			
			
			//getYourVote.setVoteUpDownId()
			session.update(getYourVote);
			tx.commit();

		}
	 			
	 			
	 			
}







			@Override
			public String commentOnPost(CommentOnPost comment ,int postId) {
				
				SessionFactory sFactory=MyUtil.getSessionFactory();
	 			Session session=sFactory.openSession();
	 			Transaction tx=session.beginTransaction();
	 			
	 			Post fetchPost =(Post) session.get(Post.class, postId); // get the post on which user wants to comment
	 			
	 			System.out.println("!!!!!!!!!!!!!!!!!=============================== > "+ fetchPost);
	 			
	 			/*Comment commentObject = new Comment();*/
	 			
	 			comment.setComments(comment.getComments());
	 			comment.setCommentBy(comment.getCommentBy());
	 			comment.setCommentOnPost(fetchPost);
	 			session.save(comment);
	 			tx.commit();
	 			
	 			
				return null;
			}







			@Override
			public List<CommentOnPost> getAllComments(int postId) {
				SessionFactory sFactory=MyUtil.getSessionFactory();
				Session session=sFactory.openSession();
				
				
				// checking for pre existing otp		
				    Query query=session.createQuery("from CommentOnPost where commentonpost_postid=:postId");	    
				    query.setInteger("postId", postId );	    
		   
				    List<CommentOnPost> allCommentList =  (List<CommentOnPost>) query.list();
				   
				
				   System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ "+ allCommentList);
				   
				return allCommentList;
			}







			@Override
			public String removeMypost(int postid) {
				
				SessionFactory sFactory=MyUtil.getSessionFactory();
	 			Session session=sFactory.openSession();
	 			Transaction tx=session.beginTransaction();
	 			
	 		
	 			
	 			Post getMyPostToDelete = (Post) session.get(Post.class, postid);
	 			
	 			session.delete(getMyPostToDelete);
	 			
	 			tx.commit();
	 			
				
				
				
				
				return "POST DELETED SUCCESSFULLY ";
			}
	 		
	 		
	 		

	

}
