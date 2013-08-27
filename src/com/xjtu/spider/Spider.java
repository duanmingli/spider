package com.xjtu.spider;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider {

	private static Set<String> urlSet = new HashSet<String>();

	private static Pattern p = Pattern.compile("^(((http|https)://"
			+ "(www.|([1-9]|[1-9]\\d|1\\d{2}|2[0-1]\\d|25[0-5])"
			+ "(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}:[0-9]+/)?)"
			+ "{1}.+){1};", Pattern.CASE_INSENSITIVE);

	public static void main(String[] args) {
		String baseUrl = "http://www.baidu.com";
		spiderInternet(baseUrl, "");
	}
	

	private static void spiderInternet(String baseUrl, String exUrl) {
		if (baseUrl.endsWith("/") && exUrl.startsWith("/")) {
			baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
		}
		String new_url = baseUrl + exUrl;
		if (urlSet.contains(new_url)) {
			return;
		}
		System.out.println(new_url);
		try {
			Document doc = Jsoup.connect(new_url).get();
			urlSet.add(new_url);
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String linkHref = link.attr("href");
				if (linkHref.equals("#")) {
					return;
				}
				Matcher matcher = p.matcher(linkHref);
				if (matcher.matches()) {
					spiderInternet(linkHref, "");
				} else {
					spiderInternet(baseUrl, linkHref);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
