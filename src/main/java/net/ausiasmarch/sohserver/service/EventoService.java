package net.ausiasmarch.sohserver.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.sohserver.helper.ValidationHelper;
import net.ausiasmarch.sohserver.entity.EventoEntity;
import net.ausiasmarch.sohserver.exception.ResourceNotFoundException;
import net.ausiasmarch.sohserver.exception.ResourceNotModifiedException;
import net.ausiasmarch.sohserver.repository.EventoRepository;
import net.ausiasmarch.sohserver.repository.TipoEventoRepository;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

@Service
public class EventoService {

    @Autowired
    TipoEventoService oTipoEventoService;
    @Autowired
    TipoEventoRepository oTipoEventoRepository;

    @Autowired
    EventoRepository oEventoRepository;

    public EventoEntity get(Long id) {
        //oAuthService.OnlyAdmins();
        return oEventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento with id: " + id + " not found"));
    }

    public Long count() {
       // oAuthService.OnlyAdmins();
        return oEventoRepository.count();
    }
    public void validate(Long id) {
        if (!oEventoRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }
    public void validate(EventoEntity oEventoEntity) {
        ValidationHelper.validateStringLength(oEventoEntity.getNombre(), 2, 50, "campo nombre de Evento (el campo debe tener longitud de 2 a 50 caracteres)");
        ValidationHelper.validateDate(oEventoEntity.getFini(), LocalDateTime.of(1990, 01, 01, 00, 00, 00), LocalDateTime.of(2025, 01, 01, 00, 00, 00), "campo fecha de factura");
        ValidationHelper.validateDate(oEventoEntity.getFfin(), LocalDateTime.of(1990, 01, 01, 00, 00, 00), LocalDateTime.of(2025, 01, 01, 00, 00, 00), "campo fecha de factura");
        oTipoEventoService.validate(oEventoEntity.getTipoEvento().getId());
        oTipoEventoService.validate(oEventoEntity.getId());
    }
    public Long create(EventoEntity oNewEventoEntity) {
        //oAuthService.OnlyAdmins();
        validate(oNewEventoEntity);
        oNewEventoEntity.setId(0L);
        return oEventoRepository.save(oNewEventoEntity).getId();
    }

    public Long update(EventoEntity oEventoEntity) {
        validate(oEventoEntity.getId());
        //oAuthService.OnlyAdminsOrOwnUsersData(oUsuarioEntity.getId());
        validate(oEventoEntity);
        oTipoEventoService.validate(oEventoEntity.getTipoEvento().getId());
        //if (oAuthService.isAdmin()) {
        //    return update4Admins(oUsuarioEntity).getId();
        //} else {
            return update4Users(oEventoEntity).getId();
        //}
    }
    @Transactional
    private EventoEntity update4Users(EventoEntity oUpdatedEventoEntity) {
        EventoEntity oEventoEntity = oEventoRepository.findById(oUpdatedEventoEntity.getId()).get();
        //keeping login password token & validado descuento activo tipousuario
        oEventoEntity.setNombre(oUpdatedEventoEntity.getNombre());
        oEventoEntity.setFini(oUpdatedEventoEntity.getFini());
        oEventoEntity.setFfin(oUpdatedEventoEntity.getFfin());
        oEventoEntity.setTipoEvento(oTipoEventoService.get(2L));
        return oEventoRepository.save(oEventoEntity);
    }

    public Long delete(Long id) {
        //oAuthService.OnlyAdmins();
        if (oEventoRepository.existsById(id)) {
            oEventoRepository.deleteById(id);
            if (oEventoRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }
  
    
}
