package net.ausiasmarch.sohserver.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.sohserver.helper.RandomHelper;
import net.ausiasmarch.sohserver.helper.TipoEventoHelper;
import net.ausiasmarch.sohserver.helper.ValidationHelper;
import net.ausiasmarch.sohserver.entity.EventoEntity;
import net.ausiasmarch.sohserver.exception.ResourceNotFoundException;
import net.ausiasmarch.sohserver.exception.ResourceNotModifiedException;
import net.ausiasmarch.sohserver.repository.EventoRepository;
import net.ausiasmarch.sohserver.repository.TipoEventoRepository;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class EventoService {

    @Autowired
    TipoEventoService oTipoEventoService;
    @Autowired
    TipoEventoRepository oTipoEventoRepository;
    @Autowired
    EventoRepository oEventoRepository;


    private final List<String> NOMBRES = List.of("Jornada 1", "Jornada 2","Jornada 3","Jornada 4","Jornada 5","Amistoso 1",
    "Amistoso 2","Amistoso 3","Amistoso 4","Amistoso 5");
    private final List<String> HORAS = List.of("17:30","18:30","19:30","20:30","21:30","22:30","23:30","00:30");
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
        oTipoEventoService.validate(oEventoEntity.getTipoevento().getId());
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
        oTipoEventoService.validate(oEventoEntity.getTipoevento().getId());
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
        oEventoEntity.setHora(oUpdatedEventoEntity.getHora());
        oEventoEntity.setTipoevento(oUpdatedEventoEntity.getTipoevento());
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

    public EventoEntity generate() {
        //oAuthService.OnlyAdmins();
        return oEventoRepository.save(generateRandomEvent());
    }

    public Long generateSome(Integer amount) {
       // oAuthService.OnlyAdmins();
        List<EventoEntity> userList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            EventoEntity oEventoEntity = generateRandomEvent();
            oEventoRepository.save(oEventoEntity);
            userList.add(oEventoEntity);
        }
        return oEventoRepository.count();
    }

    private EventoEntity generateRandomEvent(){
        EventoEntity oEventoEntity = new EventoEntity();
        oEventoEntity.setNombre(NOMBRES.get(RandomHelper.getRandomInt(0, NOMBRES.size() - 1)));
        oEventoEntity.setFini(RandomHelper.getRadomDate());
        oEventoEntity.setHora(HORAS.get(RandomHelper.getRandomInt(0, HORAS.size() - 1)));
        if (RandomHelper.getRandomInt(0, 10) > 1) {
            oEventoEntity.setTipoevento(oTipoEventoRepository.getById(TipoEventoHelper.AMISTOSO));
        } else {
            oEventoEntity.setTipoevento(oTipoEventoRepository.getById(TipoEventoHelper.JORNADA));
        }
        return oEventoEntity;
    }

  

     public Page<EventoEntity> getPage(Pageable oPageable, String strFilter, Long lTipoevento) {
        //oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<EventoEntity> oPage = null;
        if (lTipoevento == null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oEventoRepository.findAll(oPageable);
            } else {
                oPage = oEventoRepository.findByNombre(strFilter, oPageable);
            }
        } else {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oEventoRepository.findByTipoeventoId(lTipoevento, oPageable);
            } else {
                oPage = oEventoRepository.findByNombreAndTipoeventoId(strFilter, lTipoevento, oPageable);
            }
        }
        return oPage; 
    }
    
}
