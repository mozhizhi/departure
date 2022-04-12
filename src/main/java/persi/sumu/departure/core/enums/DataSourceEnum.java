package persi.sumu.departure.core.enums;

/**
 * @author mobai
 * @version 1.0
 * @Description 数据源管理配置类
 * @date 2022/4/9 15:39
 */
public class DataSourceEnum {

    private DataSourceEnum() {}

    /**
     * 数据源beanName-mysql
     */
    public final static String MYSQL_DATASOURCE = "mysqlDataSource";

    /**
     * spring事务管理器beanName-mysql
     */
    public final static String MYSQL_DB = "mysqlTransactionManager";

}
