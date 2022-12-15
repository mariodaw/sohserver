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
@Table(name = "evento")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})


public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nombre;
    private LocalDateTime fini;
    private LocalDateTime ffin;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipoEvento")
    private TipoEventoEntity tipoEvento;

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

    public LocalDateTime getFini() {
        return fini;
    }

    public void setFini(LocalDateTime fini) {
        this.fini = fini;
    }

    public LocalDateTime getFfin() {
        return ffin;
    }

    public void setFfin(LocalDateTime ffin) {
        this.ffin = ffin;
    }

    public TipoEventoEntity getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEventoEntity tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

   

}
