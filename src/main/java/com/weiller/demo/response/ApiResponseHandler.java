package com.weiller.demo.response;

import com.alibaba.fastjson.JSON;
import com.weiller.demo.utils.DESUtil;
import com.weiller.demo.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 响应消息处理类
 * @author mac
 *
 */
@Slf4j
@ControllerAdvice(annotations = { RestController.class})
public class ApiResponseHandler implements ResponseBodyAdvice<Object>{
	
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		Map localMap = ThreadLocalUtil.getMap();
		String desKey = (String)localMap.get("desKey");
		long startTime = (long)localMap.get("startTime");
		String requestId = (String)localMap.get("requestId");

		final String returnTypeName = returnType.getParameterType().getName();
		if("void".equals(returnTypeName)){
			return Msg.success(null);
		}
		if(!selectedContentType.includes(MediaType.APPLICATION_JSON)){
			return body;
		}
		if("com.weiller.demo.response.ApiResponse".equals(returnTypeName)){
			try {
				String toJSONString = JSON.toJSONString(body);
				log.info("数据返回:{}",toJSONString);
				return DESUtil.encrypt(toJSONString,desKey);
			} catch (Exception e) {
				log.error("数据加密异常",e);
				throw new RuntimeException("数据加密异常");
			}
		}
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setHead(new ResponseHead(requestId,"0","ok",startTime,System.currentTimeMillis()-startTime));
		apiResponse.setBody(body);
		try {
			String toJSONString = JSON.toJSONString(apiResponse);
			log.info("数据返回:{}",toJSONString);
			String encrypt = DESUtil.encrypt(toJSONString,desKey,true);
			log.info("加密：{}", encrypt);
			return encrypt;
		} catch (Exception e) {
			log.error("数据加密异常",e);
			throw new RuntimeException("数据加密异常");
		}
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		final String  returnTypeName = returnType.getParameterType().getName();
		return !"com.weiller.demo.response.ApiResponse".equals(returnTypeName) && !"org.springframework.http.ResponseEntity".equals(returnTypeName);
	}

}
