/**
 * duduba
 * Created at 2016年4月18日 下午11:02:27
 */

package com.laic.slider.config;

import com.laic.slider.web.interceptor.ExecuteTimeInterceptor;
import com.laic.slider.web.interceptor.SecurityInterceptor;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author dly
 * @since 2015-12-19 16:16
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	SecurityInterceptor securityInterceptor;
	
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new ExecuteTimeInterceptor());
		registry.addInterceptor(securityInterceptor).addPathPatterns("/**");
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

	// 将代理服务器发来的请求包含的IP地址转换成真正的用户IP
	@Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }
	
}
