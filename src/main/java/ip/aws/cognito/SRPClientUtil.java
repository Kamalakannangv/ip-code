package ip.aws.cognito;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SRPClientUtil {

	private static final Charset UTF8_CHARSET = Charset.forName("UTF8");
	private final byte[] EMPTY_ARRAY = new byte[0];
	private static final int HKDF_DERIVED_KEY_SIZE = 16;
	private static final String HKDF_DERIVED_KEY_INFO = "Caldera Derived Key"; //Amazon Cognito specific
	private static final String SRP_HASH_ALGORITHM = "SHA-256";
	private static final int CLIENT_RANDOM_NUM_KEY_LENGTH = 2048; 
	private static final String PRNG_ALGORITHM = "SHA1PRNG";
	private static final String PRIME_NUMBER_N_HEX =
			"FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD1"
					+ "29024E088A67CC74020BBEA63B139B22514A08798E3404DD"
					+ "EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245"
					+ "E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7ED"
					+ "EE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3D"
					+ "C2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F"
					+ "83655D23DCA3AD961C62F356208552BB9ED529077096966D"
					+ "670C354E4ABC9804F1746C08CA18217C32905E462E36CE3B"
					+ "E39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9"
					+ "DE2BCBF6955817183995497CEA956AE515D2261898FA0510"
					+ "15728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64"
					+ "ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7"
					+ "ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6B"
					+ "F12FFA06D98A0864D87602733EC86A64521F2B18177B200C"
					+ "BBE117577A615D6C770988C0BAD946E208E24FA074E5AB31"
					+ "43DB5BFCE0FD108E4B82D120A93AD2CAFFFFFFFFFFFFFFFF";
	private static final BigInteger N = new BigInteger(PRIME_NUMBER_N_HEX, 16);
	private static final BigInteger g = BigInteger.valueOf(2);
	private static final BigInteger k;

	private static final ThreadLocal<MessageDigest> THREAD_MESSAGE_DIGEST =
			new ThreadLocal<MessageDigest>() {
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance(SRP_HASH_ALGORITHM);
			} catch (NoSuchAlgorithmException e) {
				throw new SecurityException("Exception in authentication", e);
			}
		}
	};
	private static final SecureRandom SECURE_RANDOM;

	static {
		try {
			SECURE_RANDOM = SecureRandom.getInstance(PRNG_ALGORITHM);
			MessageDigest messageDigest = THREAD_MESSAGE_DIGEST.get();
			messageDigest.reset();
			messageDigest.update(N.toByteArray());
			byte[] digest = messageDigest.digest(g.toByteArray());
			k = new BigInteger(1, digest);
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e.getMessage(), e);
		}
	}

	private BigInteger a;
	private BigInteger A;
	private String hmacAlgorithm;

	public SRPClientUtil(String hmacAlgorithm){
		this.hmacAlgorithm = hmacAlgorithm;
		generateA();
	}

	private void generateA(){
		do {
			a = new BigInteger(CLIENT_RANDOM_NUM_KEY_LENGTH, SECURE_RANDOM).mod(N);
			A = g.modPow(a, N);
		} while (A.mod(N).equals(BigInteger.ZERO));
	}

	public BigInteger getA(){
		return A;
	}

	public byte[] getSRPAuthenticationKey(String userId, String userPassword, BigInteger B, BigInteger salt) {
		if (B.mod(N).equals(BigInteger.ZERO)) {
			throw new SecurityException("SRP error, B cannot be zero");
		}

		// u = H(A, B)
		MessageDigest messageDigest = THREAD_MESSAGE_DIGEST.get();
		messageDigest.reset();
		messageDigest.update(A.toByteArray());
		BigInteger u = new BigInteger(1, messageDigest.digest(B.toByteArray()));
		if (u.equals(BigInteger.ZERO)) {
			throw new SecurityException("Hash of A and B cannot be zero");
		}

		// This Amazon Cognito specific
		// x = H(salt | H(poolName | userId | ":" | password))
		messageDigest.reset();
		messageDigest.update(Inputs.USER_POOL_ID.split("_", 2)[1].getBytes(UTF8_CHARSET));
		messageDigest.update(userId.getBytes(UTF8_CHARSET));
		messageDigest.update(":".getBytes(UTF8_CHARSET));
		byte[] userIdHash = messageDigest.digest(userPassword.getBytes(UTF8_CHARSET));
		messageDigest.reset();
		messageDigest.update(salt.toByteArray());
		BigInteger x = new BigInteger(1, messageDigest.digest(userIdHash));
		BigInteger S = (B.subtract(k.multiply(g.modPow(x, N))).modPow(a.add(u.multiply(x)), N)).mod(N);

		byte[] key = getHKDFDerivedKey(S.toByteArray(), u.toByteArray());
		return key;
	}

	private byte[] getHKDFDerivedKey(byte[] inputKeyMaterial, byte[] inputSalt){
		final byte[] derivedKey = new byte[HKDF_DERIVED_KEY_SIZE];
		byte[] salt = inputSalt == null ? EMPTY_ARRAY : (byte[]) inputSalt.clone();
		byte[] keyBytes = EMPTY_ARRAY;
		try {
			final Mac mac = Mac.getInstance(hmacAlgorithm);
			if (salt.length == 0) {
				salt = new byte[mac.getMacLength()];
				Arrays.fill(salt, (byte) 0);
			}
			mac.init(new SecretKeySpec(salt, hmacAlgorithm));
			keyBytes = mac.doFinal(inputKeyMaterial);
			final SecretKeySpec key = new SecretKeySpec(keyBytes, hmacAlgorithm);
			Arrays.fill(keyBytes, (byte) 0);
			mac.reset();
			mac.init(key);
			byte[] byteArray = EMPTY_ARRAY;
			try {
				int byteCount = 0;
				for(byte i = 1; byteCount < HKDF_DERIVED_KEY_SIZE; ++i) {
					mac.update(byteArray);
					mac.update(HKDF_DERIVED_KEY_INFO.getBytes(UTF8_CHARSET));
					mac.update(i);
					byteArray = mac.doFinal();
					for (int j = 0; j < byteArray.length && byteCount < HKDF_DERIVED_KEY_SIZE; ++byteCount) {
						derivedKey[byteCount] = byteArray[j];
						++j;
					}
				}
			} finally {
				Arrays.fill(byteArray, (byte) 0);
			}
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return derivedKey;
	}

}
