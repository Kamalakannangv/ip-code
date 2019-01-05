package ip.security.authentication.srp;

import java.math.BigInteger;

import com.nimbusds.srp6.SRP6CryptoParams;

public class SRPConfig {
	
	public static final String REGISTER_ENDPOINT = "/register";
	public static final String AUTHENTICATE_ENDPOINT = "/authenticate";
	
	public static final int SRP_N_PRIME_NUMBER_BIT_SIZE = 512; // Possible values are 256, 512, 768, 1024, 1536, 2048
	// Generator 'g' parameter 
	public static final BigInteger SRP_G_GENERATOR = BigInteger.valueOf(2);; // Always 2 with SRP version 6.0???? 
	// hash algorithm supported by the default security provider of the underlying Java runtime.
	public static final String SRP_HASH_ALGORITHM = "SHA-1";
	
	public static final int SRP_SALT_BYTE_SIZE = 16;
	
	
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_USER_AGENT = "User-Agent";
	public static final String HEADER_AUTH_STEP = "Auth-Step";
	
	
	public static final String SESSIONID = "SessionId";
	public static final String USERNAME = "Username";
	public static final String SALT = "Salt";
	public static final String VERIFIER = "Verifier";
	public static final String A = "A";
	public static final String B = "B";
	public static final String M1 = "M1";
	public static final String M2 = "M2";
	
	public static final SRP6CryptoParams config = new SRP6CryptoParams(getNPrimeNumberByBitSize(SRPConfig.SRP_N_PRIME_NUMBER_BIT_SIZE), SRPConfig.SRP_G_GENERATOR, SRPConfig.SRP_HASH_ALGORITHM);

	private static BigInteger getNPrimeNumberByBitSize(int bitsize){
		switch (bitsize) {
		case 256:
			return SRP6CryptoParams.N_256;
		case 512:
			return SRP6CryptoParams.N_512;
		case 768:
			return SRP6CryptoParams.N_768;
		case 1024:
			return SRP6CryptoParams.N_1024;
		case 1536:
			return SRP6CryptoParams.N_1536;
		case 2048:
			return SRP6CryptoParams.N_2048;
		}
		return null;
	}
}

enum AuthenticationStep{
	
	STEP1,
	STEP2,
	STEP3
	
}
