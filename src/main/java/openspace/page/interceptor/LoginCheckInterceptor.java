package openspace.page.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import openspace.page.domain.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            String redirectUrl = request.getRequestURI();
            response.sendRedirect(request.getContextPath() + "/user/login?redirectUrl=" + redirectUrl);
            return false;
        }
        return true;
    }
}
