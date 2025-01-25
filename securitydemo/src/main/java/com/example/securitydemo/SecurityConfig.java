package com.example.securitydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity//1
@EnableMethodSecurity
public class SecurityConfig {
    //2

    @Autowired
    DataSource dataSource;//3

    @Bean//4
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/h2-console/**").permitAll().//5
                anyRequest().authenticated());//6
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //7
        //http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));// 8
        http.csrf(AbstractHttpConfigurer::disable);//9
        return http.build();
    }

//    @Bean//in memory authentication
//    public UserDetailsService userDetailsService() {//part of spring framework. manages users in memory->non persistent
//        UserDetails user1 = User.withUsername("user1").password("{noop}password1")//{noop} for telling to store password in plain text, not encrypted
//                .roles("USER").build();
//        UserDetails admin = User.withUsername("admin").password("{noop}password2")
//                .roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user1, admin);
//    }

        @Bean//in database now!!
    public UserDetailsService userDetailsService() {//10
//            UserDetails user1 = User.withUsername("user1").password("{noop}password1")//{noop} for telling to store password in plain text, not encrypted
//                    .roles("USER").build();//11
            UserDetails user1 = User.withUsername("user1").password(passwordEncoder().encode("password1"))
                    .roles("USER").build();//12

//            UserDetails admin = User.withUsername("admin").password("{noop}password2")
//                    .roles("ADMIN").build();//without encryption
            UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("password2"))
                    .roles("ADMIN").build();//with encryption  =>    you'll see password encrypted in database h2 now

//13
            JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
            jdbcUserDetailsManager.createUser(user1);
            jdbcUserDetailsManager.createUser(admin);
            return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
