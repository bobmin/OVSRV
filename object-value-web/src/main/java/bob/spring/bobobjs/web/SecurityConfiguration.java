package bob.spring.bobobjs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers("/").permitAll();
		/**
        httpSecurity
        	.authorizeRequests()
        		.antMatchers("/resources/**", "/").permitAll()
        		.antMatchers("/admin/**").hasRole("USER")
        		.anyRequest().authenticated()                                                   
        		.and()
            .formLogin()
            	.loginPage("/login")
            	.permitAll();
        */
    }
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
	
}
