
package net.ausiasmarch.sohserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import net.ausiasmarch.sohserver.entity.EquipoEntity;
import net.ausiasmarch.sohserver.service.EquipoService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/equipo")
public class EquipoController {

    @Autowired
    EquipoService oEquipoService;

    @GetMapping("/{id}")
    public ResponseEntity<EquipoEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<EquipoEntity>(oEquipoService.get(id), HttpStatus.OK);
    }
     
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oEquipoService.count(), HttpStatus.OK);
    }
 
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody EquipoEntity oNewEquipoEntity) {
        return new ResponseEntity<Long>(oEquipoService.create(oNewEquipoEntity), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody EquipoEntity oNewEquipoEntity) {
        return new ResponseEntity<Long>(oEquipoService.update(oNewEquipoEntity), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oEquipoService.delete(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<EquipoEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<Page<EquipoEntity>>(oEquipoService.getPage(oPageable, strFilter), HttpStatus.OK);
    }


    @PostMapping("/generate")
    public ResponseEntity<EquipoEntity> generate() {
        return new ResponseEntity<EquipoEntity>(oEquipoService.generate(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(oEquipoService.generateSome(amount), HttpStatus.OK);
    } 

}
