package net.ausiasmarch.sohserver.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.sohserver.entity.TipoEventoEntity;
import net.ausiasmarch.sohserver.exception.ResourceNotFoundException;
import net.ausiasmarch.sohserver.helper.ValidationHelper;
import net.ausiasmarch.sohserver.repository.TipoEventoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TipoEventoService {
    
    @Autowired
    TipoEventoRepository oTipoEventoRepository;

 
    public TipoEventoEntity get(Long id) {
        //oAuthService.OnlyAdmins();
        return oTipoEventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento with id: " + id + " not found"));
    }
    public void validate(Long id) {
        if (!oTipoEventoRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(TipoEventoEntity oTipoEventoEntity) {
        ValidationHelper.validateStringLength(oTipoEventoEntity.getNombre(), 2, 100, "campo nombre de Tipoevento (el campo debe tener longitud de 2 a 100 caracteres)");
    }

    public List<TipoEventoEntity> generateUsersType() {
        List<TipoEventoEntity> usersTypeList = new ArrayList<>();
        usersTypeList.add(new TipoEventoEntity(1L));
        usersTypeList.add(new TipoEventoEntity(2L));
        return usersTypeList;
    }
    public List<TipoEventoEntity> all() {
        return oTipoEventoRepository.findAll();
    }
    public Long count() {
        return oTipoEventoRepository.count();  
    }
    public Long update(TipoEventoEntity oTipoEventoEntity) {
       // oAuthService.OnlyAdmins();
        validate(oTipoEventoEntity.getId());
        validate(oTipoEventoEntity);
        return oTipoEventoRepository.save(oTipoEventoEntity).getId();
    }
    public Long generate() {
        //oAuthService.OnlyAdmins();
        List<TipoEventoEntity> usersTypeList = generateUsersType();

        for (int i = usersTypeList.size() - 1; i >= 0; i--) {
            oTipoEventoRepository.save(usersTypeList.get(i));
        }
        return oTipoEventoRepository.count();
    }

   public Page<TipoEventoEntity> getPage(Pageable oPageable, String strFilter) {
        //oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        return oTipoEventoRepository.findAll(oPageable);
    }
}
