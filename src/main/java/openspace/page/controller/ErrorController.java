package openspace.page.controller;

import openspace.page.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @RequestMapping("/error/403")
    public ResponseEntity<CommonResponse> forbidden() {
        return ResponseEntity.status(403).body(CommonResponse.error("접근 권한이 없습니다."));
    }
}
