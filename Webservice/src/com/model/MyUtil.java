package com.model;

import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;

public class MyUtil {
	
	 

	private static SessionFactory sessionFactory = buildSessionFactory();
	private static SessionFactory buildSessionFactory() {
		try
	      {
	         if (sessionFactory == null)
	         {
	        	 AnnotationConfiguration configuration = new AnnotationConfiguration().configure();	           
	             sessionFactory = configuration.buildSessionFactory();
	         }
	         return sessionFactory;
	      } catch (Throwable ex)
	      {
	         System.err.println("Initial SessionFactory creation failed." + ex);
	         throw new ExceptionInInitializerError(ex);
	      }
	   }
	 
	   public static SessionFactory getSessionFactory()
	   {
	      return sessionFactory;
	   }
}
	 