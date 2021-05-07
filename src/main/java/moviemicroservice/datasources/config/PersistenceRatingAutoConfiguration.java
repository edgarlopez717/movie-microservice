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
  basePackages = "moviemicroservice.rating",
  entityManagerFactoryRef = "ratingEntityManager",
  transactionManagerRef = "ratingTransactionManager")
public class PersistenceRatingAutoConfiguration {
	@Autowired
	private Environment env;
	
    @Bean(name = "ratingDataSource")
    @ConfigurationProperties(prefix="spring.second-datasource")
    public DataSource ratingDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    // ratingEntityManager bean 
    @Bean(name = "ratingEntityManager")
    public LocalContainerEntityManagerFactoryBean ratingEntityManager(
            final EntityManagerFactoryBuilder builder,
            @Qualifier("ratingDataSource") final DataSource dataSource) {
//        // dynamically setting up the hibernate properties for each of the datasource.
        final Map<String, String> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//        // in springboot2 the dialect can be automatically detected.
//        // setting up here just to avoid any incident.
//        //properties.put("spring.jpa.database-platform", env.getProperty("spring.jpa.database-platform"));
        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("moviemicroservice.rating")
                .persistenceUnit("rating")
                .build();
    }

    // ratingTransactionManager bean
    @Bean(name = "ratingTransactionManager")
    public PlatformTransactionManager ratingTransactionManager(
            @Qualifier("ratingEntityManager") final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
