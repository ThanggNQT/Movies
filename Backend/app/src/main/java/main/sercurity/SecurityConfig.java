package main.sercurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration              // để config
@EnableWebSecurity          // để kích hoạt spring security
// @EnableJdbcHttpSession
public class SecurityConfig {
    /**
     * @param http
     * @return
     * @throws Exception 
     */
    @Bean                   // đánh dấu đây là 1 đối tượng được tạo ra sẵn r, khi nào cần thì chỉ cần gọi đến , ko cần new ra nữa. khi dùng thì chỉ cần @Autowired lại thoi
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests(auth ->
            auth.requestMatchers("/admin/**").hasRole("ADMIN")     // Chỉ admin truy cập /admin
            .requestMatchers("/profile/**").authenticated()             // cần đăng nhập để được vào
            .anyRequest().permitAll()                                               // Yêu cầu đăng nhập cho tất cả các URL khác thì cho qua hết 
        ).formLogin(form ->
            form.loginPage("/login")                                      // đường dẫn dẫn đến trang login
            .defaultSuccessUrl("/home")                           // login thành công thì di chuyển đến trang home
            .permitAll()
        ).logout(logout ->
            logout.logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()                                                            // logout user như bình thường
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public DaoAuthenticationProvider authProvider() {
    //     DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // Một class do Spring Security cung cấp, để kiểm tra user trong database
    //     provider.setUserDetailsService(userDetailsService);
    //     provider.setPasswordEncoder(passwordEncoder()); 
    //     return provider;
    // }

    // @Bean
    // public AuthenticationManager authManager(HttpSecurity http) throws Exception { // Là trung tâm xử lý xác thực user: username + password có đúng không
    //     return http.getSharedObject(AuthenticationManagerBuilder.class)
    //         .authenticationProvider(authProvider()) // Gắn provider mà bạn cấu hình ở trên (authProvider) vào
    //         .build(); // Trả về AuthenticationManager đã cấu hình xong
    // }
}
