package com.example.securitywithhttpsession.configuration;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableMethodSecurity // 1. 启用方法级别的访问控制
@EnableWebSecurity // 2. 启用 Spring Security 安全机制
public class SecurityConfiguration {

    // 详见下文：配置 CSRF 攻击防护（配置 CsrfFilter 过滤器）
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {

        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository(); // CSRF Token 生成与加载的位置，这里是生成并保存在 HttpSession 中，并从 HttpSession 中加载

        repository.setHeaderName("X-CSRF-TOKEN"); // 前端可在 X-CSRF-TOKEN 请求头中携带 CSRF Token
        repository.setParameterName("_csrfToken"); // 前端可在 _csrfToken 请求体中携带 CSRF Token
        return repository;
    }

    // 4. 配置 AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // 从 Spring Security 配置中获取其默认的 AuthenticationManager 实例
    }

    // 5. 配置 密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 返回合适的实现类
    }

    // 配置 SecurityFilterChain，即我们熟知的那些过滤器链
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 6. 禁用默认表单登录，同时禁用 UsernamePasswordAuthenticationFilter 过滤器
                .formLogin(form -> {
                    form.disable();
                })

                // 7. 禁用默认注销功能
                .logout(logout -> {
                    logout.disable();
                })

                // 8. 配置资源级别的访问控制
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/public/**").permitAll()
                            .anyRequest().authenticated(); // 其他所有路径均需通过认证
                })

                // 9. 配置用户 未认证、权限不足 的处理
                .exceptionHandling(handler -> {
                    handler
                            // 未认证时的响应（处理 AuthenticationException 异常）
                            .authenticationEntryPoint((request, response, authException) -> {
                                response.setContentType("application/json;charset=UTF-8");
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().write("{\"error\":\"未认证，请先登录\"}");
                            })
                            // 权限不足时的响应（处理 AccessDeniedException 异常）
                            .accessDeniedHandler((request, response, accessDeniedException) -> {
                                response.setContentType("application/json;charset=UTF-8");
                                // 先判断是否是 CSRF 相关异常
                                if (accessDeniedException instanceof MissingCsrfTokenException
                                        || accessDeniedException instanceof InvalidCsrfTokenException) {
                                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    response.getWriter().write("{\"error\":\"CSRF Token 校验失败，无法访问此资源\"}");
                                } else {
                                    // 普通权限不足
                                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    response.getWriter().write("{\"error\":\"权限不足，无法访问此资源\"}");
                                }
                            });
                })

                // 10. 配置 HttpSession
                .sessionManagement(session -> {
                    session
                            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                })

                // 11. 配置 SecurityContextPersistenceFilter 过滤器
                .securityContext(security -> {
                    security.requireExplicitSave(false);
                })

                // 12. 配置 CSRF 攻击防护（配置 CsrfFilter 过滤器）
                .csrf(csrf -> {
                    csrf
                            .ignoringRequestMatchers("/public/**") // 忽略对这些路径的 CSRF 保护（默认全部保护）
                            .csrfTokenRepository(csrfTokenRepository()); // 使用我们自定义的 Token 存储库
                });
        return httpSecurity.build(); // 构建 SecurityFilterChain 对象
    }
}
