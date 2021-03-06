package com.agile.monkeys.demo.customer.config;

import com.agile.monkeys.demo.customer.service.UserDetailsServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.agile.monkeys.demo.data.UserRole.ADMIN;
import static com.agile.monkeys.demo.data.UserRole.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Getter
    @Value("${customer.image.url_path}")
    private String urlPath;

    @Autowired
    public UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * Note that Spring recommend using csrf when serving browser clients csrf can be disabled if the API is not
     * requested directly by a browser client.
     * @param http HttpSecurity instance
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                    .antMatchers("/customer/**").hasAnyRole(USER.toString(), ADMIN.toString())
                    .antMatchers("/" + urlPath + "/**").hasAnyRole(USER.toString(), ADMIN.toString());
        http.httpBasic();
        http.headers().contentSecurityPolicy("script-src 'self'");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
