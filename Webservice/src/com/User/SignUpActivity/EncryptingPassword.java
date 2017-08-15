package com.User.SignUpActivity;
import javax.xml.bind.annotation.XmlRootElement;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;


@XmlRootElement
public class EncryptingPassword {

    private static String mpCryptoPassword = "AZQSXWDCMLPOKNJIUYTEFCVBGHGHHG";
    
    String encryptedPassword="";

	public String encrypt(String password) {
		
		
		
		System.out.println("|||||||||||||||||||||     "+encryptedPassword);

		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(mpCryptoPassword);

		encryptedPassword = encryptor.encrypt(password);
		System.out.println(encryptedPassword);

		return encryptedPassword;
        
         
    }

	
public String decrypt(String password) {
		

	  StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
      
      decryptor.setPassword(mpCryptoPassword);
      String str =  decryptor.decrypt(password);
      
    System.out.println("############### "+ str);
      
    //decryptor.setPassword("Dvul/3qlf4a4wDj2fNpBZGQqPHC0XRnu");
      

		return str;
        
         
    }
	
}