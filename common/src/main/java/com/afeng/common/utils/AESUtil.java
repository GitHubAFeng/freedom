package com.afeng.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author 付为地
 * AES工具类
 */
public class AESUtil {

    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

    //密钥
    private final static String KEY = "!@#$%^&*(-free2020";

    /**
     * AES加密
     *
     * @param content 源数据
     * @param Aeskey  加密私钥
     * @return 加密结果
     */
    public static String encrypt(String content, String Aeskey) {
        try {
            if (StringUtils.isBlank(Aeskey)) {
                Aeskey = KEY;
            }
            logger.debug("执行AESUtil.encrypt加密,传入参数:content=[" + content + "] Aeskey=[" + Aeskey + "]");
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(Aeskey.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            return byteArr2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行AESUtil.encrypt加密时异常:" + e);
        }
        return null;
    }

    public static String encrypt(String content) {
        return encrypt(content, null);
    }

    /**
     * AES解密
     *
     * @param content 源数据
     * @param Aeskey  解密私钥
     * @return 解密结果
     */
    public static String decrypt(String content, String Aeskey) {
        try {
            if (StringUtils.isBlank(Aeskey)) {
                Aeskey = KEY;
            }
            logger.debug("执行AESUtil.decrypt解密,传入参数:content=[" + content + "] Aeskey=[" + Aeskey + "]");
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");// 修复linux系统报错问题
            secureRandom.setSeed(Aeskey.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(hexStr2ByteArr(content));
            return new String(result, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行AESUtil.decrypt解时异常:" + e);
        }
        return null;
    }

    public static String decrypt(String content) {
        return decrypt(content, null);
    }

    /*
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     */
    private static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /*
     * 将byte数组转换为表示16进制值的字符串
     * hexStr2ByteArr(String strIn)
     */
    private static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        String result = sb.toString();
        return result;
    }


//    public static void main(String[] args) {
//        String text = "apple";
//        System.err.println("源码:" + text);
//        System.err.println("加密:" + new String(encrypt(text, KEY)));
//        System.err.println("解密:" + new String(decrypt(new String(encrypt(text, KEY)), KEY)));
//
//        User usr = new User();
//        usr.setAge(10);
//        usr.setName("张三");
//        usr.setPassword("123456");
//        usr.setSex(1);
//        byte[] source = SerializationUtils.serialize(usr);
//        User usr1 = (User) SerializationUtils.deserialize(source);
//        System.err.println(usr1.getName() + "\t" + usr1.getAge() + "\t" + usr1.getPassword());
//        Date dt = DateUtil.parseDate("2018-05-06 12:05:00");
//        System.err.println(dt.getTime());
//    }

}