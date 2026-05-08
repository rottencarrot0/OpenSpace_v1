package openspace.page.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.PathContainer;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class UrlCheckFilter extends OncePerRequestFilter {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final PathPatternParser pathPatternParser = new PathPatternParser();

    public UrlCheckFilter(RequestMappingHandlerMapping mappingHandlerMapping) {
        this.requestMappingHandlerMapping = mappingHandlerMapping;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // /.well-known/appspecific/com.chrome.devtools.json 제거
        if(isIgnorePath(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 정적 리소스는 검사 제외한다.
        if(isStaticResource(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }


        boolean registered = isRegisteredControllerUrl(requestURI);

        if(!registered) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            log.info("UrlCheckFilter 필터 종료 {}", requestURI);
        }
    }


    private boolean isRegisteredControllerUrl(String requestURI) {
        PathContainer path = PathContainer.parsePath(requestURI);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        log.info("UrlCheckFilter 필터 실행 {}", requestURI);

//        handlerMethods.entrySet().stream().forEach(entry -> log.info(entry.getKey() +" : "+ entry.getValue()));

        return handlerMethods.keySet()
                .stream()
                .flatMap(info -> info.getPatternValues().stream())
                .anyMatch(pattern -> pathPatternParser.parse(pattern).matches(path));
    }

    private boolean isStaticResource(String requestURI) {
        return requestURI.startsWith("/js/")
                || requestURI.startsWith("/css/")
                || requestURI.endsWith(".js")
                || requestURI.endsWith(".css");
    }

    private boolean isIgnorePath(String requestURI) {
        return requestURI.startsWith("/.well-known/");
    }
}
