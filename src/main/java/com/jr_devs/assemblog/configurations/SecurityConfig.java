package com.jr_devs.assemblog.configurations;

import com.jr_devs.assemblog.token.JwtAccessDeniedHandler;
import com.jr_devs.assemblog.token.JwtAuthenticationEntryPoint;
import com.jr_devs.assemblog.token.JwtAuthenticationFilter;
import com.jr_devs.assemblog.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider tokenProvider;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // csrf().disable() -> 방식은 deprecated 되었으며, 다음과 같은 방식으로 변경되었다.
        httpSecurity
                // 자세한 설명은 Notion 정리
                .csrf((csrf) -> csrf.disable())
                .formLogin((formLogin) -> formLogin.disable())
                .httpBasic((httpBasic) -> httpBasic.disable())
                // 세션을 사용하지 않기 때문에 STATELESS 로 설정
                .sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // 권한 설정 시작 (URL 관리)
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests
                            // "/users/signup", "/users/login" 은 누구나 접근 가능
                            .requestMatchers("/users/signup", "/users/login").permitAll()
                            // 그외 모든 요청은 인증된 회원만 접근 가능
                            .anyRequest().authenticated();
                })
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                )
                // 지정된 필터 앞에 커스텀 필터를 추가 (UsernamePasswordAuthenticationFilter 보다 먼저 실행된다)
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    // PasswordEncoder 는 BCryptPasswordEncoder 를 사용하도록 설정
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}