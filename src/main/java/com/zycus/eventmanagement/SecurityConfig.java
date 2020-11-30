//package com.zycus.eventmanagement;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
////	// Authentication : User --> Roles
//	protected void configure(AuthenticationManagerBuilder auth)
//			throws Exception {
//		auth.inMemoryAuthentication()
//		.withUser("user").password(passwordEncoder().encode("user")).roles("USER")
//		.and()
//		.withUser("admin1").password(passwordEncoder().encode("admin1")).roles("USER", "ADMIN");
//	}
//
////	// Authorization : Role -> Access
//	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic()
//		.and()
//		.authorizeRequests()
//		.antMatchers("/dashboad1/**").hasRole("USER")
//		.antMatchers("/dashboad2/**").hasRole("USER")
//		.antMatchers("/dashboad3/**").hasRole("USER")
//		.antMatchers("/**").hasRole("ADMIN")
//		.and()
//		.csrf().disable()
//		.headers().frameOptions().disable();
//	}
//	
//	@Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}