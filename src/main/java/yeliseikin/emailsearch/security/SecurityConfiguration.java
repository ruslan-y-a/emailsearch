package yeliseikin.emailsearch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
   // DefaultSecurityFilterChain ddd;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Entry points
        http.authorizeRequests()
             //   .anyRequest().permitAll()
                .antMatchers("/parse/page").permitAll()
                .antMatchers("/parse/page/**").permitAll()
                .antMatchers("/parse/all").permitAll()
                .antMatchers("/parse/setdepth").permitAll()
                .antMatchers("/users/signin").permitAll()

                 .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/configuration/**").permitAll()
                .antMatchers("/browser/**").permitAll()
                .antMatchers("/explorer/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/public").permitAll()

                .antMatchers("/site/**").hasAnyRole("ROLE_ADMIN","ROLE_CSR")
                .antMatchers("/sites/**").hasAnyRole("ROLE_ADMIN","ROLE_CSR")
                .antMatchers("/sitemails").hasAnyRole("ROLE_ADMIN","ROLE_CSR")
                .antMatchers("/emaillist").hasAnyRole("ROLE_ADMIN","ROLE_CSR")
                // Disallow everything else..
                .anyRequest().authenticated();

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JwtTokenFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/browser/**")//
                .antMatchers("/explorer/**")//
                .antMatchers("/webjars/**")//browser
                .antMatchers("/public");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
  //  @Bean public PasswordEncoder passwordEncoder() {  return new BCryptPasswordEncoder(12);   }
}