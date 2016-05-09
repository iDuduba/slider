package com.laic.slider.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter {

	// 拦截器是单例，因此不管用户请求多少次都只有一个拦截器实现，即线程不安全
	// private NamedThreadLocal<Long>  startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(log.isDebugEnabled()) {
			long startTime = System.currentTimeMillis();
			// startTimeThreadLocal.set(beginTime);//线程绑定变量（该数据只有当前请求的线程可见）
			request.setAttribute("_startTime", startTime);
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(log.isDebugEnabled()) {
			long startTime = (Long)request.getAttribute("_startTime");
			// long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
			long endTime = System.currentTimeMillis();
			long executeTime = endTime - startTime;
	
			log.debug("[{}] elapsed : {}ms", request.getRequestURI(), executeTime);
		}
	}
}
