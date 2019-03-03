package com.cloudsky.material.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

public class EncryptUtil {

	/**
	 * MD5加密
	 * 
	 * @param str
	 *            待加密的字符串
	 * @return 加密后的字符串
	 */
	public static String encryptByMd5(String str) {
		// 确定计算方法
		MessageDigest md;
		String newstr = str;
		try {
			md = MessageDigest.getInstance("MD5");
			// JDK中提供了非常方便的BASE64Encoder和BASE64Decoder,用它们可以非常方便的完成基于BASE64的编码和解码。
			BASE64Encoder base64en = new BASE64Encoder();
			// 加密后的字符串
			newstr = base64en.encode(md.digest(str.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newstr;
	}

	
}