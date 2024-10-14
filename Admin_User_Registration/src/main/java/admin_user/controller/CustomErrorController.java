package admin_user.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, WebRequest webRequest, Model model) {
        // Retrieve error attributes
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        model.addAttribute("status", errorDetails.get("status"));
        model.addAttribute("error", errorDetails.get("error"));
        model.addAttribute("message", errorDetails.get("message"));
        model.addAttribute("path", errorDetails.get("path"));
        
        // Determine specific error page
        Integer statusCode = (Integer) errorDetails.get("status");
        if (statusCode == 404) {
            return "error/404";
        } else if (statusCode == 500) {
            return "error/500";
        }
        
        return "error/default";
    }
}
