package com.raphael.cantina.security;

import javax.sql.DataSource;

// import com.raphael.cantina.security.jwt.JWTAuthenticationFilter;
// import com.raphael.cantina.security.jwt.JWTLoginFilter;
import com.raphael.cantina.service.UsuarioServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    private final String admin = "ADMIN";
    private final String user = "USER";

    
    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    @Autowired
    private UsuarioServiceImpl usuarioService;
    
    @Autowired
    public SecurityConfiguration(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
	protected void configure(HttpSecurity http) throws Exception{
		http
            // .csrf().disable()
            .authorizeRequests()
            
            //.antMatchers(HttpMethod.GET, "/").permitAll()
            .anyRequest().authenticated()
            // .antMatchers(HttpMethod.GET, "/alunos/**").hasRole(admin)
            // .antMatchers(HttpMethod.POST, "/pagamentos/**").hasRole(admin)
            .and()
            .formLogin()
            // .loginPage("/login").permitAll()
            // .and().logout()
            // .logoutUrl("/login?logout")
            .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("admin123")).roles(admin);
        auth.inMemoryAuthentication().withUser("Raphael").password(passwordEncoder.encode("admin123")).roles(admin);
        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("user123")).roles(user);

		auth.userDetailsService(usuarioService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/resources/**", "/static/**");
	}

}