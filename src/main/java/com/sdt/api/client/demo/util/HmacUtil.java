package com.sdt.api.client.demo.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * hmac公用方法类
 */
public class HmacUtil {
	public static final String CHARSET_UTF8 = "UTF-8";

	public static final String KEY_MAC = "HmacMD5";

	private static final String HEXSTR = "0123456789ABCDEF";

	private static String[] binaryArray = { "0000", "0001", "0010", "0011",
			"0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
			"1100", "1101", "1110", "1111" };

	

	/**
	 * hmac-md5签名
	 * 
	 * @param data
	 * @param secret
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static byte[] encryptHMAC(String data, String secret)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		byte[] bytes = null;
		SecretKey secretKey = new SecretKeySpec(secret.getBytes(CHARSET_UTF8),
				KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		bytes = mac.doFinal(data.getBytes(CHARSET_UTF8));
		return bytes;
	}

	/**
	 * 将byte转化为十六进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sign.append(String.valueOf(HEXSTR.charAt((bytes[i] & 0xF0) >> 4))); // 字节高4位
			sign.append(String.valueOf(HEXSTR.charAt(bytes[i] & 0x0F))); // 字节低4位
		}
		return sign.toString().toUpperCase();
	}

	/**
	 * 
	 * @param hexString
	 * @return 将十六进制转换为字节数组
	 */
	public static byte[] hexToBinary(String hexString) {
		// hexString的长度对2取整，作为bytes的长度
		int len = hexString.length() / 2;
		byte[] bytes = new byte[len];
		for (int i = 0; i < len; i++) {
			// 右移四位得到高位
			byte high = (byte) ((HEXSTR.indexOf(hexString.charAt(2 * i))) << 4);// 字节高四位
			byte low = (byte) HEXSTR.indexOf(hexString.charAt(2 * i + 1));// 字节低四位
			bytes[i] = (byte) (high | low);// 高地位做或运算
		}
		return bytes;
	}

	/**
	 * 
	 * @param str
	 * @return 转换为二进制字符串
	 */
	public static String bytes2BinaryStr(byte[] bArray) {
		String outStr = "";
		for (byte b : bArray) {
			// 高四位
			int pos = (b & 0xF0) >> 4;
			outStr += binaryArray[pos];
			// 低四位
			pos = b & 0x0F;
			outStr += binaryArray[pos];
		}
		return outStr;

	}
}
