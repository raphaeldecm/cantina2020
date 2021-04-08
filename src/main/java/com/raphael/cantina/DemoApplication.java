package com.raphael.cantina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

	public static final int BCRYPT_STRENGTH = 6;

    /**
     * Nesse projeto estamos definindo o DriverManagerSource para a aplicação. Nesse projeto vai ser responsável
     * em configurar nosso banco para autenticação.
     * <p>
     * In this project we are defining the DriverManagerSource for the application. In this project
     * you will be responsible for setting up our bank for authentication.
     *
     * @return DriverManagerDataSource
     */
    @Bean("dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/db_cantina_dev?allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true&useSSL=false");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("Carvalho.2");
        return driverManagerDataSource;
    }

    /**
     * Define o passwordEncoder da aplicação
     * <p>
     * Define the passwordEncoder application
     *
     * @return BCryptPasswordEncoder
     */
    @Bean()
    public PasswordEncoder passwordEncoder() {
        //https://www.browserling.com/tools/bcrypt
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH);
        //return NoOpPasswordEncoder.getInstance();
    }
	
	public static void main(String[] args) {
//		System.setProperty("server.servlet.context-path", "/geces");
		SpringApplication.run(DemoApplication.class, args);
	}

}
