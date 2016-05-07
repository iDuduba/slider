package com.laic.slider.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.laic.slider.api.enums.CodeEnum;
import com.laic.slider.api.response.CommonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Objects;

import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;

@Slf4j
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	@Value("${api.secretKey}")
	private String secretKey;

	@Value("${api.skipSign:false}")
	private boolean skipSign;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String data = request.getParameter("data");
		if(Strings.isNullOrEmpty(data)) {
			CommonResponse comResponse = new CommonResponse();
			comResponse.setResponse(CodeEnum.FAIL);
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.getWriter().write(JSONObject.toJSONString(comResponse));
			return false;
		}
		String decodedData = URLDecoder.decode(data,"UTF-8");
		log.debug(">>> {} : {}", request.getRequestURI(), decodedData);
		request.setAttribute("data", decodedData);

		if (!skipSign) {
			String appid = request.getParameter("appid");
			String sign = request.getParameter("sign");
			String chaos = request.getParameter("chaos");

			StringBuilder md5String = new StringBuilder();
			md5String.append(appid).append(decodedData).append(chaos).append(secretKey);
			String md5 = DigestUtils.md5DigestAsHex(md5String.toString().getBytes());

			if (!Objects.equal(md5, sign)) {
				CommonResponse comResponse = new CommonResponse();
				comResponse.setRespCode(CodeEnum.MD5_ERROR.getCode());
				comResponse.setRespMsg(CodeEnum.MD5_ERROR.getMsg());

				log.error("签名错误->Client:{},Server:{}", sign, md5);

				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.getWriter().write(JSONObject.toJSONString(comResponse));
				return false;
			}
		}
		
		return super.preHandle(request, response, handler);
	}
}
