package com.myspringdemo.blog.configs;


import com.myspringdemo.blog.models.ERole;
import com.myspringdemo.blog.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@Configurable
@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    UserDetailsServiceImpl userServiceIml;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .antMatchers("/blog/*/edit/**").hasAnyAuthority(ERole.EDITOR.name(), ERole.ROLE_ADMIN.name())
                .antMatchers("/users/**", "/blog/*/remove").hasRole("ADMIN")
                .antMatchers("/blog/add").hasAnyAuthority(ERole.EDITOR.name(), ERole.ROLE_ADMIN.name())
                .and()
                .formLogin()
                .and()
                .csrf().ignoringAntMatchers("/api/**").and()
                .logout().logoutSuccessUrl("/")
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userServiceIml);


        return authenticationProvider;
    }

}
