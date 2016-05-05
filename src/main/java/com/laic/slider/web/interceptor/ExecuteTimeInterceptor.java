package com.laic.slider.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(log.isDebugEnabled()) {
			long startTime = System.currentTimeMillis();
			request.setAttribute("_startTime", startTime);
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(log.isDebugEnabled()) {
			long startTime = (Long)request.getAttribute("_startTime");
			long endTime = System.currentTimeMillis();
			long executeTime = endTime - startTime;
	
			log.debug("[{}] elapsed : {}ms", request.getRequestURL(), executeTime);
		}
	}
}
