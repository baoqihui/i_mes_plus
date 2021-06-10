package com.rh.i_mes_plus.config;

import com.rh.i_mes_plus.common.model.ErrorEnum;
import com.rh.i_mes_plus.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: hbq
 * @description: 统一异常拦截
 * @date: 2017/10/24 10:31
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	/**
	 * 未登录报错拦截
	 * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
	 */
	@ExceptionHandler(UnauthenticatedException.class)
	public Result unauthenticatedException(UnauthenticatedException e) {
		log.error("发生未登录异常！原因是：{} --- 位置为：{}",e.getMessage(),e.getStackTrace());
		return Result.failedWith(e.getMessage(),ErrorEnum.E_203.getErrorCode(), ErrorEnum.E_203.getErrorMsg());
	}

	/**
	 * 权限不足报错拦截
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public Result unauthorizedExceptionHandler(UnauthorizedException e) {
		log.error("发生无权限异常！原因是：{} --- 位置为：{}",e.getMessage(),e.getStackTrace());
		return Result.failedWith("", ErrorEnum.E_204.getErrorCode(), ErrorEnum.E_204.getErrorMsg());
	}
	/**
	 * 空指针异常
	 */
	@ExceptionHandler(NullPointerException.class)
	public Result nullPointerExceptionHandle(Exception e) {
		log.error("发生空指针异常！原因是：{} --- 位置为：{}",e.getMessage(),e.getStackTrace());
		return Result.failedWith(e.getMessage(), ErrorEnum.E_500.getErrorCode(), ErrorEnum.E_500.getErrorMsg());
	}
	/**
	 * 常规异常
	 */
/*	@ExceptionHandler(Exception.class)
	public Result exceptionHandle(Exception e) {
		log.error("发生未知异常！原因是：{} --- 位置为：{}",e.getMessage(),e.getStackTrace());
		return Result.failedWith(e.getMessage(), ErrorEnum.E_500.getErrorCode(), ErrorEnum.E_500.getErrorMsg());
	}*/

}
