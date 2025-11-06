package com.ivancarrillo.carrillovela_ivn_examenev1.model;

import com.ivancarrillo.carrillovela_ivn_examenev1.app.MyApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Book extends RealmObject {

    @PrimaryKey //
    private int id;
    private String nombre;
    private String autor;
    private String estado;
    private Integer rating; // Usamos Integer (objeto) para permitir nulos
    private int imagen;
    private boolean esFavorito;

    // Constructor vacío requerido por Realm
    public Book() {
    }

    public Book(String nombre, String autor, String estado, Integer rating, int imagen, boolean esFavorito) {
        this.id = MyApp.bookId.incrementAndGet(); //
        this.nombre = nombre;
        this.autor = autor;
        this.estado = estado;
        this.rating = rating;
        this.imagen = imagen;
        this.esFavorito = esFavorito;
    }

    // --- Getters y Setters (Requeridos por Realm) ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public boolean getEsFavorito() {
        return esFavorito;
    }

    public void setEsFavorito(boolean esFavorito) {
        this.esFavorito = esFavorito;
    }
}