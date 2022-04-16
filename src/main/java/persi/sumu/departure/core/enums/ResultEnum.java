package persi.sumu.departure.core.enums;

/**
 * @author mobai
 * @version 1.0
 * @Description 统一返回前端结果枚举
 * @date 2022/4/9 15:51
 */
public enum ResultEnum {

    SERVER_ERROR(500,"服务器内部错误"),

    SERVICE_SAVE_ERROR(1001, "保存失败"),

    QN_UN_LEGAL_FILE_NAME(1101, "不支持的文件类型"),

    QN_EMPTY_UPLOAD_FILE_ERROR(1102, "待上传文件不能为空"),

    QN_FILE_IMAGE_SINGLE_UPLOAD_MORE_THAN_MAX(1103, "单个图片上传超过最大文件大小限制"),

    QN_FILE_UPLOAD_ERROR(1104, "文件上传失败"),

    QN_DELETE_FILE_FAIL(1105, "删除文件失败"),

    QN_REFRESH_FILE_FAIL(1106, "刷新文件失败");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
