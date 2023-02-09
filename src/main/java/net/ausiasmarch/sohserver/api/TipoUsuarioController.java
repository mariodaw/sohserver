package net.ausiasmarch.sohserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import net.ausiasmarch.sohserver.entity.TipoUsuarioEntity;
import net.ausiasmarch.sohserver.service.TipoUsuarioService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
@RequestMapping("/tipousuario")
public class TipoUsuarioController {

    @Autowired
    TipoUsuarioService oTipoUsuarioService;


     @GetMapping("/{id}")
    public ResponseEntity<TipoUsuarioEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<TipoUsuarioEntity>(oTipoUsuarioService.get(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TipoUsuarioEntity>> all() {
        return new ResponseEntity<List<TipoUsuarioEntity>>(oTipoUsuarioService.all(), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTipoUsuarioService.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<TipoUsuarioEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<Page<TipoUsuarioEntity>>(oTipoUsuarioService.getPage(oPageable, strFilter), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody TipoUsuarioEntity oTipoUsuarioEntity) {
        return new ResponseEntity<Long>(oTipoUsuarioService.update(oTipoUsuarioEntity), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<Long> generate() {
        return new ResponseEntity<Long>(oTipoUsuarioService.generate(), HttpStatus.OK); 
    }
}
