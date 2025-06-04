package projetojavavscode.safeflame.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import projetojavavscode.safeflame.model.Usuario;
import projetojavavscode.safeflame.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

     private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        var header = request.getHeader("Authorization");
        log.info("Authorization header recebido: {}", header);

        if (header == null || !header.startsWith("Bearer ")) {
            log.warn("Cabeçalho Authorization ausente ou inválido.");
            filterChain.doFilter(request, response);
            return;
        }

        var token = header.replace("Bearer ", "");
        log.info("Token extraído: {}", token);

        try {
            Usuario user = tokenService.getUserFromToken(token);
            if (user != null) {
                log.info("Usuário autenticado: {}", user.getUsername());
                var authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Erro ao validar token", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido ou expirado.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
