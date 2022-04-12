package persi.sumu.departure.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import persi.sumu.departure.core.entity.HttpResult;
import persi.sumu.departure.core.enums.ResultEnum;

/**
 * @author mobai
 * @version 1.0
 * @Description 通用异常捕获（统一返回）
 * @date 2022/4/9 16:14
 */
@RestController
@ControllerAdvice
public class GlobalExceptionReturnHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionReturnHandler.class);

    /**
     * 不可预料的上层建筑异常时捕获，统一返回前端格式化JSON数据
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public HttpResult<Object> allExceptionHandler(Exception exception){
        ResultEnum resultCodeEnum = ResultEnum.SERVER_ERROR;
        logger.error("捕获到全局异常", exception);
        return HttpResult.failure(resultCodeEnum);
    }

}
