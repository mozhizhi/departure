package persi.sumu.departure.core.exception;

/**
 * @author mobai
 * @version 1.0
 * @Description 自定义异常父类（由子类扩展定义不同的异常类型）
 * @date 2022/4/12 8:48
 */
public class CustomBaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public CustomBaseException() {
    }

    public CustomBaseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
