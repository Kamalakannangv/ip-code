package ip.security.soapwebservice.passworddigest;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.TimeZone;

public class PasswordDigestMainClass {

	static String password = "password";

	public static void main(String[] args) {

		try {
			Client client = new Client();
			Server server = new Server();
			SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
			rand.setSeed(System.currentTimeMillis());
			byte[] nonceBytes = new byte[16];
			rand.nextBytes(nonceBytes);

			//Make the created date
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			df.setTimeZone(TimeZone.getTimeZone("UTC"));

			String createdDate = df.format(Calendar.getInstance().getTime());
			System.out.println("Created Date : " + createdDate);
			String passwordDigest = client.createPasswordDigest(createdDate, nonceBytes, password);

			server.validateUser((Base64.getEncoder().encodeToString(nonceBytes)), createdDate, password, passwordDigest);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
