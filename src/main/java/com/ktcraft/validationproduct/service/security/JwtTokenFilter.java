package com.ktcraft.validationproduct.service.security;

import com.ktcraft.validationproduct.service.exceptions.InvalidTokenException;
import com.ktcraft.validationproduct.service.security.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Value("${application.product.id}")
    private String productId;

    @Autowired
    public JwtTokenFilter(final JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        final Claims claims = jwtTokenUtil.decodeJWT(token);

        final AccountAuthentication accountAuthentication = new AccountAuthentication();
        accountAuthentication.setOrganizationId(claims.get("organization_id", Long.class));
        accountAuthentication.setAccountId(claims.get("account_id", Long.class));
        accountAuthentication.setProductId(claims.get("product_id", Integer.class));

        if (accountAuthentication.getOrganizationId() == null || accountAuthentication.getAccountId() == null
        || accountAuthentication.getProductId() == null) {
            throw new InvalidTokenException();
        }
        if (StringUtils.hasText(productId)) {
            if (!Integer.valueOf(productId).equals(accountAuthentication.getProductId())) {
                throw new InvalidTokenException();
            }
        }

        SecurityContextHolder.getContext().setAuthentication(accountAuthentication);
        filterChain.doFilter(request, response);
    }
}
