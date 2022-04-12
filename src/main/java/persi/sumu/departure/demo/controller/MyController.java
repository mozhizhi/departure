package persi.sumu.departure.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import persi.sumu.departure.core.entity.HttpResult;
import persi.sumu.departure.core.enums.ResultEnum;
import persi.sumu.departure.demo.entity.User;
import persi.sumu.departure.demo.service.UserService;

import java.util.List;

/**
 * @author mobai
 * @version 1.0
 * @Description 测试专用
 * @date 2022/4/9 15:27
 */
@RestController
public class MyController {

    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public HttpResult test() {
        logger.info("收到test请求");
        return HttpResult.success("测试成功");
    }

    @RequestMapping("/selectAllUser")
    public HttpResult selectAllUser() {
        logger.info("收到test请求");
        JSONObject result = new JSONObject();
        result.put("userList", userService.selectAll());
        return HttpResult.success(result);
    }

    @RequestMapping("/addUser")
    public HttpResult addUser(@RequestBody User user) {
        try {
            logger.info("待保存用户信息：" + JSON.toJSONString(user));
            userService.addUser(user);
            return HttpResult.success("保存成功");
        } catch (Exception e) {
            logger.error("保存用户信息异常", e);
            return HttpResult.failure(ResultEnum.SERVICE_SAVE_ERROR);
        }
    }

}
