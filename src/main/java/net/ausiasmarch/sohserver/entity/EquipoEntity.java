package net.ausiasmarch.sohserver.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name = "equipo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EquipoEntity {
    @Id
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY)
    private final List<UsuarioEntity> usuarios;

    public EquipoEntity(){
        this.usuarios= new ArrayList<>();
    }

    public EquipoEntity(Long id){
        this.usuarios = new ArrayList<>();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}