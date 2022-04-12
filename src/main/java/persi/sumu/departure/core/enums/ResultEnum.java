package persi.sumu.departure.core.enums;

/**
 * @author mobai
 * @version 1.0
 * @Description 统一返回前端结果枚举
 * @date 2022/4/9 15:51
 */
public enum ResultEnum {

    SERVER_ERROR(500,"服务器内部错误"),

    SERVICE_SAVE_ERROR(1002, "保存失败"),;

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
