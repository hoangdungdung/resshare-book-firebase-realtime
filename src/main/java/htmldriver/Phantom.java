package htmldriver;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

//https://www.guru99.com/first-webdriver-script.html
public class Phantom {
	public static void main(String[] args) {
		// seachBook();
	}

	public static String phantomjs;

	public static HashMap seachBook(String url, HashMap<String, Object> hmMas, String provider_info) {
		HashMap<String, Object> hmReturn = new HashMap<String, Object>();
		try {

			// File file = new File("C:/Program
			// Files/phantomjs-2.1.1-windows/bin/phantomjs.exe");
			File file = new File(phantomjs);

			System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
			WebDriver driver = new PhantomJSDriver();

			driver.get(url);

			if (hmMas.containsKey("validation")) {
				HashMap<String, String> hmValidation = (HashMap<String, String>) hmMas.get("validation");
				String xPath = hmValidation.get("xpath");
				String xvalue = hmValidation.get("value");
				List<WebElement> element1 = driver.findElements(By.xpath(xPath));
				if (element1.size() > 0) {
					WebElement element = element1.get(0);
					String sText = element.getText();
					if ("".equals(sText))
						sText = element.getAttribute("textContent").trim();
					if (!xvalue.equals(sText))
						return null;
				} else
					return null;

			}

			HashMap<String, String> hm = (HashMap<String, String>) hmMas.get("element");

			for (Map.Entry<String, String> entry : hm.entrySet()) {
				String key = entry.getKey();
				String xpath = entry.getValue();
				if (!"".equals(xpath)) {

					List<WebElement> element1 = driver.findElements(By.xpath(xpath));
					if (element1.size() > 0) {
						WebElement element = element1.get(0);
						String sText = element.getText();
						if ("".equals(sText))
							sText = element.getAttribute("textContent").trim();
						if ("".equals(sText))
							sText = element.getAttribute("src").trim();

						if (!"".equals(sText)) {
							hmReturn.put(key, sText);
						}

					} else
						System.out.print(key);
					// *[@id="product-attribute-specs-table"]/tbody/tr[2]/td
				}

			}
			HashMap<String, String> hmDetail = (HashMap<String, String>) hmMas.get("detail");

			String xPathLabel = (String) hmMas.get("table_xpath");
			String xPathValue = (String) hmMas.get("table_xpath_value");
			// *[@id=\"product-attribute-specs-table\"]/tbody/tr

			boolean bWhile = true;
			// *[@id="product-attribute-specs-table"]/tbody/tr[1]
			// *[@id="product-attribute-specs-table"]/tbody/tr[1]/th
			// *[@id="product-attribute-specs-table"]/tbody/tr[1]/td

			// *[@id="chi-tiet"]/tbody/tr[1]/td[1]
			int i = 1;
			while (bWhile) {
				String sXpathLbl = xPathLabel.subSequence(0, xPathLabel.length()).toString();

				sXpathLbl = sXpathLbl.replace("(i)", String.valueOf(i));
				String sXpathValue = xPathValue.subSequence(0, xPathValue.length()).toString();
				sXpathValue = sXpathValue.replace("(i)", String.valueOf(i));

				List<WebElement> elementLbl = driver.findElements(By.xpath(sXpathLbl));
				if (elementLbl.size() > 0) {

					List<WebElement> elementValue = driver.findElements(By.xpath(sXpathValue));
					if (elementValue.size() > 0) {
						WebElement elementLb = elementLbl.get(0);

						String label = elementLb.getText().trim();
						if (hmDetail.containsKey(label)) {
							WebElement elementVl = elementValue.get(0);
							String sTextVl = elementVl.getText();
							hmReturn.put(hmDetail.get(label), sTextVl);

						}

					}

				} else
					bWhile = false;

				i = i + 1;
			}

			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ((!hmReturn.containsKey("author_name")) && (!hmReturn.containsKey("publisher"))
				&& (!hmReturn.containsKey("translator")) && (!hmReturn.containsKey("page_number")))
			return null;

		hmReturn.put("source", url);
		hmReturn.put("provider_info", provider_info);

		if (!hmReturn.containsKey("cover_price")) {
			hmReturn.put("cover_price", "0");
		}

		String cover_price = (String) hmReturn.get("cover_price");
		String cover_price10 = getPrice(cover_price);
		// *[@id="product-attribute-specs-table"]/tbody/tr[1]
		// *[@id="product-attribute-specs-table"]/tbody/tr[2]
		// *[@id="product-attribute-specs-table"]/tbody/tr[1]/th
		// *[@id="product-attribute-specs-table"]/tbody/tr[1]/td
		// *[@id="product-attribute-specs-table"]/tbody/tr[1]

		hmReturn.remove("cover_price");
		long cover_price1 = Long.parseLong(cover_price10.toString());
		if (cover_price1 == 0L) {
			if (hmReturn.containsKey("cover_price_bk")) {
				String cover_price20 = (String) hmReturn.get("cover_price_bk");

				String cover_price21 = getPrice(cover_price20);
				long cover_price22 = Long.parseLong(cover_price21.toString());
				hmReturn.remove("cover_price_bk");
				cover_price1 = cover_price22;

			}
		}
		hmReturn.put("cover_price", cover_price1);

		if (hmReturn.containsKey("page_number")) {
			{
				String page_number = (String) hmReturn.get("page_number");
				String page_number10 = "";
				for (int i = 0; i < page_number.length(); i++) {
					char cha = page_number.charAt(i);
					if ((cha >= 48) && (cha <= 57)) {
						page_number10 = page_number10 + cha;
					}
				}

				hmReturn.remove("page_number");
				long page_number1 = Long.parseLong(page_number10.toString());
				hmReturn.put("page_number", page_number1);
			}

		}

		if (hmReturn.containsKey("name")) {
			{
				String name = (String) hmReturn.get("name");

				if (name.contains("(")) {
					String sNames = name.substring(0, name.indexOf("("));

					hmReturn.remove("name");
					hmReturn.put("name", sNames.trim());
					hmReturn.put("name_ext", name.trim());
				}

			}

		}
		return hmReturn;
	}

	private static String getPrice(String cover_price) {
		String cover_price10 = "";
		for (int i = 0; i < cover_price.length(); i++) {
			char cha = cover_price.charAt(i);
			if ((cha >= 48) && (cha <= 57)) {
				cover_price10 = cover_price10 + cha;
			}
		}
		return cover_price10;
	}
}