package persi.sumu.departure.core.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import persi.sumu.departure.core.enums.DataSourceEnum;

import javax.sql.DataSource;

/**
 * @author mobai
 * @version 1.0
 * @Description 数据源配置类
 * @date 2022/4/9 15:30
 */
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name= DataSourceEnum.MYSQL_DATASOURCE)
    @ConfigurationProperties(prefix="spring.datasource.mysqldb")
    public DataSource mysqlDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name=DataSourceEnum.MYSQL_DB)
    public DataSourceTransactionManager mysqlTransaction(@Qualifier(DataSourceEnum.MYSQL_DATASOURCE) DataSource mysqlDataSource){
        return new DataSourceTransactionManager(mysqlDataSource);
    }

}
