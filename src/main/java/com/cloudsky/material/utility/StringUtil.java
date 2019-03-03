package com.cloudsky.material.utility;


import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import sun.misc.BASE64Decoder;




public class StringUtil {
	
	private static long orderNum = 0l;  
    private static String date ;
	
	public static String ifNull(Object obj){
		if(obj==null){
			return "";
		}
		else{
			return obj.toString();
		}
	}
	
	
	public static String formatDecimal(Object object){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(object);
	}


	
	public static String encryptByMd5(String str) {
		MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        StringBuffer hexValue = new StringBuffer();
        try{
        	byte[] byteArray = str.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        }
        catch (UnsupportedEncodingException e) {
        	System.out.println(e.toString());
        	e.printStackTrace();
		}
        
        return hexValue.toString();
	}
	
	
	public static String getRandomNumber() {
		StringBuilder sb = new StringBuilder();// 字串对象
		Random random = new Random();// 随机对象
		for (int i = 0; i < 4; i++) {// 循环产生随机数字串
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	 public static String getIp() throws Exception{
		  
		  String ip = InetAddress.getLocalHost().getHostAddress();  
		  return ip;
	  }	  
	 
	 
	 public static byte[] base64ToBytes(String base64Str, String fileType) throws Exception {
		 
		 BASE64Decoder decoder = new BASE64Decoder(); 
		 byte[] bytes = decoder.decodeBuffer(base64Str.replace("data:image/" + fileType + ";base64,", ""));
		 
		 for(int j=0;j<bytes.length;++j)  
         {  
             if(bytes[j]<0)  
             {//调整异常数据  
             	bytes[j]+=256;  
             }  
         }  
		 
		 return bytes;
	 }
	 
	/* public static synchronized String getOrderNo() {  
	        String str = new SimpleDateFormat("yyMMddHHmmss").format(new Date());  
	        if(date==null||!date.equals(str)){  
	            date = str;  
	            orderNum  = 0l;  
	        }  
	        orderNum ++;  
	        long orderNo = Long.parseLong((date));  
	        orderNo += orderNum;;  
	        return orderNo+"";  
	    }   */
}