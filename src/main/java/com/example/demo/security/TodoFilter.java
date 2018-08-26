package com.example.demo.security;

import com.example.demo.feign.UserFeign;
import com.example.demo.model.User;
import com.google.common.net.HttpHeaders;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class TodoFilter extends OncePerRequestFilter {

    @Value("privateKey")
    private String privateKey;

    @Autowired
    private UserFeign userFeign;

    String token;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        token = request.getHeader(HttpHeaders.AUTHORIZATION);

        System.out.println("---------------------zull----------------------");

        if (!StringUtils.isEmpty(token)) {
            try {
                Object name = Jwts.parser()
                        .setSigningKey(privateKey.getBytes())
                        .parseClaimsJws(token)
                        .getBody().get("name");

                User user = userFeign.verifyToken(name.toString());
                //if (newToken != null) {
                if(user!=null){
                    String newToken = validateToken();
                    SecurityContextHolder.getContext()
                            .setAuthentication(
                                    new UsernamePasswordAuthenticationToken("user",
                                            null, Collections.emptyList()));

                    RequestContext.getCurrentContext().addZuulRequestHeader(HttpHeaders.AUTHORIZATION, newToken);

                }
            } catch (RuntimeException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, String.format("todo Auntntication failed"));
                //return;
            }
        }
        filterChain.doFilter(request, response);

    }

    private String validateToken() {

        Object id = Jwts.parser()
                .setSigningKey(privateKey.getBytes())
                .parseClaimsJws(token)
                .getBody().get("id");

        Object name = Jwts.parser()
                .setSigningKey(privateKey.getBytes())
                .parseClaimsJws(token)
                .getBody().get("name");

        String userToken = id.toString() + ":" + name.toString();

        return userToken;
    }
}
