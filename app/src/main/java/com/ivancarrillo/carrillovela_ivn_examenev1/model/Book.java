package com.ivancarrillo.carrillovela_ivn_examenev1.model;

import io.realm.RealmObject;

public class Book extends RealmObject {


    private String nombre;
    private String autor;
    private String estado;
    private int rating;
    private int imagen;
    private boolean esFavorito;



    public Book(String nombre, String autor, String estado, Integer rating, int imagen, boolean esFavorito) {
        this.nombre = nombre;
        this.autor = autor;
        this.estado = estado;
        this.rating = rating;
        this.imagen = imagen;
        this.esFavorito = esFavorito;
    }

//    En esta aplicación tenemos una serie de libros que tienen las siguientes
//    características: nombre, autor, estado (Pendiente, Leido, Leyendo), rating,
//    imagen (las imágenes se corresponden con la temática del libro son:
//            fantasía, sci-fi, Young-adult, histórico, terror y misterio) y si es un libro
//    favorito o no. Cuidado al crear el constructor, los atributos deben ir en este
//    orden, es el orden que sigue la creación de objetos de la función
//    getSampleBooks.

}
