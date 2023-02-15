package net.ausiasmarch.sohserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.sohserver.entity.UsuarioEntity;
import net.ausiasmarch.sohserver.exception.ResourceNotFoundException;
import net.ausiasmarch.sohserver.exception.ResourceNotModifiedException;
import net.ausiasmarch.sohserver.helper.RandomHelper;
import net.ausiasmarch.sohserver.helper.TipoUsuarioHelper;
import net.ausiasmarch.sohserver.helper.ValidationHelper;
import net.ausiasmarch.sohserver.repository.EquipoRepository;
import net.ausiasmarch.sohserver.repository.TipoUsuarioRepository;
import net.ausiasmarch.sohserver.repository.UsuarioRepository;  
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class UsuarioService {

    @Autowired
    TipoUsuarioService oTipoUsuarioService;

     @Autowired
    EquipoService oEquipoService;
 
    @Autowired
    TipoUsuarioRepository oTipoUsuarioRepository;

    @Autowired
    EquipoRepository oEquipoRepository;

    @Autowired
    UsuarioRepository oUsuarioRepository;
    
    @Autowired
    AuthService oAuthService;

    private final String WILDCART_DEFAULT_PASSWORD = "4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64"; //wildcart
    
    //ARRAYS DE PEÑA
    private final List<String> USERNAMES = List.of("Jose", "Jose", "Laura", "Vika", "Sergio",
        "Javi", "Marcos", "Pere", "Daniel", "Jose", "Javi", "Sergio", "Aaron", "Rafa", "Lionel", "Borja");
//necesitas una sola password, una lista de nombres, campeones y skins. El username puede ser igual al nombre y email nombre mas arroba, fnac= helper talayis

        private final List<String> CAMPEONES = List.of("Miss Fortune", "Lux", "Pyke", "Bard", "Sett", "Lee sin", "Dr. Mundo");

        private final List<String> SKINS = List.of("Guardian de las estrellas", "Luna Sangrienta", "Policia", "Navideña", "Fiesta en la piscina");

    public void validate(Long id) {
        if (!oUsuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

     public void validate(UsuarioEntity oUsuarioEntity) { 
     ValidationHelper.validateStringLength(oUsuarioEntity.getUsername(), 3, 15, null);
     }
     
    public UsuarioEntity get(Long id) {
       oAuthService.OnlyAdminsOrOwnUsersData(id);
       try {
           return oUsuarioRepository.findById(id).get();
       } catch (Exception ex) {
           throw new ResourceNotFoundException("id " + id + " not exist");
       }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oUsuarioRepository.count();
    }

    public Long create(UsuarioEntity oNewUsuarioEntity) {
        oAuthService.OnlyAdmins();
        validate(oNewUsuarioEntity);
        oNewUsuarioEntity.setId(0L);
        oNewUsuarioEntity.setPassword(WILDCART_DEFAULT_PASSWORD);
        return oUsuarioRepository.save(oNewUsuarioEntity).getId();
    }

    public Long update(UsuarioEntity oUsuarioEntity) {
        validate(oUsuarioEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(oUsuarioEntity.getId());
        validate(oUsuarioEntity);
        oTipoUsuarioService.validate(oUsuarioEntity.getTipousuario().getId());
        oEquipoService.validate(oUsuarioEntity.getEquipo().getId());
        /* if (oAuthService.isAdmin()) {
            return update4Admins(oUsuarioEntity).getId();
        } else { */
            return update4Users(oUsuarioEntity).getId();
        //}
    }

    @Transactional
    private UsuarioEntity update4Users(UsuarioEntity oUpdatedUsuarioEntity) {
        UsuarioEntity oUsuarioEntity = oUsuarioRepository.findById(oUpdatedUsuarioEntity.getId()).get();
        //keeping login password token & validado descuento activo tipousuario
        oUsuarioEntity.setUsername(oUpdatedUsuarioEntity.getUsername());
        oUsuarioEntity.setCorreo(oUpdatedUsuarioEntity.getCorreo());
        oUsuarioEntity.setNombre(oUpdatedUsuarioEntity.getNombre());
        oUsuarioEntity.setCuenta(oUpdatedUsuarioEntity.getCuenta());
        oUsuarioEntity.setFnac(oUpdatedUsuarioEntity.getFnac());
        oUsuarioEntity.setCampeon(oUpdatedUsuarioEntity.getCampeon());
        oUsuarioEntity.setSkin(oUpdatedUsuarioEntity.getSkin());
        
        oUsuarioEntity.setEquipo(oUpdatedUsuarioEntity.getEquipo());
        oUsuarioEntity.setTipousuario(oUpdatedUsuarioEntity.getTipousuario());
        return oUsuarioRepository.save(oUsuarioEntity);
    }
    public Long delete(Long id) {   
        oAuthService.OnlyAdmins();
        if (oUsuarioRepository.existsById(id)) {
            oUsuarioRepository.deleteById(id);
            if (oUsuarioRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }

    public UsuarioEntity generate() {
        oAuthService.OnlyAdmins();
        return oUsuarioRepository.save(generateRandomUser());
    }

    public Long generateSome(Integer amount) {
        oAuthService.OnlyAdmins();
        List<UsuarioEntity> userList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            UsuarioEntity oUsuarioEntity = generateRandomUser();
            oUsuarioRepository.save(oUsuarioEntity);
            userList.add(oUsuarioEntity);
        }
        return oUsuarioRepository.count();
    }

     private UsuarioEntity generateRandomUser() {
        UsuarioEntity oUserEntity = new UsuarioEntity();
        String nombre = USERNAMES.get(RandomHelper.getRandomInt(0, USERNAMES.size() - 1));
        String campeon = CAMPEONES.get(RandomHelper.getRandomInt(0, CAMPEONES.size() - 1));
        oUserEntity.setUsername(nombre);
        oUserEntity.setNombre(nombre);
        oUserEntity.setCuenta(nombre + campeon);
        oUserEntity.setPassword(WILDCART_DEFAULT_PASSWORD);
        oUserEntity.setCorreo(nombre + "@gmail.com");
        oUserEntity.setFnac(RandomHelper.getRadomDate());
        oUserEntity.setCampeon(campeon);
        oUserEntity.setSkin(SKINS.get(RandomHelper.getRandomInt(0, SKINS.size() - 1)));
        if (RandomHelper.getRandomInt(0, 10) > 1) {
            oUserEntity.setTipousuario(oTipoUsuarioRepository.getById(TipoUsuarioHelper.MIEMBRO));
        } else {
            oUserEntity.setTipousuario(oTipoUsuarioRepository.getById(TipoUsuarioHelper.ADMIN));
        }
        oUserEntity.setEquipo(oEquipoService.getOneRandom());
        return oUserEntity;
    } 

/*     public Page<UsuarioEntity> getPage(Pageable oPageable, String strFilter, Long lTipoUsuario, Long lEquipo) {
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if(lTipoUsuario == null){
            if(lEquipo == null){
                return oUsuarioRepository.findAll(oPageable);
            } else {
                return oUsuarioRepository.findByEquipoId(lEquipo, oPageable);
            }
        } else {
            if (lTipoUsuario != null){
                if (lEquipo != null){
                    return oUsuarioRepository.findByTipousuarioIdAndEquipoId(lTipoUsuario, lEquipo, oPageable);
                }
            }
            return oUsuarioRepository.findByTipousuarioId(lTipoUsuario, oPageable);
        } else {
            if(lTipoUsuario || )
            return oUsuarioRepository.findByNombre(strFilter, oPageable);
        }

    } */
     public Page<UsuarioEntity> getPage(Pageable oPageable, String strFilter, Long id_tipoUsuario, Long id_usuario, Long id_equipo){
        //oAuthService.OnlyAdmins();

        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<UsuarioEntity> oPage = null;

        if (id_usuario == null && id_tipoUsuario == null && id_equipo == null) {
            if (strFilter == null  || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oUsuarioRepository.findAll(oPageable);
                System.out.println(strFilter);
            } else {
                oPage = oUsuarioRepository.findByNombreIgnoreCaseContaining(strFilter, oPageable);
            }
        } else if (id_usuario == null && id_equipo == null) {
            oPage = oUsuarioRepository.findByTipousuarioId(id_tipoUsuario, oPageable);
        } else if (id_tipoUsuario == null && id_usuario == null) {
            oPage = oUsuarioRepository.findByEquipoId(id_equipo, oPageable);
        } else if (id_usuario == null) {
            oPage = oUsuarioRepository.findByEquipoAndTipousuario(oPageable, id_equipo, id_tipoUsuario);
        } else {
            oPage = oUsuarioRepository.findByEquipoAndTipousuario(oPageable, id_equipo, id_tipoUsuario);
        }

        return  oPage ;
    } 
}   