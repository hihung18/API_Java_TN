package com.example.api_java.security;



import com.example.api_java.security.jwt.JwtAuthEntryPoint;
import com.example.api_java.security.jwt.JwtAuthTokenFilter;
import com.example.api_java.security.jwt.JwtUtils;
import com.example.api_java.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtAuthEntryPoint unauthorizedHandler;

    private final JwtUtils jwtUtils;

    public WebSecurityConfig(
            UserDetailsServiceImpl userDetailsService,
            JwtAuthEntryPoint unauthorizedHandler,
            JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        // TODO
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // cấu hình phân quyền
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().permitAll(); // Cho phép truy cập mà không cần xác thực
//        http.csrf().disable(); // Tắt CSRF protection (khuyến cáo chỉ sử dụng trong môi trường phát triển)
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/auth/**",
                        "/api/v1/public**",
                        "/swagger-ui**",
                        "/swagger-ui/**",
                        "/api/tasks/**",
                        "/v3/api-docs/**")
                .permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/businessTrips**",
                        "/api/businessTrips/**",
                        "/api/users/**",
                        "/api/users**",
                        "/api/images**",
                        "/api/images/**",
                        "/api/tasks**",
                        "/api/tasks/**",
                        "/api/reports**",
                        "/api/reports/**",
                        "/api/rates**",
                        "/api/rates/**",
                        "/api/partners**",
                        "/api/partners/**")
                .permitAll()
                .antMatchers(HttpMethod.DELETE,
                        "/api/businessTrips/**",
                        "/api/images/**",
                        "/api/users/**",
                        "/api/tasks/**",
                        "/api/reports/**")
                .hasRole("QL")
                .antMatchers(HttpMethod.DELETE,
                        "/api/images/**")
                .hasRole("NV")
                .antMatchers(HttpMethod.POST,
                        "/api/businessTrips**",
                        "/api/images**",
                        "/api/tasks**",
                        "/api/users**",
                        "/api/reports**",
                        "/api/rates**",
                        "/api/partners**")
                .hasRole("QL")
                .antMatchers(HttpMethod.POST,
                        "/api/reports**",
                        "/api/images**",
                        "/api/users/**",
                        "/api/rates**")
                .hasRole("NV")
                .antMatchers(HttpMethod.PUT,
                        "/api/businessTrips/**",
                        "/api/images/**",
                        "/api/tasks/**",
                        "/api/users/**",
                        "/api/reports/**",
                        "/api/rates/**",
                        "/api/partners/**")
                .hasRole("QL")
                .antMatchers(HttpMethod.PUT,
                        "/api/reports/**",
                        "/api/users/**",
                        "/api/tasks/**",
                        "/api/images**",
                        "/api/rates/**")
                .hasRole("NV")
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}