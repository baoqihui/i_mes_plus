package com.rh.i_mes_plus.common.model;

/**
 * @Author: hbq
 * @Description:
 * @Date: 2021/3/26 11:29
 */
public enum ErrorEnum {
	/*
	 * 错误信息
	 * */
	E_202(202, "与服务器断开连接，请重新登录"),
	E_203(203, "登陆已过期,请重新登陆"),
	E_204(204, "权限不足"),
	E_205(205, "保存失败,关键字段重复"),
	E_206(206, "保存失败,请检查保存数据格式"),
	E_500(500,"请求失败，请联系管理员");

	private Integer errorCode;

	private String errorMsg;

	ErrorEnum(Integer errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}