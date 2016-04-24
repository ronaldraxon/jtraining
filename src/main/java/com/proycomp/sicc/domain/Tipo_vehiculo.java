package com.proycomp.sicc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tipo_vehiculo.
 */
@Entity
@Table(name = "tipo_vehiculo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tipo_vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descripcion_uso")
    private String descripcion_uso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion_uso() {
        return descripcion_uso;
    }

    public void setDescripcion_uso(String descripcion_uso) {
        this.descripcion_uso = descripcion_uso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tipo_vehiculo tipo_vehiculo = (Tipo_vehiculo) o;
        if(tipo_vehiculo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipo_vehiculo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tipo_vehiculo{" +
            "id=" + id +
            ", tipo='" + tipo + "'" +
            ", descripcion_uso='" + descripcion_uso + "'" +
            '}';
    }
}
