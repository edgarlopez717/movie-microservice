package moviemicroservice.datasources.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
  basePackages = "moviemicroservice.movie",
  entityManagerFactoryRef = "movieEntityManager",
  transactionManagerRef = "movieTransactionManager")
public class PersistenceMovieAutoConfiguration {
	@Autowired
	private Environment env;
	
    @Primary
    @Bean(name = "movieDataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource movieDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    // movieEntityManager bean 
    @Bean(name = "movieEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean movieEntityManager(
            final EntityManagerFactoryBuilder builder,
            @Qualifier("movieDataSource") final DataSource dataSource) {
//        // dynamically setting up the hibernate properties for each of the datasource.
        final Map<String, String> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//        // in springboot2 the dialect can be automatically detected.
//        // setting up here just to avoid any incident.
//        //properties.put("spring.jpa.database-platform", env.getProperty("spring.jpa.database-platform"));
        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("moviemicroservice.movie")
                .persistenceUnit("movie")
                .build();
    }

    // movieTransactionManager bean
    @Bean(name = "movieTransactionManager")
    @Primary
    public PlatformTransactionManager movieTransactionManager(
            @Qualifier("movieEntityManager") final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
