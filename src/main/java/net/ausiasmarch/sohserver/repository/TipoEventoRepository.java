package net.ausiasmarch.sohserver.repository;

import net.ausiasmarch.sohserver.entity.TipoEventoEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TipoEventoRepository extends JpaRepository<TipoEventoEntity, Long>{
 
    public Page<TipoEventoEntity> findByNombreIgnoreCaseContaining(String strFilter, Pageable oPageable);
}