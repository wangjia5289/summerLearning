package com.example.securitywithhttpsession.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtils {

    /**
     * ============================================
     * 1. 生成 Base64 编码后的 AES 密钥方法
     * --------------------------------------------
     *
     * ============================================
     */
    public static String generateSecretKey() throws NoSuchAlgorithmException {
        // 创建 AES 密钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        // 指定密钥的位数，AES 支持 128、192、256 位
        keyGenerator.init(256);
        // 生成密钥
        SecretKey secretKey = keyGenerator.generateKey();
        // 将密钥编码为 Base64 字符串
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * ============================================
     * 2. 加密方法
     * --------------------------------------------
     * 传入参数：
     * - String plainText
     *      - 要加密的明文字符串
     * - String base64Key
     *      - Base64 编码后的 AES 密钥字符串
     * ============================================
     */
    public static String encrypt(String plainText, String base64Key) throws Exception {

        // 把传入的 Base64 编码密钥转换为字节数组，供 AES 使用
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);

        // 用字节数组创建一个 AES 对称密钥对象，供后续加密初始化使用
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // 获取 Cipher 实例，使用 AES 算法
        Cipher cipher = Cipher.getInstance("AES");

        // 初始化加密器，设置为加密模式，并指定使用的密钥
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // 执行加密，得到密文字节数组
        byte[] encrypted = cipher.doFinal(plainText.getBytes());

        // 把密文字节用 Base64 编码，便于作为字符串返回、存储或传输
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * ============================================
     * 3. 解密密方法
     * --------------------------------------------
     * 传入参数：
     * - String encryptedText
     *      - 加密后的密文字符串
     * - String base64Key
     *      - Base64 编码后的 AES 密钥字符串
     * ============================================
     */
    public static String decrypt(String encryptedText, String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decrypted);
    }
}