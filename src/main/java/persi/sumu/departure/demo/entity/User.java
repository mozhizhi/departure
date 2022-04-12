package persi.sumu.departure.demo.entity;

/**
 * @author mobai
 * @version 1.0
 * @Description 用户类（测试）
 * @date 2022/4/9 16:34
 */
public class User {

    private Long id;
    private String name;
    private String password;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
