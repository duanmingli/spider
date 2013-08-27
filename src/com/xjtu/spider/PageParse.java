package com.xjtu.spider;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 
 * <p>
 * Title: 爬虫测试
 * </p>
 * 
 * @author dml@2013-2-17
 * @version 1.0
 */
public class PageParse {
	public static void main(String[] args) {
		PageParse t = new PageParse();
		System.out.println("===========t.parseString()==============");
		t.parseString();
		System.out.println("===========t.parseUrl()==============");
		t.parseUrl();
	}

	/**
	 * 解析字符串
	 * 
	 */
	public void parseString() {
		String html = "<html><head><title>blog</title></head><body onload='test()'><p>Parsed HTML into a doc.</p></body></html>";
		Document doc = Jsoup.parse(html);
		Elements es = doc.body().getAllElements();
		System.out.println("获取onload方法名：" + es.attr("onload"));
		System.out.println("获取指定标签内容：" + es.select("p"));
	}
	/**
	 * 解析URL
	 */
	public void parseUrl() {
		try {
			Document doc = Jsoup.connect("http://www.baidu.com/").get();
			Elements hrefs = doc.select("a[href]");
			System.out.println("获取链接地址方式一：" + hrefs);
			System.out.println("------------------");
			System.out.println("获取链接地址方式二:" + hrefs.select("[href^=http]"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析文件
	 */
	 public void parseFile() {
		 try {
			 File input = new File("input.html");
			 Document doc = Jsoup.parse(input, "UTF-8");
			 // 提取出所有的编号
			 Elements codes = doc.body().select(
			 "td[title^=IA] > a[href^=javascript:view]");
			 System.out.println(codes);
			 System.out.println("------------------");
			 System.out.println(codes.html());
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
}