package persi.sumu.departure.core.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mobai
 * @version 1.0
 * @Description
 * @date 2022/4/11 19:49
 */
@RestController
public class HealthCheck {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheck.class);

    @RequestMapping("/healthCheck")
    public String healthCheck() {
        logger.info("============触发健康检查============");
        return "ok";
    }

}
