package com.junianto.apps.test2.configuration;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.junianto.apps.test2.common.AppsConstanta;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final Logger log = LogManager.getLogger(this.getClass());

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	
//	@Value("${spring.queries.users-query}")
	private String usersQuery = "select u.username, u.password, u.active from m_user u where u.username=?";

//	@Value("${spring.queries.roles-query}")
	private String rolesQuery = "select u.username, r.role, CONCAT(b.id, ' - ',b.nama_cabang) nama_cabang from m_user u " + 
			"	 inner join m_user_role ur on(u.user_id=ur.user_id) " + 
			"	 inner join m_role r on(ur.role_id=r.role_id) " + 
			"	 left join bank_branch b on b.id=u.branch " + 
			"	where u.username=?";

	private String authorityAccess = "/**"; // "/admin/**"

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll()
			.antMatchers("/errors/**").permitAll()
			.antMatchers("/api/**").permitAll()
			.antMatchers("/testAPI/**").permitAll()
			.antMatchers("/fragments/**").permitAll()
			.antMatchers(authorityAccess)
			.hasAnyAuthority("ADMIN","INPUTER","CHECKER")
//			.hasAuthority("ADMIN")
			.anyRequest()
			.authenticated().and().csrf().disable()
			.formLogin().loginPage("/login")
			.failureUrl("/login?error=true")
			.defaultSuccessUrl(AppsConstanta.PATH_HOME)
			.usernameParameter("username")
			.passwordParameter("password")
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher(AppsConstanta.PATH_LOGOUT))
			.logoutSuccessUrl(AppsConstanta.PATH_HOME)
			.and().exceptionHandling().accessDeniedPage(AppsConstanta.PAGE_DENIED);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
			"/resources/**", 
			"/static/**", 
			"/webfonts/**", 
			"/fonts/**", 
			"/plugins/**", 
			"/css/**", 
			"/js/**", 
			"/images/**", 
			"/apps/**"
//			,"/apps/fragments/**"
			, "/errors/**");
	}

}
