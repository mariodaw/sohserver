package net.ausiasmarch.sohserver.repository;

import net.ausiasmarch.sohserver.entity.EventoEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<EventoEntity, Long>{
    
    Page<EventoEntity> findByTipoeventoId(Long tipoevento, Pageable oPageable);

    Page<EventoEntity> findByNombre(String nombre, Pageable oPageable);

    Page<EventoEntity> findByNombreAndTipoeventoId(String nombre, Long tipoevento, Pageable oPageable);

}
