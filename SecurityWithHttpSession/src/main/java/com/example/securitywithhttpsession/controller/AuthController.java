package com.example.securitywithhttpsession.controller;

import com.example.securitywithhttpsession.entity.User;
import com.example.securitywithhttpsession.entity.UserRole;
import com.example.securitywithhttpsession.mapper.UserMapper;
import com.example.securitywithhttpsession.mapper.UserRoleMapper;
import com.example.securitywithhttpsession.service.TestService;
import com.example.securitywithhttpsession.util.AuthenticationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private TestService testService;

    // 注册方法
    @PostMapping("/public/signup")
    public String signUp(@RequestBody User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());

        int i = userMapper.insertUser(user.getUsername(), encodePassword, user.getEmail(), user.getPhoneNumber());
        if (i != 1) {
            return "服务器繁忙，请稍后再试";
        }
        return "用户注册成功";
    }

    // 登录方法
    @PostMapping("/public/login")
    public String logIn(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        try {
            // 将 username 和 password 封装成 UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(username, password);

            // 传递给 AuthenticationManager 进行认证
            Authentication auth = authenticationManager.authenticate(token);

            // 将 Authentication 保存到当前线程的 SecurityContext
            SecurityContextHolder.getContext().setAuthentication(auth);

            // 获取当前请求中的 CSRF Token（此 token 是 Spring Security 自动生成并放入 request 中的）
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

            // 将 token 返回给前端，常见做法是放入响应头，也可以放入响应体
            response.setHeader("X-CSRF-TOKEN", csrfToken.getToken());

            return "登录成功，欢迎用户：" + auth.getName();
        } catch (AuthenticationException e) {
            return "登录失败，用户名或密码错误";
        }
    }

    // 授权方法
    @PostMapping("/grantaccess")
    public String grantAccess(@RequestParam("userid") int userid,
                              @RequestParam("roleid") int roleid) {
        int i = userRoleMapper.insertUserRole(userid, roleid);
        if (i != 1) {
            return "服务器繁忙，请稍后再试";
        }
        return "成功将 " + roleid + " 授权给 " + userid;
    }

    // 测试方法
    @GetMapping("/test")
    public String test() {
        System.out.println("正在执行只有 test:test:test 权限才能执行的 Service 方法");
        System.out.println("现在的 Authentication 信息如下：");
        System.out.println(AuthenticationUtils.getAuthentication());
        String testString = testService.test();
        return testString;
    }

    // 注销方法
    @PostMapping("/public/logOut")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // 尝试获取 HttpSession，如果没有对应的 Session，就返回 null，不要新建 Session
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 使 session 失效，完成注销
            session.invalidate();
        }
        return ResponseEntity.ok("用户已成功注销");
    }
}
