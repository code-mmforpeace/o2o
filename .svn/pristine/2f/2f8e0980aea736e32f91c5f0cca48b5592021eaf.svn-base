package com.ouver.o2o.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;


//DES算法，用来对关键信息进行加密,对称算法，即加密和解密都是采用一样的算法
//key，data，model
public class DESUtil {

    private static Key key;
    private static String KEY_STR = "myKey";
    private static String CHARSETNAME = "UTF-8";
    private static String ALGORITHM = "DES";

    static {
        try {
            //生成des算法对象
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            //运用sha1的安全策略
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            //设置上密钥种子
            secureRandom.setSeed(KEY_STR.getBytes());
            //基于SHA1算法初始化对象
            generator.init(secureRandom);
            //生成密钥对象
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get jiami
     * @param str
     * @return
     */
    public static String getEncryptString(String str) {
        //基于base64编码，接受byte[]并转换成string
        Encoder encoder = Base64.getEncoder();
        try {
            //声明编码规则为utf8
            byte[] bytes = str.getBytes(CHARSETNAME);
            //获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //
            byte[] doFinal = cipher.doFinal(bytes);
            return encoder.encodeToString(doFinal);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }


    /**
     * 解密 decode
     * @param str
     * @return
     */
    public static String getDecryptString(String str) {
        Decoder decoder = Base64.getDecoder();
        try {
            byte[] bytes = decoder.decode(str);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] doFinal = cipher.doFinal(bytes);
            return new String(doFinal, CHARSETNAME);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getEncryptString("root"));
        System.out.println(getEncryptString("rootroot"));
        System.out.println(getEncryptString("wxd7f6c5b8899fba83"));
        System.out.println(getEncryptString("665ae80dba31fc91ab6191e7da4d676d"));
    }

}