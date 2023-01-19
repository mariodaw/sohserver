package net.ausiasmarch.sohserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.sohserver.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Page<UsuarioEntity> findByTipousuarioId(Long tipousuario, Pageable oPageable);

    Page<UsuarioEntity> findByNombre(String nombre, Pageable oPageable);

    Page<UsuarioEntity> findByNombreAndTipousuarioId(String nombre, Long tipousuario, Pageable oPageable);
}