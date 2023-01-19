package net.ausiasmarch.sohserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.sohserver.entity.TipoUsuarioEntity;
import net.ausiasmarch.sohserver.exception.ResourceNotFoundException;
import net.ausiasmarch.sohserver.helper.ValidationHelper;
import net.ausiasmarch.sohserver.repository.TipoUsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class TipoUsuarioService {
    
    @Autowired
    TipoUsuarioRepository oTipoUsuarioRepository;
    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oTipoUsuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(TipoUsuarioEntity oTipoUsuarioEntity) {
        ValidationHelper.validateStringLength(oTipoUsuarioEntity.getNombre(), 2, 100, "campo nombre de Tipoevento (el campo debe tener longitud de 2 a 100 caracteres)");
    }

    
    public List<TipoUsuarioEntity> generateUsersType() {
        List<TipoUsuarioEntity> usersTypeList = new ArrayList<>();
        usersTypeList.add(new TipoUsuarioEntity(1L));
        usersTypeList.add(new TipoUsuarioEntity(2L));
        return usersTypeList;
    }
    public TipoUsuarioEntity get(Long id) {
        //oAuthService.OnlyAdmins();
        return oTipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento with id: " + id + " not found"));
    }
    public List<TipoUsuarioEntity> all() {
        return oTipoUsuarioRepository.findAll();
    }
    public Long update(TipoUsuarioEntity oTipoUsuarioEntity) {
       // oAuthService.OnlyAdmins();
        validate(oTipoUsuarioEntity.getId());
        validate(oTipoUsuarioEntity);
        return oTipoUsuarioRepository.save(oTipoUsuarioEntity).getId();
    }
}
