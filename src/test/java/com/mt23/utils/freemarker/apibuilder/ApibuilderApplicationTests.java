package com.mt23.utils.freemarker.apibuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApibuilderApplicationTests {
	@Autowired
	Configuration configuration;
	public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
		InputStream is = new FileInputStream(filePath);
		String line; // 用来保存每行读取的内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
			buffer.append(line); // 将读到的内容添加到 buffer 中
			buffer.append("\n"); // 添加换行符
			line = reader.readLine(); // 读取下一行
		}
		reader.close();
		is.close();
	}
	public static String readFile(String filePath) throws IOException {
		StringBuffer sb = new StringBuffer();
		readToBuffer(sb, filePath);
		return sb.toString();
	}
	@Test
	public void contextLoads() throws IOException, TemplateException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());
		model.put("message", "这是测试的内容。。。");
		model.put("toUserName", "张三");
		model.put("fromUserName", "老许");
		String apijson = readFile("src/main/resources/templates/sourceapi.json");
		JSONObject api = JSONObject.parseObject(apijson);

		Template t = configuration.getTemplate("test.html"); // freeMarker template
//		String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
//		System.out.println(content);
		FileWriter file = new FileWriter(new File("builder/aa.html"));
		t.process(model,file);
		file.flush();
		file.close();

	}

}
