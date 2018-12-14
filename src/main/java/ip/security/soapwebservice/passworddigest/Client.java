package ip.security.soapwebservice.passworddigest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Client {
	
	public String createPasswordDigest (String createdDate, byte[] nonceBytes, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException{

		
		byte[] createdDateBytes = createdDate.getBytes("UTF-8");

		//Make the password
		byte[] passwordBytes = password.getBytes("UTF-8");

		//SHA-1 hash the bunch of it.
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(nonceBytes);
		baos.write(createdDateBytes);
		baos.write(passwordBytes);
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] digestedPassword = md.digest(baos.toByteArray());

		//Encode the password and nonce for sending                   
		String passwordB64 = Base64.getEncoder().encodeToString(digestedPassword);
		String nonceB64 = Base64.getEncoder().encodeToString(nonceBytes);

		System.out.println( " Nonce : " + nonceB64);
		System.out.println(" Digest : " + passwordB64);

		return passwordB64;
	}

}
