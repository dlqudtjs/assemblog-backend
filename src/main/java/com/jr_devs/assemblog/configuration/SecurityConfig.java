package com.jr_devs.assemblog.configuration;

import com.jr_devs.assemblog.exception.CustomExceptionHandler;
import com.jr_devs.assemblog.token.JwtAccessDeniedHandler;
import com.jr_devs.assemblog.token.JwtAuthenticationEntryPoint;
import com.jr_devs.assemblog.token.JwtAuthenticationFilter;
import com.jr_devs.assemblog.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomExceptionHandler customExceptionHandler;
    private final JwtProvider tokenProvider;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception, IOException {
        // csrf().disable() -> 방식은 deprecated 되었으며, 다음과 같은 방식으로 변경되었다.
        httpSecurity
                // 자세한 설명은 Notion 정리
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 세션을 사용하지 않기 때문에 STATELESS 로 설정
                .sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // 권한 설정 시작 (URL 관리)
                // URL 권한 설정은 JwtAuthenticationFilter 에서 shouldNotFilter 메소드를 통해 설정한다.
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests
                            .requestMatchers("/**").permitAll()
                            // api 로 시작하는 경로는 인증이 필요함
                            .requestMatchers("/api/**").authenticated();
                })
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                )

//         지정된 필터 앞에 커스텀 필터를 추가 (UsernamePasswordAuthenticationFilter 보다 먼저 실행된다)
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider, customExceptionHandler),
                        UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    // PasswordEncoder 는 BCryptPasswordEncoder 를 사용하도록 설정
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}