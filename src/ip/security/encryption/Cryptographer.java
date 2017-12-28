package ip.security.encryption;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Cryptographer {
	
	private String keyStoreFileNamePath;
	private String keyStorePassword;
	private String keyAlias;
	private String keyPassword;
	private IvParameterSpec ivspec;
	private static final String CIPHER_METHOD_TYPE = "AES/CBC/PKCS5Padding";
	private static final String KEY_STORE_TYPE = "JCEKS";
	private static final String CHARSET_ISO = "ISO-8859-1";
	
	public Cryptographer(String keyStoreFileNamePath, String keyStorePassword, String keyAlias, 
			String keyPassword, String ivspec){
		this.keyStoreFileNamePath = keyStoreFileNamePath;
		this.keyStorePassword = keyStorePassword;
		this.keyAlias = keyAlias;
		this.keyPassword = keyPassword;
		this.ivspec = new IvParameterSpec(ivspec.getBytes());
	}
	
	public String encrypt(String data){
		String encryptedString = null;
		if(data != null){
			try {
				Cipher cipher = Cipher.getInstance(CIPHER_METHOD_TYPE);
				Key key = getKey();
				cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
				byte[] encryptedBytes = cipher.doFinal(data.getBytes(CHARSET_ISO));
				//encryptedString = BaseEncoding.base64().encode(encrypted);
				byte[] base64EncodedEncryptedBytes = Base64.getEncoder().encode(encryptedBytes);
				encryptedString = new String(base64EncodedEncryptedBytes, CHARSET_ISO);
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return encryptedString;
	}
	
	public String decrypt(String encryptedData){
		String decryptedString = null;
		if(encryptedData != null){
			try {
				byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData.getBytes(CHARSET_ISO));
				//byte[] encryptedTextBytes = BaseEncoding.base64().decode(encryptedData);
				Cipher cipher = Cipher.getInstance(CIPHER_METHOD_TYPE);
				Key key = getKey();
				cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
				byte[] decrypt = cipher.doFinal(encryptedBytes);
				decryptedString = new String(decrypt, CHARSET_ISO);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return decryptedString;
	}
	
	private Key getKeyFromKeyStore(){
		InputStream keystoreStream;
		try {
			keystoreStream = new FileInputStream(keyStoreFileNamePath);
			KeyStore keystore = KeyStore.getInstance(KEY_STORE_TYPE);
			keystore.load(keystoreStream, keyStorePassword.toCharArray());
			if (keystore.containsAlias(keyAlias)) {
				Key key = keystore.getKey(keyAlias, keyPassword.toCharArray());
				return key;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Key getKey(){
		byte[] keyByteArray = "password".getBytes();
		SecretKeySpec secretKeySpec = null;
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			keyByteArray = sha.digest(keyByteArray);
			keyByteArray = Arrays.copyOf(keyByteArray, 16);
			secretKeySpec = new SecretKeySpec(keyByteArray, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return secretKeySpec;
		
	}
	
	

}
