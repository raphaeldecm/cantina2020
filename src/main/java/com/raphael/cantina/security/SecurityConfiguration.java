package com.raphael.cantina.security;

import javax.sql.DataSource;

import com.raphael.cantina.security.jwt.JWTAuthenticationFilter;
import com.raphael.cantina.security.jwt.JWTLoginFilter;
import com.raphael.cantina.service.UsuarioServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    private final String admin = "ADMIN";
    private final String user = "USER";

    @Autowired
    private UsuarioServiceImpl usuarioService;
    
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    // @Bean
    // public PasswordEncoder passwordEncoder(){
    //     return new BCryptPasswordEncoder();
    // }

    /**Autentication - This user have access? */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Cria um usuario em memoria.
        //Creates a user in memory
        auth.inMemoryAuthentication()
                .withUser("root")
                .password(passwordEncoder.encode("xCvSDs$46gG"))
                .roles("ADMIN", "USER");

        //-----

        //Configura banco de dados de usuarios para realizar autenticações;
        //Configure user database to perform authentication;
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select login,password, enabled from users where login=?")
                .authoritiesByUsernameQuery(
                        "select login, role from usuario_roles where login=?");
    }

    /**This user have authorization? */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/alunos/**").hasAnyRole(user, admin)
                .antMatchers("/compras/**").hasAnyRole(user, admin)
                .antMatchers("/pagamentos/**").hasAnyRole(user, admin)
                .antMatchers("/turmas/**").hasAnyRole(user, admin)
                .antMatchers("/turnos/**").hasAnyRole(user, admin)
                .antMatchers("/usuarios/**").hasRole(admin)
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
            .and()
                
                //Filtra requisições de login (converte entrada para validação)
                //Filters login requests (converts input for validation)
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)

                //Filtra as demais requisições para verificar a presença do JWT no header
                //Filters the others requests to verify presence of JWT in header
                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)

                //Desabilita o CSRF?!
                .csrf().disable();
    }

}