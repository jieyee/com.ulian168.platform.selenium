package com.ulian168.platform.selenium.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Configuration
//@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Value("${platform.oauth.in.memory.clientId:admin}")
    private String clientId;
    @Value("${platform.oauth.in.memory.password:admin}")
    private String password;
    
    @Autowired  
    Environment env;
    
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        SpringSecurityDialect dialect = new SpringSecurityDialect();
        return dialect;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        String contextPath = env.getProperty("management.context-path");
//        
//        http.authorizeRequests().antMatchers("/**"+contextPath+"/**", "/icon/**", "/css/**", "/fonts/**", "/images/**", "/js/**","/js/**/**", "/druid/**").permitAll()
//                .anyRequest().fullyAuthenticated()
//                .and().formLogin().loginPage("/login").failureUrl("/login?error")
//                .successHandler(new SimpleUrlAuthenticationSuccessHandler("/")).permitAll()
//                .and().logout().permitAll();
//
//        http.csrf().disable();
//        http.headers().frameOptions().sameOrigin();
//        session失效后跳转
//        http.sessionManagement().invalidSessionUrl("/login");
//        http.headers().frameOptions().sameOrigin();
        
        http.authorizeRequests().antMatchers("/css/**", "/fonts/**", "/images/**", "/js/**/**", "/druid/**").permitAll()
        .anyRequest().fullyAuthenticated()
        .and().formLogin().loginPage("/login").failureUrl("/login?error").successHandler(new SimpleUrlAuthenticationSuccessHandler("/")).permitAll()
        .and().logout().permitAll();
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

    }
    

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(clientId).password(password).roles("USER");
    }

}
