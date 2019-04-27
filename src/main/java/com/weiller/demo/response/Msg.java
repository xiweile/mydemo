package com.weiller.demo.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Msg<T> {
	
	protected boolean success = false;
	private T data;
	private String errorCode;
	private String errorMsg;

	public Msg(T data,boolean success){
		this.data = data;
		this.success = success;
	}

	public Msg() {

	}

	/**
	 * 正常数据返回
	 * @param data
	 * @return
	 */
	public static <T>Msg<T> success(T data){
		return new Msg<>(data,true);
	}
	
	/**
	 * 异常数据返回
	 * @param errorMsg
	 * @param errorCode
	 * @return
	 */
	public static <T>Msg<T> fail(String errorMsg,String errorCode){
		Msg<T> resp = new Msg<T>();
		resp.setErrorMsg(errorMsg);
		resp.setErrorCode(errorCode);
		resp.setSuccess(false);
		return resp;
	}



	public Msg(boolean success, T data, String errorCode, String errorMsg) {
		super();
		this.success = success;
		this.data = data;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
}
