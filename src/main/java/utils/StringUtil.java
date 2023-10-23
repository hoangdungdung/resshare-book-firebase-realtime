package utils;

public class StringUtil {

	public static boolean like(String str, String key) {
		key = key.toLowerCase(); // ignoring locale for now
		key = key.replace(".", "\\."); // "\\" is escaped to "\" 
		key = key.replace("?", ".");
		key = key.replace("%", ".*");
	    str = str.toLowerCase();
	    return str.contains(key) || key.contains(str);
	}
}
