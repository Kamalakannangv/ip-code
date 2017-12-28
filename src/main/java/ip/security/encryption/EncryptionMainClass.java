package ip.security.encryption;

public class EncryptionMainClass {
	
	public static void main(String[] args) {
		
		/*
		*********  Comment used to generate KeyStore  **********
		* keytool -genseckey -keystore D:\Work\Workspace\Interview_Preparation\ip-code\Encryption\keystore-file.jck -storetype jceks -storepass mystorepass -keyalg AES -keysize 256 -alias jceksaes -keypass mykeypass
		*/
		
		String keyStoreFileNamePath = "D:/Work/Workspace/Interview_Preparation/ip-code/Encryption/keystore-file_128.jck";
		String keyStorePassword = "mystorepass";
		String keyAlias = "jceksaes";
		String keyPassword = "mykeypass";
		String ivspec = "abcdefghijklmnop";
		Cryptographer cryptographer = new Cryptographer(keyStoreFileNamePath, 
				keyStorePassword, keyAlias, keyPassword, ivspec);
		
		String originalString = "Kamal";
		System.out.println("Orginal String:"+originalString);
		String encryptedString = cryptographer.encrypt(originalString);
		System.out.println("Encrypted String:"+encryptedString);
		String decryptedString = cryptographer.decrypt(encryptedString);
		System.out.println("Decrypted String:"+decryptedString);
		
	}

}
