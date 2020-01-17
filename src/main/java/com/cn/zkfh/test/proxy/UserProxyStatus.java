package com.cn.zkfh.test.proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.InvocationHandler;

public class UserProxyStatus implements InvocationHandler  {

	
	private Object target;
	
	public UserProxyStatus(Object target){
		this.target = target;
	}
	
	/**
     * proxy    被代理的对象
     * method   被代理对象的方法
     * args     方法的参数
     * return   Object方法的返回值
     */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("登录前");
		//执行目标类方法
        Object result = method.invoke(target, args);
		System.out.println("登录后");
		return result;
	}


	
}
