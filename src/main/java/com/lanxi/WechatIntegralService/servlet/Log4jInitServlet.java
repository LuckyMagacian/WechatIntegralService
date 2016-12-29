package com.lanxi.WechatIntegralService.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.FileUtil;


@SuppressWarnings("serial")
public class Log4jInitServlet extends HttpServlet{
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.err.println("log4j init----");
		//从ServlcetConfig中获取 配置文件相对路径 参数
		String location=config.getInitParameter("location");
		//判断参数是否存在
		if(location==null||location.trim().equals("")){
			System.err.println("no init file----");
			//调用默认配置
			BasicConfigurator.configure(); 
			return;
		}
		ServletContext context=config.getServletContext();
		//获取项目路径
		String webappPath=context.getRealPath("/");
		//获取配置文件绝对路径
		String logConf=webappPath+location;
		//读取文件
		File   confFile=new File(logConf);
		//判断文件是否存在
		if(confFile.exists()||confFile.isDirectory()){
			//获取初始化参数 中webapproot
//			String webroot=context.getInitParameter("webAppRoot");
			//替换webapproot为项目路径
			System.setProperty("webAppRoot",webappPath);
			System.out.println("init log4j by file "+logConf);
			//使用配置文件初始化log4j
			PropertyConfigurator.configure(logConf);
		}else{
			//使用默认配置
			System.err.println("config file not exists");
			BasicConfigurator.configure(); 
		}
		//记录日志
		Logger logger=Logger.getLogger(this.getClass());
		logger.info("log4j init finished !");
		//继续servlet初始化
		super.init(config);
	}	
	
	@SuppressWarnings("unused")
	public static void Log4jInit(){
		try{
		// TODO 异常类开启测试模式
		AppException.enTest();
		System.out.println("Log4j init start ----------------------------------------");
		//从ServlcetConfig中获取 配置文件相对路径 参数
		Properties logProperties=new Properties();
		FileInputStream inStream=new FileInputStream(FileUtil.getFileOppositeClassPath("properties/log4j.properties"));
		InputStreamReader isReader= inStream==null?null:new InputStreamReader(inStream,"UTF-8");
		//判断配置文件是否存在
		if(null==isReader){
			System.out.println("no init file---------------------------------");
			//调用默认配置
			BasicConfigurator.configure(); 
			System.out.println("Log4j init by default ------------------------------");
			return;
		}
			logProperties.load(isReader);
			logProperties.setProperty("log4j.appender.logfile.File",Log4jInitServlet.class.getClassLoader().getResource("\\").toURI().getPath()+"log/easyintegral.log");
		System.out.println("init log4j by properties/log4j.properties------------------------------------------- ");
			//使用配置文件初始化log4j
		PropertyConfigurator.configure(logProperties);
		Logger logger=Logger.getLogger(Log4jInitServlet.class);
		logger.info("log4j init finished !------------------------------");
		}catch (Exception e) {
			throw new AppException("Log4j 初始化异常",e);
		}
	}
}
