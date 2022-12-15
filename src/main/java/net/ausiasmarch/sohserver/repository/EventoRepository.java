package net.ausiasmarch.sohserver.repository;

import net.ausiasmarch.sohserver.entity.EventoEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventoRepository extends JpaRepository<EventoEntity, Long>{
    
}
