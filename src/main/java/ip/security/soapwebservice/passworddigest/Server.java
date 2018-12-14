package ip.security.soapwebservice.passworddigest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Server {
	
	public void validateUser(String nonce, String created, String pwd, String passwordDigest) throws IOException, NoSuchAlgorithmException{

		byte[] nonceBytes = Base64.getDecoder().decode(nonce);
		byte[] createdBytes = created.getBytes("UTF-8");
		byte[] passwordBytes = pwd.getBytes("UTF-8");      
		ByteArrayOutputStream outputStream =       new ByteArrayOutputStream( );
		outputStream.write(nonceBytes);
		outputStream.write(createdBytes);
		outputStream.write(passwordBytes);
		byte[] concatenatedBytes = outputStream.toByteArray();
		MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
		digest.update(concatenatedBytes, 0, concatenatedBytes.length);
		byte[] digestBytes = digest.digest();            
		String digestString = Base64.getEncoder().encodeToString(digestBytes);

		String result = "";
		if (digestString.equals(passwordDigest)) {
			result = "valid";
		} else {
			result = "invalid";
		}
		System.out.println("Provided password digest is: "+result);
		System.out.println("   Nonce: " + nonce);
		System.out.println("   Timestamp: " + created);
		System.out.println("   Password: " + pwd);
		System.out.println("   Computed digest: " + digestString);
		System.out.println("   Provided digest: " + passwordDigest);

	}

}
