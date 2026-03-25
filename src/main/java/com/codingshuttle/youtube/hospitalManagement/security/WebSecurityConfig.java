package com.codingshuttle.youtube.hospitalManagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

//    private final PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/public/**", "/auth/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/doctors/**").hasAnyRole("ADMIN", "DOCTORS")
                                .anyRequest().authenticated()
                        );

//                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }



//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user1 = User.withUsername("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("ADMIN").build();
//
//        UserDetails user2 = User.withUsername("prathmesh")
//                .password(passwordEncoder.encode("prath123"))
//                .roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }



}
