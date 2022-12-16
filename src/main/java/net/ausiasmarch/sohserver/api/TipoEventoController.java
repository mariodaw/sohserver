package net.ausiasmarch.sohserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.sohserver.entity.TipoEventoEntity;
import net.ausiasmarch.sohserver.service.TipoEventoService;

@RestController
@RequestMapping("/tipoevento")
public class TipoEventoController {
    @Autowired
    TipoEventoService oTipoEventoService;


    @GetMapping("/{id}")
    public ResponseEntity<TipoEventoEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(oTipoEventoService.get(id), HttpStatus.OK);
    }
}
