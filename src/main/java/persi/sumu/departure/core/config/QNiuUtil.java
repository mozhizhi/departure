package persi.sumu.departure.core.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import persi.sumu.departure.core.util.DateUtil;

import java.io.FileInputStream;
import java.util.Base64;

/**
 * @author mobai
 * @version 1.0
 * @Description 七牛云工具类
 * @date 2022/4/16 13:03
 */
@Component
public class QNiuUtil implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(QNiuUtil.class);

    private static QNiuUtil qNiuUtil;

    private static UploadManager uploadManager;
    private static BucketManager bucketManager;
    private static CdnManager cdnManager;
    private static Auth auth;
    private static String token; // 使用默认有效期1小时

    /** 域名*/
    @Value("${seven.cattle.domain}")
    private String domain;

    /** 公钥*/
    @Value("${seven.cattle.accessKey}")
    private String accessKey;

    /** 私钥*/
    @Value("${seven.cattle.secretKey}")
    private String secretKey;

    /** 包名*/
    @Value("${seven.cattle.bucketName}")
    private String bucketName;

    /**
     * 核心方法，beanPostProcessor前置后置方法之间执行
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 为当前静态对象赋值
        qNiuUtil = this;

        // 构造一个带指定Zone对象的配置类
        Configuration config = new Configuration(Zone.zone2());

        // 七牛文件上传管理器
        uploadManager = new UploadManager(config);

        // 七牛认证管理
        auth = Auth.create(accessKey, secretKey);

        // 七牛文件删除管理器
        bucketManager = new BucketManager(auth, config);

        // 刷新缓存
        cdnManager = new CdnManager(auth);

        // 初始化token
        token = auth.uploadToken(bucketName);
    }

    /**
     * 获取七牛云工具类对象
     * @return
     */
    public static QNiuUtil getQNiuUtil() {
        return qNiuUtil;
    }

    /**
     * 获取token
     * @return
     */
    public static String getToken() {
        try {
            // 第一次取token
            if(StringUtils.isEmpty(token)) {
                token = auth.uploadToken(getQNiuUtil().getBucketName());
                return token;
            }

            // token格式校验（增加健壮性）
            String[] splitArr = token.split(":");
            if(splitArr.length < 3) {
                logger.error("当前七牛云上传token【" + token + "】不符合token规则，即将重新生成token");
                token = auth.uploadToken(getQNiuUtil().getBucketName());
                return token;
            }

            // 获取超时时间JSON对象
            JSONObject jsonObj = JSON.parseObject(new String(Base64.getDecoder().decode(splitArr[2].getBytes())));
            Integer deadLineTime = (Integer) jsonObj.get("deadline"); // 过期时间对应秒数
            long curTime = DateUtil.getNowTime().getTime();	// 当前时间毫秒数
            if(curTime >= deadLineTime.longValue() * 1000 - 600000) { // token已过期（提前10分钟生成）
                logger.info("当前七牛云上传token已过期，即将生成新的token");
                token = auth.uploadToken(getQNiuUtil().getBucketName());
            }
        } catch (Exception e) {
            logger.error("七牛云生成token过程异常，即将生成新的token");
            token = auth.uploadToken(getQNiuUtil().getBucketName()); // 重新获取token
        }
        return token;
    }

    /**
     * 上传图片到七牛云
     * @param inputStream 文件输入流
     * @param key 图片唯一标识
     * @return 图片访问路径（返回null代表上传图片失败）
     */
    public static String uploadImage(FileInputStream inputStream, String key) {
        // 待返回结果：上传后七牛云生成的url地址
        String retUrl = null;

        // 文件输入流非空校验
        if(inputStream == null) {
            logger.error("文件上传到七牛云失败，文件输入流不能为空！");
            return null;
        }

        // 图片唯一标识非空校验
        if(StringUtils.isEmpty(key)) {
            logger.error("文件上传到七牛云失败，文件唯一标识key不能为空！");
            return null;
        }

        // 上传图片
        try {
            Response response = uploadManager.put(inputStream, key, getToken(), null, null);
            if(!response.isOK()) {
                logger.error("文件上传到七牛云失败，响应内容：【" + response.toString() + "】");
                return null;
            }
            // 上传成功
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            retUrl = getQNiuUtil().getDomain() + "/" + putRet.key;
        } catch (Exception e) {
            logger.error("文件上传到七牛云异常", e);
        }

        return retUrl;

    }

    /**
     * 删除七牛云文件
     * @param key 文件唯一标识（含后缀名）
     * @return true：删除文件成功 false：删除文件失败
     */
    public static boolean deleteQNFile(String key) {
        try {
            bucketManager.delete(getQNiuUtil().getBucketName(), key);
            return true;
        } catch (QiniuException e) {
            logger.error("七牛云删除文件失败，存储空间名【" + getQNiuUtil().getBucketName() + "】，文件名称【" + key + "】", e);
            return false;
        }
    }

    /**
     * 刷新单个文件缓存
     * @param url 待刷新单个文件url
     * @return true：成功 false：失败
     */
    public static boolean refresh(String url) {
        return refreshAll(new String[]{url});
    }

    /**
     * 刷新多个文件缓存
     * @param urls 待刷新多个文件url集合
     * @return true：成功 false：失败
     */
    public static boolean refreshAll(String[] urls) {
        try {
            CdnResult.RefreshResult refreshResult = cdnManager.refreshUrls(urls);
            if(refreshResult.code == 200) {
                return true;
            } else {
                logger.error("刷新缓存文件列表失败，返回code：{}，错误信息：{}", refreshResult.code, refreshResult.error);
                return false;
            }
        } catch (QiniuException e) {
            logger.error("七牛云刷新缓存失败，存储空间名【" + getQNiuUtil().getBucketName() + "】，文件七牛云路径【" + JSON.toJSONString(urls) + "】", e);
            return false;
        }
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
