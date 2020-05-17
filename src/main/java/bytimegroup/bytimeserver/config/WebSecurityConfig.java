package bytimegroup.bytimeserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import bytimegroup.bytimeserver.services.MongoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * @Autowired means service will be connected automatically
	 */
	@Autowired
	MongoUserDetailsService userDetailsService;
	
	/**
	 * disables CSRF protection (unnecessary for API)
	 * all requests to any endpoint must be authorized (except login, register)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
	      .csrf().disable()
	      .authorizeRequests()
	      .antMatchers("/register").permitAll()
	      .anyRequest().authenticated()
	      .and().httpBasic()
	      .and().sessionManagement().disable();
	}

	/**
	* use the BCrypt encoder to hash and compare passwords
	*/
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	* we use MongoUserDetailsService for authentication
	*/
	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService);
	}
	
}
