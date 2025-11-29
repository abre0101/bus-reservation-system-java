package util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Utility class for password encryption and decryption
 * WARNING: In production, use bcrypt or Argon2 for password hashing
 */
public class EncryptionUtil {
    
    private static final int KEY_LENGTH = 256;
    private static final int ITERATION_COUNT = 65536;
    
    // TODO: Move these to environment variables or secure configuration
    private static final String SECRET_KEY = "12_win";
    private static final String SALT = "_gm+";
    
    /**
     * Encrypt a password
     * @param password Plain text password
     * @return Encrypted password as Base64 string
     * @throws Exception if encryption fails
     */
    public static String encrypt(String password) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), ITERATION_COUNT, KEY_LENGTH);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);
        byte[] cipherText = cipher.doFinal(password.getBytes("UTF-8"));
        
        byte[] encryptedData = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, encryptedData, 0, iv.length);
        System.arraycopy(cipherText, 0, encryptedData, iv.length, cipherText.length);
        
        return Base64.getEncoder().encodeToString(encryptedData);
    }
    
    /**
     * Decrypt an encrypted password
     * @param encryptedPassword Encrypted password as Base64 string
     * @return Decrypted password
     * @throws Exception if decryption fails
     */
    public static String decrypt(String encryptedPassword) throws Exception {
        byte[] encryptedValue = Base64.getDecoder().decode(encryptedPassword);
        
        byte[] iv = new byte[16];
        System.arraycopy(encryptedValue, 0, iv, 0, iv.length);
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), ITERATION_COUNT, KEY_LENGTH);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);
        
        byte[] cipherText = new byte[encryptedValue.length - 16];
        System.arraycopy(encryptedValue, 16, cipherText, 0, cipherText.length);
        byte[] decryptedText = cipher.doFinal(cipherText);
        
        return new String(decryptedText, "UTF-8");
    }
}
