package com.firstwicket;

import com.firstwicket.excel.*;
import org.junit.*;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.*;
import org.springframework.core.io.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.*;
import org.springframework.util.*;
import org.springframework.web.client.*;

import java.io.*;
import java.net.*;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=9000")
public class ApplicationTests {

	private RestTemplate restTemplate =  new RestTemplate();
	private String url = "http://localhost:9000/convertJson";

	@Test
	public void contextLoads() {

		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(new File("Movie.xlsx"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XcelConverter xcelConverter = XcelConverter.newInstance();
		List list = xcelConverter.excelParser(inputStream);

		Assert.assertEquals(xcelConverter.toJson(list),"[[{\"Movie\":\"Money Monster\",\"Release_date\":\"Fri May 13 00:00:00 PDT 2016\"},{\"Movie\":\"Mother's Day\",\"Release_date\":\"Fri Apr 29 00:00:00 PDT 2016\"}]]");

	}

	@Test
	public void upload(){

		MultiValueMap<String,Object> file = new LinkedMultiValueMap<String,Object>();
		file.add("file",new FileSystemResource("Movie.xlsx"));
		String response = restTemplate.postForObject(url,file,String.class);
		Assert.assertEquals(response,"[[{\"movie\":\"Money Monster\",\"release_date\":\"Fri May 13 00:00:00 PDT 2016\"},{\"movie\":\"Mother's Day\",\"release_date\":\"Fri Apr 29 00:00:00 PDT 2016\"}]]");

	}

}
