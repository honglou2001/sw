package com.zhuika.util;

import java.io.File;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zhuika.entity.Config;

public class XMLReader {

	// 配置文件名
	private static String filename = "conf.xml";
	private static Config config;

	/**
	 * 从配置文件中读取参数并保存到Config类中,
	 * 很多时候程序中会多次使用到配置中的参数, 
	 * 于是设置成静态方法,读取一次后就一直保存其中的参数，
	 * 不再反复读取
	 * 
	 * @return
	 */
	public static Config loadconfig() {
		//if (config == null)
			config = getconfig();
		return config;
	}

	private static Config getconfig() {
		
		String xmlPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		xmlPath = xmlPath+filename;
		Config config = new Config();
		try {
			File f = new File(xmlPath);
			if (!f.exists()) {
				System.out.println("  Error : Config file doesn't exist!");
				System.exit(1);
			}
			SAXReader reader = new SAXReader();
			Document doc;
			doc = reader.read(f);
			Element root = doc.getRootElement();
			Element data;
			Iterator<?> itr = root.elementIterator("VALUE");
			data = (Element) itr.next();

			config.socketip = data.elementText("socketip").trim();
			config.socketport = data.elementText("socketport").trim();
			
			data = (Element) itr.next();

			config.mailhost = data.elementText("mailhost").trim();
			config.mailusername = data.elementText("mailusername").trim();
			config.mailpassword = data.elementText("mailpassword").trim();
			config.mailfrom = data.elementText("mailfrom").trim();
			config.mailfromname = data.elementText("mailfromname").trim();
			
		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		}
		return config;

	}
}