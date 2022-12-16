package net.ausiasmarch.sohserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.ausiasmarch.sohserver.service.TipoEventoService;

@RestController
@RequestMapping("/TipoEvento")
public class TipoEventoController {
    @Autowired
    TipoEventoService oTipoEventoService;

}
