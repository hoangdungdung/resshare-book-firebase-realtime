/**
 * 
 */
package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Admin
 *
 */
public final class Barcode {

	/**
	 * 
	 */
	public Barcode() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		URL url;

		try {
			String s1 = "";
			String s2 = "";
			String s3 = "";
			String s4 = "";
			String s5 = "";
			String s6 = "";
			String s7 = "";
			String s8 = "";
			String s9 = "";
			 
			// get URL content

			String a = "http://www.minhkhai.com.vn/store2/index.aspx?q=view&isbn=8935251406214";
			//9786047221585
			  a = "http://www.minhkhai.com.vn/store2/index.aspx?q=view&isbn=8935251406214";
			url = new URL(a);
			URLConnection conn = url.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			int i = 0;
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
			//	System.out.println(inputLine);
				String s = "Tác giả";
				s ="<b>ISBN:";

				 if ((i>0)&(i<100))
				 {
					 i++;
						System.out.println(inputLine);
				 }
				 if (inputLine.startsWith(s))

				{ i++;
					System.out.println(s1);
					System.out.println(s2);
					System.out.println(s3);
					System.out.println(s4);
					System.out.println(s5);
					System.out.println(s6);
					System.out.println(s7);
					System.out.println(s8);
					System.out.println(s9);
					 
					System.out.println(inputLine);

				}

				s1 = s2;
				s2 = s3;
				s3 = s4;
				s4 = s5;
				s5 = s6;
				s6 = s7;
				s7 = s8;
				s8 = s9;
				s9 = inputLine;
				
			}
			br.close();

	 

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}