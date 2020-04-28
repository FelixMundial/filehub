package com.example.filehub.commons.service.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @deprecated
 * @author yinfelix
 * @date 2020/3/8
 */
public class RsaUtils {
    public static final int DEFAULT_KEY_SIZE = 2048;

    public static PublicKey getPublicKeyFromFile(String filename) {
        byte[] bytes = readFile(filename);
        return getPublicKey(bytes);
    }

    public static PrivateKey getPrivateKeyFromFile(String filename) {
        byte[] bytes = readFile(filename);
        return getPrivateKey(bytes);
    }

    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret, int keySize) {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        publicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        writeFile(publicKeyFilename, publicKeyBytes);

        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        privateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
        writeFile(privateKeyFilename, privateKeyBytes);
    }

    private static PublicKey getPublicKey(byte[] bytes) {
        bytes = Base64.getDecoder().decode(bytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        PublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    private static PrivateKey getPrivateKey(byte[] bytes) {
        bytes = Base64.getDecoder().decode(bytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    private static byte[] readFile(String filename) {
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(new File(filename).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static void writeFile(String filename, byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileChannel fileChannel = new FileOutputStream(file).getChannel()) {
            fileChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
