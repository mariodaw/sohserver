package net.ausiasmarch.sohserver.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.sohserver.entity.TipoEventoEntity;
import net.ausiasmarch.sohserver.exception.ResourceNotFoundException;
import net.ausiasmarch.sohserver.repository.TipoEventoRepository;

@Service
public class TipoEventoService {
    
    @Autowired
    TipoEventoRepository oTipoEventoRepository;

 
    public TipoEventoEntity get(Long id) {
        //oAuthService.OnlyAdmins();
        return oTipoEventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento with id: " + id + " not found"));
    }

}
