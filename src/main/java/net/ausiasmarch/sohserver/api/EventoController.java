package net.ausiasmarch.sohserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.sohserver.entity.EventoEntity;
import net.ausiasmarch.sohserver.service.EventoService;

@RestController
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    EventoService oEventoService;

    @GetMapping("/{id}")
    public ResponseEntity<EventoEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(oEventoService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oEventoService.count(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody EventoEntity oNewEventoEntity) {
        return new ResponseEntity<Long>(oEventoService.create(oNewEventoEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oEventoService.delete(id), HttpStatus.OK);
    }
}
