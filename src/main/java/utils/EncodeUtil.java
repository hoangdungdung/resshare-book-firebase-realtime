package utils;

import java.security.MessageDigest;
import java.util.Base64;

import model.Books;

public class EncodeUtil {

	public static String base64encode(Books book) {
		try {
			String key = book.getName().toLowerCase();
			byte[] bytesOfMessage = key.getBytes("UTF-8");

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < thedigest.length; ++i) {
				sb.append(Integer.toHexString((thedigest[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String base64encode_Nha(Books book) {
		try {
			String key = book.getName().toLowerCase() + book.getAuthor_id() + book.getCategory_id();
			byte[] bytesOfMessage = key.getBytes("UTF-8");

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < thedigest.length; ++i) {
				sb.append(Integer.toHexString((thedigest[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String base64encode(String src) {
		try {
			byte[] bytesOfMessage = src.getBytes("UTF-8");

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < thedigest.length; ++i) {
				sb.append(Integer.toHexString((thedigest[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
