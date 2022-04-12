package persi.sumu.departure.core.exception;

/**
 * @author mobai
 * @version 1.0
 * @Description 自定义异常（服务层）
 * @date 2022/4/12 8:54
 */
public class CustomServiceException extends CustomBaseException {

    private static final long serialVersionUID = 1L;

    public CustomServiceException(String message) {
        super(message);
    }
}
