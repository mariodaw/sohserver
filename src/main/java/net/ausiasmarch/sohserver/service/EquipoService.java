package net.ausiasmarch.sohserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.sohserver.entity.EquipoEntity;
import net.ausiasmarch.sohserver.exception.ResourceNotFoundException;
import net.ausiasmarch.sohserver.exception.ResourceNotModifiedException;
import net.ausiasmarch.sohserver.helper.RandomHelper;
import net.ausiasmarch.sohserver.helper.ValidationHelper;
import net.ausiasmarch.sohserver.repository.EquipoRepository;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class EquipoService {

    @Autowired
    EquipoRepository oEquipoRepository;
    @Autowired
    AuthService oAuthService;

    private final List<String> NOMBRES = List.of("Void Busters", "Pizza con Piña", "G2", "Barcelona",
            "Albacete Club Sports");

    public void validate(Long id) {
        if (!oEquipoRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(EquipoEntity oEquipoEntity) {
        ValidationHelper.validateStringLength(oEquipoEntity.getNombre(), 2, 100,
                "campo nombre de Tipoevento (el campo debe tener longitud de 2 a 100 caracteres)");
    }

    public EquipoEntity get(Long id) {
         oAuthService.OnlyAdmins();
        return oEquipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento with id: " + id + " not found"));
    }

    public Long count() {
         oAuthService.OnlyAdmins();
        return oEquipoRepository.count();
    }

    public Long create(EquipoEntity oNewEquipoEntity) {
         oAuthService.OnlyAdmins();
        validate(oNewEquipoEntity);
        oNewEquipoEntity.setId(0L);
        return oEquipoRepository.save(oNewEquipoEntity).getId();
    }

    public Long update(EquipoEntity oEquipoEntity) {
        validate(oEquipoEntity.getId());
         oAuthService.OnlyAdminsOrOwnUsersData(oEquipoEntity.getId());
        validate(oEquipoEntity);
        // oEquipoService.validate(oEquipoEntity.getEquipo().getId());
        /*
         * if (oAuthService.isAdmin()) {
         * return update4Admins(oEquipoEntity).getId();
         * } else {
         */
        return update4Users(oEquipoEntity).getId();
        // }
    }

    @Transactional
    private EquipoEntity update4Users(EquipoEntity oUpdatedEquipoEntity) {
        EquipoEntity oEquipoEntity = oEquipoRepository.findById(oUpdatedEquipoEntity.getId()).get();
        // keeping login password token & validado descuento activo tipoEquipo
        oEquipoEntity.setNombre(oUpdatedEquipoEntity.getNombre());
        // oEquipoEntity.setEquipo (oUpdatedEquipoEntity.getEquipo());
        return oEquipoRepository.save(oEquipoEntity);
    }

    public Long delete(Long id) {
         oAuthService.OnlyAdmins();
        if (oEquipoRepository.existsById(id)) {
            oEquipoRepository.deleteById(id);
            if (oEquipoRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }

    public EquipoEntity generate() {
         oAuthService.OnlyAdmins();
        return oEquipoRepository.save(generateRandomUser());
    }

    public Long generateSome(Integer amount) {
         oAuthService.OnlyAdmins();
        List<EquipoEntity> userList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            userList.add(generateRandomUser());
        }
        oEquipoRepository.saveAll(userList);
        return oEquipoRepository.count();
    }

    private EquipoEntity generateRandomUser() {
        EquipoEntity oEquipoEntity = new EquipoEntity();
        oEquipoEntity.setId(0L);
        oEquipoEntity.setNombre(NOMBRES.get(RandomHelper.getRandomInt(0, NOMBRES.size() - 1)));
        return oEquipoEntity;
    }

    public Page<EquipoEntity> getPage(Pageable oPageable, String strFilter) {
         oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<EquipoEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = oEquipoRepository.findAll(oPageable);
        } else {
            oPage = oEquipoRepository.findByNombre(strFilter, oPageable);
        }

        return oPage;
    }

    public EquipoEntity getOneRandom() {
        EquipoEntity oEquipoEntity = null;
        int iPosicion = RandomHelper.getRandomInt(0, (int) oEquipoRepository.count() - 1);
        Pageable oPageable = PageRequest.of(iPosicion, 1);
        Page<EquipoEntity> tipoEquipoPage = oEquipoRepository.findAll(oPageable);
        List<EquipoEntity> tipoEquipoList = tipoEquipoPage.getContent();
        oEquipoEntity = oEquipoRepository.getById(tipoEquipoList.get(0).getId());
        return oEquipoEntity;
    }

}
