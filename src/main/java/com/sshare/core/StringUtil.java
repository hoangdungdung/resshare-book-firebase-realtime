package com.sshare.core;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {

	public static String removeAccent(String s) {

		if( s==null)
			return null;
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		String keyEncode1 = pattern.matcher(temp).replaceAll("");
		String keyEncode2 = keyEncode1.replaceAll("\\s+", " ");
		String keyEncode3 = keyEncode2.trim();
		String keyEncode4 = keyEncode3.toUpperCase();
		String keyEncode5 = keyEncode4.replaceAll("�", "D");
		String keyEncode6 = keyEncode5.replaceAll("@", " AQ ");
		String keyEncode61 = keyEncode6.replaceAll("\\.", "");

		String keyEncode7 = keyEncode61.replaceAll("\\.", " DOT ");
		
		String keyEncode8 = keyEncode7.replaceAll("Đ", "D");
		String keyEncode81 = keyEncode8.trim();
		return 	  keyEncode81;

	}
}
