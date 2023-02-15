package net.ausiasmarch.sohserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.sohserver.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    UsuarioEntity findByUsernameAndPassword(String username, String password);

    UsuarioEntity findByUsername(String username);

    Page<UsuarioEntity> findByTipousuarioId(Long tipousuario, Pageable oPageable);

    Page<UsuarioEntity> findByEquipoId(Long equipo, Pageable oPageable);

    Page<UsuarioEntity> findByTipousuarioIdAndEquipoId(Long tipousuario, Long equipo, Pageable oPageable);

    Page<UsuarioEntity> findByNombre(String nombre, Pageable oPageable);

    Page<UsuarioEntity> findByNombreAndTipousuarioId(String nombre, Long tipousuario, Pageable oPageable);

     Page<UsuarioEntity> findByEquipoAndTipousuario(Pageable oPageable, Long equipo, Long tipousuario); 

    Page<UsuarioEntity> findByNombreAndEquipoId(String nombre, Long equipo, Pageable oPageable);

     Page<UsuarioEntity> findByNombreIgnoreCaseContaining(String strFilter , Pageable oPageable); 
}