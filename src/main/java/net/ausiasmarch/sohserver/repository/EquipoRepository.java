package net.ausiasmarch.sohserver.repository;

import net.ausiasmarch.sohserver.entity.EquipoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipoRepository extends JpaRepository<EquipoEntity, Long> {

     public Page<EquipoEntity> findByNombreIgnoreCaseContaining(String strFilter, Pageable oPageable); 
     
     Page<EquipoEntity> findByNombre(String nombre, Pageable oPageable);

}