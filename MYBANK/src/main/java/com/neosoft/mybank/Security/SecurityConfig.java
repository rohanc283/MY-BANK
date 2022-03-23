package com.neosoft.mybank.Security;

import com.neosoft.mybank.Model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    SecurityService securityService;

    //2 users for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    //authorize
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().
                authorizeRequests().
                antMatchers("/application-status","/admins" ,"/branches" , "/customers","/requested_accounts" , "/requests").permitAll().
                antMatchers(HttpMethod.GET, "/approve_requested_accounts/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.GET, "/application-status").hasAnyRole("ADMIN","CUSTOMER").
                antMatchers(HttpMethod.POST, "/add/admin").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.POST, "/add/branch").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.GET, "/deactivate/branch/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.GET, "/activate/branch/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.GET, "/branch/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.PUT, "/update/branch/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.GET, "/customers/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.POST, "/add/customerToBranch/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.DELETE, "/delete/requested_accounts/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.GET, "/under_processing/requested_accounts/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.PUT, "/update/customer/**").hasAnyRole("ADMIN" , "CUSTOMER").
                antMatchers(HttpMethod.GET, "/requested_accounts/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.DELETE, "/delete/request/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.GET, "/under_processing/request/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.POST, "/send_notifications_to_Customer/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.GET, "/notifications").hasAnyRole("ADMIN" , "CUSTOMER").
                antMatchers(HttpMethod.GET, "/approve/request/**").hasAnyRole("ADMIN").
                antMatchers(HttpMethod.GET, "/send_money_to_accNo/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.POST, "/add/requestsOfCustomers/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.GET, "/customer_from_session/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.GET, "/deposit_money/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.GET, "/withdraw_money/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.GET, "/deposited_money/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.GET, "/withdrawal_money/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.GET, "/money_sended/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.GET, "/money_received/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.GET, "/send_money_to_accNo/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.GET, "/seen/noti/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.GET, "/getUnseenNotification/**").hasAnyRole("CUSTOMER").
                antMatchers(HttpMethod.GET, "/noOfbranches").hasAnyRole("CUSTOMER" , "ADMIN").
                antMatchers(HttpMethod.GET, "/noOfCustomers").hasAnyRole( "ADMIN").
                and().csrf().disable();
    }
}
