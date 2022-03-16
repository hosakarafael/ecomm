package com.rafaelhosaka.ecomm.security;

import com.rafaelhosaka.ecomm.account.UserRole;
import com.rafaelhosaka.ecomm.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import static com.rafaelhosaka.ecomm.account.UserRole.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Profile("dev")
public class DevSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("customRequestCache")
    RequestCache requestCache;

    @Autowired
    public DevSecurityConfiguration(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestCache()
                    .requestCache(requestCache)
                .and()
                .sessionManagement()
                .invalidSessionUrl("/invalid-session")
                .and()
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/**","/css/**","/js/**","/webjars/**","/img/**","/h2-console/**").permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                .headers().frameOptions().sameOrigin().and()
                .formLogin()
                    .failureUrl("/login-error")
                    .defaultSuccessUrl("/")
                    .loginPage("/login").permitAll()
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login")
                .and()
                .rememberMe().
                    tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                    .rememberMeParameter("remember-me")
                    .userDetailsService(userDetailsService);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}