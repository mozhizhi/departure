package persi.sumu.departure.core.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import persi.sumu.departure.core.entity.HttpResult;
import persi.sumu.departure.core.web.service.QNiuFileUploadService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author mobai
 * @version 1.0
 * @Description 七牛云文件上传控制层
 * @date 2022/4/16 10:13
 */
@RestController
@RequestMapping("/qNiuFile")
public class QNiuFileUploadController {

    @Autowired
    private QNiuFileUploadService qNiuFileUploadService;

    @RequestMapping("/uploadImage")
    public HttpResult uploadImageFile(HttpServletRequest request, @RequestBody MultipartFile file) {
        return qNiuFileUploadService.uploadImageFile(request, file);
    }

    @PostMapping("/delete")
    public HttpResult deleteFile(@RequestBody Map<String, Object> params) {
        return qNiuFileUploadService.deleteFile((String) params.get("url"));
    }

    @PostMapping("/refresh")
    public HttpResult refreshFile(@RequestBody Map<String, Object> params) {
        return qNiuFileUploadService.refreshFile((String) params.get("url"));
    }

}
