package persi.sumu.departure.core.web.service;

import org.springframework.web.multipart.MultipartFile;
import persi.sumu.departure.core.entity.HttpResult;

import javax.servlet.http.HttpServletRequest;

public interface QNiuFileUploadService {

    HttpResult<Object> uploadImageFile(HttpServletRequest request, MultipartFile file);

    HttpResult<Object> deleteFile(String url);

    HttpResult<Object> refreshFile(String url);

}
