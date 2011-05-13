/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.test.utils;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * Struts2 测试的工具类, 将MockRequest/MockResponse放入Struts2的ServletActionContext.
 * 
 * @author calvin
 */
public abstract class Struts2TestUtils {

	/**
	 * 将request放入Struts2的ServletActionContext,支持Struts2待测代码用ServletActionContext.getRequest()取出MockRequest.
	 */
	public static void setRequestToStruts2(HttpServletRequest request) {
		initStruts2ActionContext();
		ServletActionContext.setRequest(request);
	}

	/**
	 * 将response放入Struts2的ServletActionContext,支持Struts2待测代码用ServletActionContext.getResponse()取出MockResponse.
	 */
	public static void setResponseToStruts2(HttpServletResponse response) {
		initStruts2ActionContext();
		ServletActionContext.setResponse(response);
	}

	/**
	 * 如果Struts2 ActionContext未初始化则进行初始化.
	 */
	private static void initStruts2ActionContext() {
		if (ActionContext.getContext() == null) {
			ActionContext.setContext(new ActionContext(new HashMap()));
		}
	}

}
