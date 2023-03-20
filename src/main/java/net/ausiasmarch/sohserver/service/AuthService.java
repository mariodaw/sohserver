package net.ausiasmarch.sohserver.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import net.ausiasmarch.sohserver.bean.CaptchaResponse;
import net.ausiasmarch.sohserver.bean.UsuarioBean;
import net.ausiasmarch.sohserver.entity.UsuarioEntity;
import net.ausiasmarch.sohserver.exception.UnauthorizedException;
import net.ausiasmarch.sohserver.helper.JwtHelper;
import net.ausiasmarch.sohserver.helper.RandomHelper;
import net.ausiasmarch.sohserver.helper.TipoUsuarioHelper;
import net.ausiasmarch.sohserver.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private HttpServletRequest oRequest;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpSession oHttpSession;

    public String login(@RequestBody UsuarioBean oUsuarioBean) {
        if (oUsuarioBean.getPassword() != null) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsernameAndPassword(oUsuarioBean.getUsername(), oUsuarioBean.getPassword());
            if (oUsuarioEntity != null) {
                System.out.println(oUsuarioEntity);
                return JwtHelper.generateJWT(oUsuarioBean.getUsername(), oUsuarioEntity.getTipousuario().getId());
            } else {
                throw new UnauthorizedException("username or password incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong password");
        }
    }

    public UsuarioEntity check() {
        String strUsuario = (String) oRequest.getAttribute("usuario");
        if (strUsuario != null) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsername(strUsuario);
            return oUsuarioEntity;
        } else {
            throw new UnauthorizedException("No active session");
        }
    }

    public boolean isAdmin() {
        UsuarioEntity oUsuarioSessionEntity = oUsuarioRepository.findByUsername((String) oRequest.getAttribute("usuario"));
        if (oUsuarioSessionEntity != null) {
            if (oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
                return true;
            }
        }
        return false;
    }

    public void OnlyAdmins() {
        UsuarioEntity oUsuarioSessionEntity = oUsuarioRepository.findByUsername((String) oRequest.getAttribute("usuario"));
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to admin role");
        } else {
            if (!oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
                throw new UnauthorizedException("this request is only allowed to admin role");
            }
        }
    }

    public boolean isLoggedIn() {
        String strUsuario = (String) oRequest.getAttribute("usuario");
        if (strUsuario == null) {
            return false;
        } else {
            return true;
        }
    }

    public Long getUserID() {
        String strUsuario = (String) oRequest.getAttribute("usuario");
        UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsername(strUsuario);
        if (oUsuarioEntity != null) {
            return oUsuarioEntity.getId();
        } else {
            throw new UnauthorizedException("this request is only allowed to auth Usuarios");
        }
    }

    public boolean isUsuario() {
        String strUsuario = (String) oRequest.getAttribute("usuario");
        UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsername(strUsuario);
        if (oUsuarioEntity != null) {
            if (oUsuarioEntity.getTipousuario().getId().equals(TipoUsuarioHelper.MIEMBRO)) {
                return true;
            }
        }
        return false;
    }

    public void OnlyUsuarios() {
        String strUsuario = (String) oRequest.getAttribute("usuario");
        UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsername(strUsuario);
        if (oUsuarioEntity == null) {
            throw new UnauthorizedException("this request is only allowed to Usuario role");
        } else {
            if (!oUsuarioEntity.getTipousuario().getId().equals(TipoUsuarioHelper.MIEMBRO)) {
                throw new UnauthorizedException("this request is only allowed to Usuario role");
            }
        }
    }

    public void OnlyAdminsOrUsuarios() {
        String strUsuario = (String) oRequest.getAttribute("usuario");
        UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsername(strUsuario);
        if (oUsuarioEntity == null) {
            throw new UnauthorizedException("this request is only allowed to admin or reviewer role");
        } else {
            if (oUsuarioEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
            } else {
                if (oUsuarioEntity.getTipousuario().getId().equals(TipoUsuarioHelper.MIEMBRO)) {
                } else {
                    throw new UnauthorizedException("this request is only allowed to admin or reviewer role");
                }
            }
        }
    }

    public void OnlyAdminsOrOwnUsersData(Long id) {
        UsuarioEntity oUsuarioSessionEntity = oUsuarioRepository.findByUsername((String) oRequest.getAttribute("usuario"));
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("no session active");
        } else {
            if (oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
                
            }else if (!oUsuarioSessionEntity.getId().equals(id)) {
                throw new UnauthorizedException("this request is only allowed for your own data");
            }
        }
    }

    public void OnlyOwnUsuariosData(Long id) {
        String strUsuario = (String) oRequest.getAttribute("usuario");
        UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByUsername(strUsuario);
        if (oUsuarioEntity != null) {
            if (oUsuarioEntity.getTipousuario().getId().equals(TipoUsuarioHelper.MIEMBRO)) {
                if (!oUsuarioEntity.getId().equals(id)) {
                    throw new UnauthorizedException("this request is only allowed for your own Usuario data");
                }
            } else {
                throw new UnauthorizedException("this request is only allowed to Usuario role");
            }
        } else {
            throw new UnauthorizedException("this request is only allowed to Usuario role");
        }
    }

}