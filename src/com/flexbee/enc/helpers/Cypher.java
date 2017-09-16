package com.flexbee.enc.helpers;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
//import java.util.Arrays;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
  
import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.cipher.CryptoCipherFactory.CipherProvider;
import org.apache.commons.crypto.utils.Utils;

public class Cypher {
	public byte[] encrypt(byte[] data) throws IOException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("1234567890123456"),"AES");
		final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("1234567890123456"));
		Properties properties = new Properties();
	    properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CipherProvider.OPENSSL.getClassName());
	    //Creates a CryptoCipher instance with the transformation and properties.
	    final String transform = "AES/CBC/PKCS5Padding";
	    CryptoCipher encipher = Utils.getCipherInstance(transform, properties);
	    System.out.println("Cipher:  " + encipher.getClass().getCanonicalName());
	    
	    int length = data.length;
	    byte[] output = new byte[length];
	    //Initializes the cipher with ENCRYPT_MODE, key and iv.
	    encipher.init(Cipher.ENCRYPT_MODE, key, iv);
        //Continues a multiple-part encryption/decryption operation for byte array.
        int updateBytes = encipher.update(data, 0, data.length, output, 0);
        System.out.println(updateBytes);
        //We must call doFinal at the end of encryption/decryption.
        int finalBytes = encipher.doFinal(data, 0, 0, output, updateBytes);
        System.out.println(finalBytes);
        //Closes the cipher.
        encipher.close();
        return output;
	          //System.out.println(Arrays.toString(Arrays.copyOf(output, updateBytes+finalBytes)));
	}
	
	private static byte[] getUTF8Bytes(String input) {
		return input.getBytes(StandardCharsets.UTF_8);
	}

}
