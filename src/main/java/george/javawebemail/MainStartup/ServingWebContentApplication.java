package george.javawebemail.MainStartup;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.session.data.redis.config.annotation.SpringSessionRedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * For tests change the application properties to create-drop and testserver
 */

@EnableAutoConfiguration
@SpringBootApplication
@SpringSessionRedisConnectionFactory
@EntityScan(basePackages = { "george.javawebemail.Entities" })
@EnableJpaRepositories(basePackages = { "george.javawebemail.repositories" })
@ComponentScan(basePackages = { "george.javawebemail.Service" })
@ComponentScan(basePackages = { "george.javawebemail.Controllers", "george.javawebemail.Controllers.Helper" })
@EnableRedisHttpSession
public class ServingWebContentApplication implements WebApplicationInitializer {

	public static void main(String[] args) {

		SpringApplication.run(ServingWebContentApplication.class, args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("george.javawebemail.Controllers");

		servletContext.addListener(new ContextLoaderListener(context));

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
				new DispatcherServlet(context));

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public LettuceConnectionFactory connectionFactory() {
		LettuceConnectionFactory lcf = new LettuceConnectionFactory(
				new RedisStandaloneConfiguration("localhost", 6379));

		return lcf;

	}

	@Bean // (name = "listOperationsCaster")
	// @SessionScope
	public RedisTemplate<String, String> redisTemplate() {
		RedisTemplate<String, String> rt = new RedisTemplate<String, String>();

		rt.setConnectionFactory(connectionFactory());

		return rt;
	}

	@Bean
	// @SessionScope
	public ValueOperations<String, String> valueOperations() {
		ValueOperations<String, String> vo = redisTemplate().opsForValue();
		return vo;
	}

	// @Primary
	// @Bean(name = "dataSource")
	// @ConfigurationProperties(prefix = "spring")
	// public DataSourceProperties mainDataSourceProperties() {
	// return new DataSourceProperties();
	// }

	// @Bean
	// @Primary
	// public DataSource dataSource() {
	// return mainDataSourceProperties().initializeDataSourceBuilder().build();
	// }

	// // beginning of secondary data source
	// @Bean
	// @ConfigurationProperties(prefix = "test.prop")
	// public DataSourceProperties secondaryDataSourceProperties() {
	// return new DataSourceProperties();
	// }

	// @Bean
	// public DataSource secondaryDataSource() {
	// return secondaryDataSourceProperties().initializeDataSourceBuilder().build();
	// }

}
