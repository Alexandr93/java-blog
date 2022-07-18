package com.myspringdemo.blog.configs;


import com.myspringdemo.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configurable
@Configuration

@EnableWebSecurity
public class SecurityConfig  {
    @Autowired
    UserService userService;
  /*  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()

                .antMatchers("/blog/add").hasRole("ADMIN")
                .and().formLogin()
                .loginPage("/login")
                .permitAll()

                .and().logout().permitAll();



    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .antMatchers("/blog/*/edit/**").hasRole("ADMIN")


                .antMatchers("/users/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()

                .logout().logoutSuccessUrl("/")
        ;

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);


        return authenticationProvider;
    }

}
