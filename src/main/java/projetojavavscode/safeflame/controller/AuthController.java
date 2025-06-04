package projetojavavscode.safeflame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projetojavavscode.safeflame.model.Usuario;
import projetojavavscode.safeflame.service.TokenService;

@RestController
public class AuthController {

    public record Token (String token, String email){}
    public record Credentials (String email, String password){}

    @Autowired
    private TokenService tokenService;

    @Autowired
    AuthenticationManager authManager;

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials){
        var authentication = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());
        var usuario = (Usuario) authManager.authenticate(authentication).getPrincipal();

        return tokenService.createToken(usuario);
    }
}
