package com.weiller.demo.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 响应消息处理类
 * @author mac
 *
 */
//@ControllerAdvice(annotations = { RestController.class})
public class MsgHandler implements ResponseBodyAdvice<Object>{
	
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		final String returnTypeName = returnType.getParameterType().getName();
		if("void".equals(returnTypeName)){
			return Msg.success(null);
		}
		if(!selectedContentType.includes(MediaType.APPLICATION_JSON)){
			return body;
		}
		if("com.weiller.demo.response.Msg".equals(returnTypeName)){
			return body;
		}
		return Msg.success(body);
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		final String  returnTypeName = returnType.getParameterType().getName();
		return !"com.weiller.demo.response.Msg".equals(returnTypeName) && !"org.springframework.http.ResponseEntity".equals(returnTypeName);
	}

}
