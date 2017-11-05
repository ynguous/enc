package com.flexbee.enc.helpers;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypt {
	private static final int PASSWORD_ITERATIONS = 65536; // vs brute force
    private static final int KEY_LENGTH          = 16; //128;
    private String _keyStr = "";

    private char[]     pass                = "password".toCharArray(); // hardcoded or read me from a file
    private byte[]     salt                = new byte[20]; // for more confusion
    private byte[]     ivBytes             = null;

    public AESCrypt(String keyStr) {
        _keyStr = keyStr;
//        SecureRandom secureRandom = new SecureRandom(); // seed is 0
//        secureRandom.setSeed(secureRandom.generateSeed(16));
//        secureRandom.nextBytes(salt);
    }

    private byte[] fixSecret(String s, int length) throws UnsupportedEncodingException {
    	if (s.length() < length) {
			int missingLength = length - s.length();
			for (int i = 0; i < missingLength; i++) {
				s += " ";
			}
		}
		return s.substring(0, length).getBytes("UTF-8");
	}
    
    private Cipher createCipher(boolean encryptMode) throws Exception {

//        if (!encryptMode && ivBytes == null) {
//            throw new IllegalStateException("ivBytes is null");
//        }

        //SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        //PBEKeySpec spec = new PBEKeySpec(pass, salt, PASSWORD_ITERATIONS, KEY_LENGTH);
        
        byte[] key = new byte[KEY_LENGTH];
		//key = fixSecret("!@#$MySecr3tPassw0rd", KEY_LENGTH);
		key = fixSecret(_keyStr, KEY_LENGTH);
		String algorithm = "AES";
		SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);
		
        //SecretKey secretKey = factory.generateSecret(spec);
        //SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        
        

        //Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        int mode = encryptMode ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;

//        int[] iArray = new int[] {-91, -93, -127, -68, -37, 53, 9, -121, 80, 16, 9, 44, -6, -50, 2, -106};
//        ByteBuffer byteBuffer = ByteBuffer.allocate(iArray.length * 4);        
//        IntBuffer intBuffer = byteBuffer.asIntBuffer();
//        intBuffer.put(iArray);
//        
//        byte[] bArray = new byte[16];
//        bArray[0] = -91; bArray[1] = -93; bArray[0] = -127; bArray[1] = -68; 
//        bArray[0] = -37; bArray[1] = 53; bArray[0] = 9; bArray[1] = -121;
//        bArray[0] = 80; bArray[1] = 16; bArray[0] = 9; bArray[1] = 44;
//        bArray[0] = -6; bArray[1] = -50; bArray[0] = 2; bArray[1] = -106;

        //ivBytes = bArray;
        
        //if (ivBytes == null) {

        cipher.init(mode, secretKey);
        
            //AlgorithmParameters params = cipher.getParameters();
            //ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();

//        } else {
//
//            cipher.init(mode, secretKey, new IvParameterSpec(ivBytes));
//        }

        return cipher;
    }

    //@Override
//    public String encode(String plainText) throws Exception {
    public byte[] encode(byte[] data) throws Exception {

        Cipher cipher = createCipher(true);

        //byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        byte[] encryptedBytes = cipher.doFinal(data);

        //return new String(encryptedBytes);
        return encryptedBytes;

    }

    //@Override
    public String decode(String encodedText) throws Exception {

        Cipher cipher = createCipher(false);

        return new String(cipher.doFinal(encodedText.getBytes()), "UTF-8");
    }
    
    public byte[] decode(byte[] encryptedData) throws Exception {

        Cipher cipher = createCipher(false);

        byte[] data = cipher.doFinal(encryptedData);
        return data;
    }
}
