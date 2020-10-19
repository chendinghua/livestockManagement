package com.kymjs.app.base_res.utils.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***
 * MD5 һ����������
 * @author PassWrod
 *
 */
public class MD5 {

	public static String ganerateMD5(String input){
		MessageDigest md5;
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'}; //Ҫת����16�����ַ�
		char[]str=new char[32];
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[]source=input.getBytes("utf-8");	
			md5.update(source);
			byte[] hashCode = md5.digest();
			for(int i=0,j=0;i<16;i++){
				byte b=hashCode[i];
				//ȡ�ֽ��и�4λ������ת�����޷���������
				str[j++]=hexDigits[b>>>4&0xf];
				str[j++]=hexDigits[b&0xf];
			}
			
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new String(str);
	}
}
