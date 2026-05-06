package openspace.page.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import openspace.page.domain.SessionConst;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] no_check_pass = {"/", "/user/login", "/user/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        
        // 현재 등록되어 있는 url 들과 매칭이 되는지 확인하기


        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            if(isNoCheckPass(requestURI)) {
                // 인증 로직 시작
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);

                    httpResponse.sendRedirect("/user/login?redirectURL=" + requestURI);
                    return;
                }
            }

            chain.doFilter(request, response);
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }

    }

    private boolean isNoCheckPass(String requestURI) {
        return !PatternMatchUtils.simpleMatch(no_check_pass, requestURI);
    }

}
