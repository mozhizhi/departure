package persi.sumu.departure.core.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import persi.sumu.departure.core.config.QNiuUtil;
import persi.sumu.departure.core.entity.HttpResult;
import persi.sumu.departure.core.enums.ResultEnum;
import persi.sumu.departure.core.util.ConstantUtil;
import persi.sumu.departure.core.util.DepartureUtil;
import persi.sumu.departure.core.web.service.QNiuFileUploadService;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author mobai
 * @version 1.0
 * @Description 七牛云文件上传业务逻辑层
 * @date 2022/4/16 10:15
 */
@Component("qNiuFileUploadService")
public class QNiuFileUploadServiceImpl implements QNiuFileUploadService {

    private static final Logger logger = LoggerFactory.getLogger(QNiuFileUploadServiceImpl.class);

    /**
     * 上传图片到七牛云
     * @param request
     * @param file
     * @return
     */
    @Override
    public HttpResult<Object> uploadImageFile(HttpServletRequest request, MultipartFile file) {
        // 待返回结果
        HttpResult httpResult;

        // 上送文件必传校验
        if(file == null) {
            logger.error("待上传文件不能为空");
            return HttpResult.failure(ResultEnum.QN_EMPTY_UPLOAD_FILE_ERROR);
        }

        // 原始文件名
        String originalFileName = file.getOriginalFilename();

        logger.info("文件上传开始，原始文件名：{}", originalFileName);

        // 文件基础校验
        if(StringUtils.isEmpty(originalFileName) || originalFileName.lastIndexOf(".") == -1) {
            logger.error("待上传原始文件，文件名称为空或不合法，原始文件名：【" + originalFileName + "】");
            return HttpResult.failure(ResultEnum.QN_UN_LEGAL_FILE_NAME);
        }

        // 获取文件后缀
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 校验文件类型（文件扩展名为空或不支持）
        if(!DepartureUtil.isFileTypeSupport(suffix, ConstantUtil.QN_FILE_TYPE_IMAGE)) {
            return HttpResult.failure(ResultEnum.QN_UN_LEGAL_FILE_NAME);
        }

        // 校验单个文件大小
        if(!DepartureUtil.checkFileSize(file.getSize(), ConstantUtil.MAX_UPLOAD_IMAGE_SIZE)) {
            logger.error("单个图片上传超过单文件上传最大限制【" + ConstantUtil.MAX_UPLOAD_IMAGE_SIZE/1024/1024 + "】MB，" +
                            "完整文件名[" + originalFileName + "]，当前文件大小[" + file.getSize()/1024 + "KB]");
            return HttpResult.failure(ResultEnum.QN_FILE_IMAGE_SINGLE_UPLOAD_MORE_THAN_MAX);
        }

        // 文件输入流
        InputStream inputStream = null;

        // 上传文件
        try {
            inputStream = file.getInputStream();
            String fileNameKey = DepartureUtil.getUUIDKeyAsFileName(suffix);
            String retUrl = QNiuUtil.uploadImage((FileInputStream) inputStream, fileNameKey);

            // 上传失败
            if(StringUtils.isEmpty(retUrl)) {
                httpResult = HttpResult.failure(ResultEnum.QN_FILE_UPLOAD_ERROR);
                return httpResult;
            }

            // 上传成功
            JSONObject retJson = new JSONObject();
            retJson.put("retUrl", retUrl);
            httpResult = HttpResult.success(retJson);
            logger.info("文件上传成功，原始文件名：{}，上送后七牛云返回地址：{}", originalFileName, retUrl);
        } catch (Exception e) {
            logger.error("图片上传至七牛云服务器异常，完整文件名[" + originalFileName + "]", e);
            httpResult = HttpResult.failure(ResultEnum.QN_FILE_UPLOAD_ERROR);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("图片上传关闭输入流异常，完整文件名：【" + originalFileName + "】", e);
                }
            }
        }
        return httpResult;
    }

    /**
     * 删除单个文件
     * @param key 文件唯一标识（含后缀名）
     * @return true：成功 false：失败
     */
    @Override
    public HttpResult<Object> deleteFile(String key) {
        if(QNiuUtil.deleteQNFile(key)) {
            return HttpResult.success("删除成功");
        }
        return HttpResult.failure(ResultEnum.QN_DELETE_FILE_FAIL);
    }

    /**
     * 刷新文件（文件删除后，不刷新，还是可以通过url访问）
     * @param url
     * @return
     */
    @Override
    public HttpResult<Object> refreshFile(String url) {
        if(QNiuUtil.refresh(url)) {
            return HttpResult.success("刷新成功");
        }
        return HttpResult.failure(ResultEnum.QN_REFRESH_FILE_FAIL);
    }

}
