package miu.edu.payment.security;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ServiceFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;

    public ServiceFilter(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        boolean validated = true;
        if (request.getRequestURI().contains("/api/test")||
            request.getRequestURI().contains("/api/pay")) {
            final String serviceToken = request.getHeader("ServiceToken");
            if (serviceToken != null) {
                validated = jwtHelper.validateServiceToken("payment-service", serviceToken);
            }
        }
        if (validated)
            filterChain.doFilter(request, response);
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Service is protected");
    }
}
