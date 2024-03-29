package com.smart.smarycontactmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class MyConfig  {

    // @SuppressWarnings("deprecation")

    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasRole("USER")
            .anyRequest().permitAll()
        )
        .formLogin(formLogin -> formLogin
            // .loginPage("/signin")
            .loginProcessingUrl("/dologin")
            .defaultSuccessUrl("/user/index", true)
            .failureUrl("/login-fail")
        )
       
        .build();
}


 

    @Bean
     AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
      return configuration.getAuthenticationManager();
    }

    @Bean
     DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
       provider.setUserDetailsService(this.getUSerDetailsService());
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;

     }
    
    @Bean
     UserDetailsService getUSerDetailsService(){
        return new UserDetailsServiceImpl();
    }
    
    @Bean
     BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
    // @Bean
    //  DaoAuthenticationProvider authenticationProvider(){
    //     DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();

    //     daoAuthenticationProvider.setUserDetailsService(this.getUSerDetailsService());
    //     daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    //     return daoAuthenticationProvider;
    // }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.authenticationProvider(authenticationProvider());
    // }

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {

    //     // http.authorizeRequests(authorize -> 
    //     //     authorize
    //     //     .antMatchers("/admin").hasRole("ADMIN")
    //     //     .antMatchers("/user/**").hasRole("USER")
    //     //     .antMatchers("/**").permitAll())
    //     //      .formLogin(login -> login
    //     //     .permitAll())
    //     // .csrf().disable();
    //    http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/user/**").hasRole("USER")
    //    .antMatchers("/**").permitAll().and().formLogin().and().csrf().disable();
    // }




       // @Bean
    //  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http.authorizeRequests()
    //             .requestMatchers("/admin/**").hasRole("ADMIN")
    //             .requestMatchers("/user/**").hasRole("USER")
    //             .requestMatchers("/**").permitAll().and().formLogin().loginPage("/signin").and().csrf().disable();

    //     http.formLogin().defaultSuccessUrl("/user/index", true);

    //     return http.build();
    // }


    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
       
    //      httpSecurity
    //     .authorizeRequests(authorizeRequests -> authorizeRequests
    //         .requestMatchers("/admin").hasRole("ADMIN")
    //         .requestMatchers("/user/**").hasRole("USER")
    //         .requestMatchers("/**").permitAll()
    //     )
    //     .formLogin(login -> login
    //       .loginPage("/signin")
    //       .loginProcessingUrl("/dologin")
    //       .defaultSuccessUrl("/user/index")
    //       .failureUrl("/login-fail")
            
    //     );
       
    //     // .csrf().disable();

    //     return httpSecurity.build();

       
    // }

    

