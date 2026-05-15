package openspace.page.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import openspace.page.domain.SessionConst;
import openspace.page.domain.User;
import openspace.page.domain.UserRole;
import org.springframework.web.servlet.HandlerInterceptor;

public class GuestOnlyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User loginUser = (User) request.getSession().getAttribute(SessionConst.LOGIN_USER);
        if(loginUser.getRole() != UserRole.GUEST) {
//            response.sendRedirect(request.getContextPath() + "/error/403");
            // 화면 로드를 위해선 forward 방식으로 하자
            request.getRequestDispatcher("/WEB-INF/views/error/403.jsp").forward(request, response);
            return false;
        }
        return true;
    }
}
