package org.ibase4j.core.base;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.Constants;
import org.ibase4j.core.exception.BaseException;
import org.ibase4j.core.exception.IllegalParameterException;
import org.ibase4j.core.support.HttpCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class AdviceController {
    private Logger logger = LogManager.getLogger();

    /**
     * 异常处理
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ModelMap> exceptionHandler(HttpServletRequest request, HttpServletResponse response,
                                                     Throwable ex) {
        logger.error(Constants.Exception_Head, ex);
        ModelMap modelMap = new ModelMap();
        if (ex instanceof BaseException) {
            ((BaseException) ex).handler(modelMap);
        } else if (ex instanceof IllegalArgumentException) {
            new IllegalParameterException(ex.getMessage()).handler(modelMap);
        } else if ("org.apache.shiro.authz.UnauthorizedException".equals(ex.getClass().getName())) {
            modelMap.put("code", HttpCode.FORBIDDEN.value().toString());
            modelMap.put("msg", HttpCode.FORBIDDEN.msg());
        } else {
            modelMap.put("code", HttpCode.INTERNAL_SERVER_ERROR.value().toString());
            String msg = StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.msg());
            logger.debug(msg);
            modelMap.put("msg", msg.length() > 100 ? "业务信息提交失败，请稍后再试！" : msg);
        }
        modelMap.put("timestamp", System.currentTimeMillis());
        logger.info("response===>" + JSON.toJSON(modelMap));
        return ResponseEntity.ok(modelMap);
    }
}
