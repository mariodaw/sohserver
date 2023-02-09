package net.ausiasmarch.sohserver.api;

import net.ausiasmarch.sohserver.bean.UsuarioBean;
import net.ausiasmarch.sohserver.entity.UsuarioEntity;
import net.ausiasmarch.sohserver.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    AuthService oAuthService;

    @GetMapping()
    public ResponseEntity<UsuarioEntity> check() {
        return new ResponseEntity<UsuarioEntity>(oAuthService.check(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> login(@org.springframework.web.bind.annotation.RequestBody UsuarioBean oUsuarioBean) {
        return new ResponseEntity<String>("\"" + oAuthService.login(oUsuarioBean) + "\"", HttpStatus.OK);
    }

    @GetMapping("/getId")
    public ResponseEntity<Long> getId() {
        return new ResponseEntity<Long>(oAuthService.getUserID(), HttpStatus.OK);
    }

}