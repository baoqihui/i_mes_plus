package com.rh.i_mes_plus.config;

import com.rh.i_mes_plus.common.model.ErrorEnum;
import com.rh.i_mes_plus.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Slf4j
@ControllerAdvice
@ResponseBody
@Order(Ordered.LOWEST_PRECEDENCE - 2)
public class SqlExceptionHandler {
    /**
     * 唯一异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result duplicateKeyExceptionHandle(DuplicateKeyException e) {
        log.error("发生数据库字段索引非唯一异常！原因是：{}",e.getMessage());
        return Result.failedWith(e, ErrorEnum.E_205.getErrorCode(), ErrorEnum.E_205.getErrorMsg());
    }

    /**
     * 普通sql异常
     */
    @ExceptionHandler(SQLException.class)
    public Result sqlExceptionHandle(SQLException e) {
        log.error("发生sql异常！原因是：{}",e.getMessage());
        log.error("sql错误码：{}",e.getErrorCode());
        return Result.failedWith(e, ErrorEnum.E_206.getErrorCode(), ErrorEnum.E_206.getErrorMsg());
    }
}
