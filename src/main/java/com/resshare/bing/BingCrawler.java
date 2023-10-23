package com.resshare.bing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BingCrawler {

	public static final String baseUrl = "http://www.bing.com/search?q=";

	public static void main(String[] args) throws Exception {
		String stringTextSeah = "vo nhat";
		seachBing(stringTextSeah, "");
	}

	public static List<String> seachBing(String stringTextSeah, String sLinkSite) throws IOException {
		List<String> searchTerms = new ArrayList<String>();
		List<BingResult> searchResults = new ArrayList<BingResult>();
		List<String> sLink = new ArrayList<String>();

		searchTerms.add(stringTextSeah + " " + sLinkSite);

		for (String searchTerm : searchTerms) {
			StringBuilder queryUrl = new StringBuilder(baseUrl);
			queryUrl.append(searchTerm);

			String urlSeach = queryUrl.toString();
			System.out.println(urlSeach);
			Connection.Response res = Jsoup.connect(urlSeach).userAgent(Constants.CRAWLER_USER_AGENT).execute();
			Document result = res.parse();

			Element nextPage;
			int page = 0;
			int iCount = 0;
			do {
				System.out.println("Page: " + ++page);
				Elements links = result.select("h2>a");

				for (Element link : links) {
					String title = link.text();
					String url_string = link.attr("abs:href");

					if (url_string.startsWith("http://www.bing.com/"))
						// Some Bing internal link, not important
						continue;

					System.out.println("title = " + title);
					System.out.println("url_string = " + url_string);

					iCount = iCount + 1;
					sLink.add(url_string);

				}

				nextPage = result.select(".sb_pagN").isEmpty() ? null : result.select(".sb_pagN").first();
				if (nextPage != null) {
					String attr = "";
					try {
						attr = nextPage.attr("abs:href");
						if ((attr != null) && (!"".equals(attr))) {

							res = Jsoup.connect(attr).cookies(res.cookies()).userAgent(Constants.CRAWLER_USER_AGENT)
									.execute();
							result = res.parse();

						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				if (iCount > 100)
					break;
			} while (nextPage != null);
		}
		return sLink;
	}
}
