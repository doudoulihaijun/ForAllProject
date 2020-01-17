package com.cn.zkfh.test.proxy;

import org.springframework.cglib.proxy.Proxy;

public class test {

	public static void main(String[] args) throws Exception {

		//UserService userservice = new UserService() ;
		//UserProxy userproxy = new UserProxy(userservice) ;
		//userproxy.login();
		
		UserService userservice = new UserService() ;
		UserProxyStatus userproxy = new UserProxyStatus(userservice) ;
		User user = (User) Proxy.newProxyInstance(userservice.getClass().getClassLoader(), userservice.getClass().getInterfaces(), userproxy);
		user.login();
		
		
		
	}

}
