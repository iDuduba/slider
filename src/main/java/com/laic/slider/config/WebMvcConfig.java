/**
 * duduba
 * Created at 2016年4月18日 下午11:02:27
 */

package com.laic.slider.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laic.slider.api.response.HelloResponse;
import com.laic.slider.web.interceptor.ExecuteTimeInterceptor;
import com.laic.slider.web.interceptor.SecurityInterceptor;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author dly
 * @since 2015-12-19 16:16
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableCaching
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

	// 请求参数校验
	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	// 将代理服务器发来的请求包含的IP地址转换成真正的用户IP
	@Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

	@Autowired
	ObjectMapper objectMapper;

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter
				= new MeMappingJackson2HttpMessageConverter();

//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		jsonConverter.setObjectMapper(objectMapper);

		return jsonConverter;
	}

	class MeMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
		//重写writeInternal方法，在返回内容前进行国际化
		@Override
		protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
			// Todo : add else
			Class<?> clazz = object.getClass();
			if(clazz.isAnnotationPresent(I18n.class)) {
				Field[] fields = clazz.getDeclaredFields();
				for(Field field : fields) {
					if (field.isAnnotationPresent(I18n.class)) {
						field.setAccessible(true);
						I18n i18n = field.getAnnotation(I18n.class);
						String codeType = i18n.codeType();
						String code = i18n.code();
						try {
							Field codeField = clazz.getDeclaredField(code);
							codeField.setAccessible(true);
							field.set(object, codeType + "." + codeField.get(object));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			super.writeInternal(object, type, outputMessage);
		}
	}
}
