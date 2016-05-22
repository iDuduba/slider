/**
 * duduba
 * Created at 2016年4月18日 下午11:02:27
 */

package com.laic.slider.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laic.slider.api.enums.LangEnum;
import com.laic.slider.web.interceptor.ExecuteTimeInterceptor;
import com.laic.slider.web.interceptor.SecurityInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

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
//	@Bean
//    public RemoteIpFilter remoteIpFilter() {
//        return new RemoteIpFilter();
//    }

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
			Field langField = ReflectionUtils.findField(object.getClass(), "language", LangEnum.class);
			if(langField != null) {
				ReflectionUtils.makeAccessible(langField);
				LangEnum language = (LangEnum) ReflectionUtils.getField(langField, object);
				if (language != null) {
					translate(object, language);
				}
			}
			super.writeInternal(object, type, outputMessage);
		}

		private void translate(final Object object, final LangEnum language) {
			final Class<?> clazz = object.getClass();
			ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {
				@Override
				public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
					if (field.isAnnotationPresent(I18n.class)) {
						Class<?> fClazz = field.getType();
						ReflectionUtils.makeAccessible(field);
						if(fClazz == String.class) {
							String refFieldName = language.getCode() + StringUtils.capitalize(field.getName());
							Field refField = ReflectionUtils.findField(clazz, refFieldName, String.class);
							if(refField != null) {
								ReflectionUtils.makeAccessible(refField);
								String v = (String) ReflectionUtils.getField(refField, object);
								ReflectionUtils.setField(field, object, v);
							}
						} else {
							if(fClazz.isAnnotationPresent(I18n.class)) {
								Object o = ReflectionUtils.getField(field, object);
								translate(o, language);
							}
						}
					}
				}
			});
		}
	}
}
