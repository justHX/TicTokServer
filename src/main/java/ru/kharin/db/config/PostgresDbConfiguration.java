package ru.kharin.db.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


/**
 * Configuration for connecting to postgres database
 *
 * @author George Beliy on 10-01-2020
 */
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:postgres.jdbc.properties", "classpath:postgres.hibernate.properties"})
@EnableJpaAuditing(auditorAwareRef = "springSecurityAudit")
@EnableJpaRepositories(basePackages = {"ru.kharin.db.repository"})
@EntityScan(basePackages = {"ru.kharin.db.entity"})
public class PostgresDbConfiguration {

    private final Environment jdbcEnv;
    private final Environment hibernateEnv;


    @Autowired
    public PostgresDbConfiguration(Environment jdbcEnv, Environment hibernateEnv) {
        this.jdbcEnv = jdbcEnv;
        this.hibernateEnv = hibernateEnv;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource")DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("ru.kharin.db.repository", "ru.kharin.db.entity");

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", hibernateEnv.getRequiredProperty("hibernate.dialect"));
        hibernateProperties.put("hibernate.ejb.naming_strategy", hibernateEnv.getRequiredProperty("hibernate.ejb.naming_strategy"));
        hibernateProperties.put("hibernate.show_sql", hibernateEnv.getRequiredProperty("hibernate.show_sql"));
        hibernateProperties.put("hibernate.format_sql", hibernateEnv.getRequiredProperty("hibernate.format_sql"));
        hibernateProperties.put("hibernate.enable_lazy_load_no_trans", hibernateEnv.getRequiredProperty("hibernate.enable_lazy_load_no_trans"));
        hibernateProperties.put("hibernate.generate_statistics", hibernateEnv.getRequiredProperty("hibernate.generate_statistics"));
        hibernateProperties.put("hibernate.ejb.use_class_enhancer", hibernateEnv.getRequiredProperty("hibernate.ejb.use_class_enhancer"));
        hibernateProperties.put("hibernate.jdbc.lob.non_contextual_creation", hibernateEnv.getRequiredProperty("hibernate.jdbc.lob.non_contextual_creation"));



        hibernateProperties.put("hibernate.cache.region.factory_class", hibernateEnv.getRequiredProperty("hibernate.cache.region.factory_class"));
        hibernateProperties.put("hibernate.cache.provider_class", hibernateEnv.getRequiredProperty("hibernate.cache.provider_class"));
        hibernateProperties.put("net.sf.ehcache.configurationResourceName", hibernateEnv.getRequiredProperty("net.sf.ehcache.configurationResourceName"));
        hibernateProperties.put("hibernate.cache.use_second_level_cache", hibernateEnv.getRequiredProperty("hibernate.cache.use_second_level_cache"));
        hibernateProperties.put("hibernate.cache.use_query_cache", hibernateEnv.getRequiredProperty("hibernate.cache.use_query_cache"));

        entityManagerFactoryBean.setJpaProperties(hibernateProperties);
        return entityManagerFactoryBean;
    }


    @Bean(destroyMethod = "close", name = "dataSource")
    public HikariDataSource dataSource() {
        HikariDataSource mssqlDataSource = new HikariDataSource();
        mssqlDataSource.setPassword(jdbcEnv.getRequiredProperty("postgres.jdbc.password"));
        mssqlDataSource.setUsername(jdbcEnv.getRequiredProperty("postgres.jdbc.username"));
        mssqlDataSource.setDriverClassName(jdbcEnv.getRequiredProperty("postgres.jdbc.driverClassName"));
        mssqlDataSource.setJdbcUrl(jdbcEnv.getRequiredProperty("postgres.jdbc.url"));
        mssqlDataSource.setConnectionTestQuery("SELECT 1");
        mssqlDataSource.setMaximumPoolSize(16);
        mssqlDataSource.setMinimumIdle(1);
        mssqlDataSource.setAutoCommit(true);
        mssqlDataSource.setLeakDetectionThreshold(TimeUnit.MINUTES.toMillis(3));

        return mssqlDataSource;
    }


    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf, DataSource dataSource) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf);
        tm.setDataSource(dataSource);
        return tm;
    }
}
