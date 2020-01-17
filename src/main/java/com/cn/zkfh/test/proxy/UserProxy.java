package com.cn.zkfh.test.proxy;

public class UserProxy implements User {

	public UserService userservice;
	
	public UserProxy(UserService userservice){
		this.userservice = userservice;
	}
	
	@Override
	public void login() {
		
		System.out.println("登录前");
		userservice.login();
		System.out.println("登录后");
		
		
	}

	
}
