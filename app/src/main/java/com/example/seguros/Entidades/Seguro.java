package com.example.seguros.Entidades;

import java.io.Serializable;

public class Seguro implements Serializable {

    public Integer id_seguro;
    public String tipo_seguro;
    public String cobertura;
    public Integer precio;
    public Integer activo;

    public Seguro() {
    }

    public Seguro(Integer id_seguro, String tipo_seguro, String cobertura, Integer precio, Integer activo) {
        this.id_seguro = id_seguro;
        this.tipo_seguro = tipo_seguro;
        this.cobertura = cobertura;
        this.precio = precio;
        this.activo = activo;
    }

    public Integer getId_seguro() {
        return id_seguro;
    }

    public String getTipo_seguro() {
        return tipo_seguro;
    }

    public String getCobertura() {
        return cobertura;
    }

    public Integer getPrecio() {
        return precio;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setId_seguro(Integer id_seguro) {
        this.id_seguro = id_seguro;
    }

    public void setTipo_seguro(String tipo_seguro) {
        this.tipo_seguro = tipo_seguro;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}
