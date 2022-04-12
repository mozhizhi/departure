package persi.sumu.departure.core.entity;

import persi.sumu.departure.core.enums.ResultEnum;

import java.io.Serializable;

/**
 * @author mobai
 * @version 1.0
 * @Description 返回前端JSON格式数据统一模板
 * @date 2022/4/9 15:49
 */
public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean isSuccess;	// 是否响应成功
    private Integer code;		// 响应状态码
    private T data;				// 响应数据
    private String message;		// 错误信息

    /**
     * 无参构造器（成功时返回）
     */
    private HttpResult() {
        this.code = 200;
        this.isSuccess = true;
    }

    /**
     * 有参构造器（成功时返回，含返回错误信息）
     * @param message
     */
    private HttpResult(String message) {
        this.isSuccess = true;
        this.code = 200;
        this.message = message;
    }

    /**
     * 有参构造器（成功时返回，含返回数据）
     * @param data
     */
    private HttpResult(T data) {
        this.isSuccess = true;
        this.code = 200;
        this.data = data;
    }

    /**
     * 有参构造器（返回自定义枚举中包含项，用于自定义错误返回）
     * @param resultCode
     */
    private HttpResult(ResultEnum resultCode) {
        this.isSuccess = false;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 返回成功（无返回结果）
     * @param <T>
     * @return
     */
    public static<T> HttpResult<T> success() {
        return new HttpResult<T>();
    }

    /**
     * 返回成功（自定义错误信息）
     * @param <T>
     * @return
     */
    public static<T> HttpResult<T> success(String message) {
        return new HttpResult<T>(message);
    }

    /**
     * 返回成功（有返回结果）
     * @param data
     * @param <T>
     * @return
     */
    public static<T> HttpResult<T> success(T data){
        return new HttpResult<T>(data);
    }

    /**
     * 通用返回失败（返回自定义枚举中包含项，用于自定义错误返回）
     * @param resultCode
     * @param <T>
     * @return
     */
    public static<T> HttpResult<T> failure(ResultEnum resultCode){
        return new HttpResult<T>(resultCode);
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
