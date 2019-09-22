package org.autumn.spring.argsbind.rsa;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public abstract class RSAUtils {

    /**
     * 加密算法RSA
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 随机数
     */
    private static final SecureRandom random = new SecureRandom();

    /**
     * RSA秘钥对
     */
    public static final String[] RSA_PAIR = RSAUtils.getKeyPair();

    /**
     * 生成RSA密钥对
     */
    public static String[] getKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(1024, random);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            String privateKey = encodeToString(keyPair.getPrivate().getEncoded());
            String publicKey = encodeToString(keyPair.getPublic().getEncoded());
            return new String[]{publicKey, privateKey};
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 公钥加密
     *
     * @param plainText
     * @param publicKey
     * @return
     */
    public static String encryptByPublicKey(String plainText, String publicKey) {
        Cipher cipher = getCipher(true, publicKey, true);
        byte[] src = plainText.getBytes();
        byte[] datas = cipherData(cipher, src, 117);
        return encodeToString(datas);
    }

    /**
     * 私钥解密
     *
     * @param encryptText
     * @param privateKey
     * @return
     */
    public static String decryptByPrivateKey(String encryptText, String privateKey) {
        Cipher cipher = getCipher(false, privateKey, false);
        byte[] src = decodeFromString(encryptText);
        byte[] datas = cipherData(cipher, src, 128);
        return new String(datas);
    }

    private static String encodeToString(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private static byte[] decodeFromString(String src) {
        return Base64.getDecoder().decode(src);
    }

    private static PublicKey getPublicKey(String publicKey) {
        try {
            byte[] bytes = decodeFromString(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(bytes);
            PublicKey pk = KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(x509KeySpec);
            return pk;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PrivateKey getPrivateKey(String privateKey) {
        try {
            byte[] bytes = decodeFromString(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(bytes);
            PrivateKey pk = KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(pkcs8KeySpec);
            return pk;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Cipher getCipher(boolean encode, String keyString, boolean isPublicKey) {
        try {
            Key key = isPublicKey ? getPublicKey(keyString) : getPrivateKey(keyString);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(encode ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, key);
            return cipher;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] cipherData(Cipher cipher, byte[] src, int blockSize) {
        ByteArrayOutputStream out = null;
        try {
            int inputLen = src.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > blockSize) {
                    cache = cipher.doFinal(src, offSet, blockSize);
                } else {
                    cache = cipher.doFinal(src, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * blockSize;
            }
            return out.toByteArray();
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }
}
