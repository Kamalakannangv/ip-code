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

public class SRPClient {

	private static final Charset UTF8_CHARSET = Charset.forName("UTF8");
	private static final int HKDF_DERIVED_KEY_SIZE = 16;
	private static final String HKDF_DERIVED_KEY_INFO = "Caldera Derived Key"; //Amazon Cognito specific
	private static final String SRP_HASH_ALGORITHM = "SHA-256";
	private static final int CLIENT_RANDOM_NUM_KEY_LENGTH = 2048; 
	private static final String PRNG_ALGORITHM = "SHA1PRNG";
	private static final BigInteger N = new BigInteger("5809605995369958062791915965639201402176612226902900533702900882779736177890990861472094774477339581147373410185646378328043729800750470098210924487866935059164371588168047540943981644516632755067501626434556398193186628990071248660819361205119793693985433297036118232914410171876807536457391277857011849897410207519105333355801121109356897459426271845471397952675959440793493071628394122780510124618488232602464649876850458861245784240929258426287699705312584509625419513463605155428017165714465363094021609290561084025893662561222573202082865797821865270991145082200656978177192827024538990239969175546190770645685893438011714430426409338676314743571154537142031573004276428701433036381801705308659830751190352946025482059931306571004727362479688415574702596946457770284148435989129632853918392117997472632693078113129886487399347796982772784615865232621289656944284216824611318709764535152507354116344703769998514148343807");
	private static final BigInteger g = BigInteger.valueOf(2);

	private MessageDigest messageDigest;
	private BigInteger a;
	private BigInteger A;
	private BigInteger k;
	private String hmacAlgorithm;

	private final byte[] EMPTY_ARRAY = new byte[0];

	public SRPClient(String hmacAlgorithm){
		this.hmacAlgorithm = hmacAlgorithm;
		init();
	}

	private void init(){
		try {
			SecureRandom secureRandom = SecureRandom.getInstance(PRNG_ALGORITHM);
			messageDigest = MessageDigest.getInstance(SRP_HASH_ALGORITHM);
			messageDigest.reset();
			messageDigest.update(N.toByteArray());
			byte[] digest = messageDigest.digest(g.toByteArray());
			k = new BigInteger(1, digest);

			do {
				a = new BigInteger(CLIENT_RANDOM_NUM_KEY_LENGTH, secureRandom).mod(N);
				A = g.modPow(a, N);
			} while (A.mod(N).equals(BigInteger.ZERO));

		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e.getMessage(), e);
		}
	}

	public BigInteger getA(){
		return A;
	}

	public byte[] getSRPAuthenticationKey(String userId, String userPassword, BigInteger B, BigInteger salt) {
		if (B.mod(N).equals(BigInteger.ZERO)) {
			throw new SecurityException("SRP error, B cannot be zero");
		}

		// u = H(A, B)
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
