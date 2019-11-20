package com.tioserver.controller;

import com.tioserver.HelloPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tio.core.Tio;
import org.tio.core.starter.TioServerBootstrap;

/**
 * @author yangjian
 */
@RestController
public class HelloController {

	static Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	private TioServerBootstrap bootstrap;

	@GetMapping("/")
	public String index()
	{
		return "哈罗, tio-spring-boot-starter !!!";
	}

	/**
	 * 推送消息到客户端
	 * @throws Exception
	 */
	@GetMapping("/push")
	public String pushMessage() throws Exception {
		HelloPacket packet = new HelloPacket();
		packet.setBody("这是一条通过服务器推送的消息".getBytes(HelloPacket.CHARSET));
//		Tio.sendToAll(bootstrap.getServerGroupContext(), packet);
		Tio.sendToGroup(bootstrap.getServerGroupContext(),"Air",packet);
		logger.info("成功推送一条信息到客户端");
		return "成功推送一条信息到客户端";
	}


}
