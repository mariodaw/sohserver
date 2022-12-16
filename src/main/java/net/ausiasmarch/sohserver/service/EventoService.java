package net.ausiasmarch.sohserver.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.sohserver.entity.EventoEntity;
import net.ausiasmarch.sohserver.exception.ResourceNotFoundException;
import net.ausiasmarch.sohserver.repository.EventoRepository;
import net.ausiasmarch.sohserver.repository.TipoEventoRepository;

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
    
}
