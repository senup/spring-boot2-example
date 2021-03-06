package com.gzz;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzz.sys.user.User;
import com.gzz.sys.user.UserCond;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserTests {

	private ObjectMapper mapper = new ObjectMapper();
	private MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

	@Autowired
	private WebApplicationContext wac; // 注入WebApplicationContext

	@Before // 在测试开始前初始化工作
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void save() throws Exception {
		User user = User.builder().gender((byte) 1).name("gzz").build();
		// user.setBirthday(null).setGender(null).setId(null).setName(null);
		MvcResult result = mockMvc.perform(post("/user/save").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user))).andExpect(status().isOk())// 模拟向testRest发送get请求
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果

		log.info(result.getResponse().getContentAsString());
	}

	@Test
	public void queryList() throws Exception {
		UserCond cond = UserCond.builder().gender((byte) 1).build();

		MvcResult result = mockMvc.perform(post("/user/queryList").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(cond))).andExpect(status().isOk())// 模拟向testRest发送get请求
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果

		log.info(result.getResponse().getContentAsString());
	}

	@Test
	public void queryPage() throws Exception {
		UserCond cond = UserCond.builder().gender((byte) 1).build();
		MvcResult result = mockMvc.perform(post("/user/queryPage").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(cond))).andExpect(status().isOk())// 模拟向testRest发送get请求
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果

		log.info(result.getResponse().getContentAsString());
	}

	@Autowired
	private HikariDataSource hikariPool;

	@Test
	public void HikariDataSourceInfo() {
		/**
		 * 数据源监控
		 */
		log.info("活跃连接数:{},"
				+ "闲置连接数:{},"
				+ "总连接数:{},"
				+ "ConnectionTimeout:{},"
				+ "MaximumPoolSize:{},"
				+ "IdleTimeout:{},", 
				hikariPool.getHikariPoolMXBean().getActiveConnections(), 
				hikariPool.getHikariPoolMXBean().getIdleConnections(),
				hikariPool.getHikariPoolMXBean().getTotalConnections(),
				hikariPool.getConnectionTimeout(),
				hikariPool.getMaximumPoolSize(),
				hikariPool.getIdleTimeout()
				);

	}
}