package com.ivancarrillo.carrillovela_ivn_examenev1.app;

import com.ivancarrillo.carrillovela_ivn_examenev1.R;
import com.ivancarrillo.carrillovela_ivn_examenev1.model.Book;

import java.util.ArrayList;
import java.util.Collections;

public class Utils {

    public static ArrayList<Book> getSampleBooks() {
        ArrayList<Book> list = new ArrayList<>();

        // Fantasía
        list.add(new Book("El nombre del viento", "Patrick Rothfuss", "Leído", 5, R.drawable.fantasy, true));
        list.add(new Book("La comunidad del anillo", "J.R.R. Tolkien", "Leyendo", 4, R.drawable.fantasy, false));
        list.add(new Book("Juego de tronos", "George R.R. Martin", "Pendiente", null, R.drawable.fantasy, false));

        // Ciencia ficción
        list.add(new Book("Dune", "Frank Herbert", "Leído", 5, R.drawable.scifi, true));
        list.add(new Book("Neuromante", "William Gibson", "Leyendo", 4, R.drawable.scifi, false));
        list.add(new Book("Fundación", "Isaac Asimov", "Pendiente", null, R.drawable.scifi, false));

        // Young Adult
        list.add(new Book("Los juegos del hambre", "Suzanne Collins", "Leído", 5, R.drawable.ya, true));
        list.add(new Book("Bajo la misma estrella", "John Green", "Leyendo", 4, R.drawable.ya, true));
        list.add(new Book("Divergente", "Veronica Roth", "Pendiente", null, R.drawable.ya, false));

        //  Histórica
        list.add(new Book("Los pilares de la Tierra", "Ken Follett", "Leído", 5, R.drawable.history, true));
        list.add(new Book("La catedral del mar", "Ildefonso Falcones", "Leyendo", 4, R.drawable.history, false));
        list.add(new Book("El médico", "Noah Gordon", "Pendiente", null, R.drawable.history, false));

        // Terror
        list.add(new Book("It", "Stephen King", "Leído", 5, R.drawable.terror, true));
        list.add(new Book("Drácula", "Bram Stoker", "Leyendo", 4, R.drawable.terror, false));
        list.add(new Book("El resplandor", "Stephen King", "Pendiente", null, R.drawable.terror, false));

        //  Misterio
        list.add(new Book("Asesinato en el Orient Express", "Agatha Christie", "Leído", 5, R.drawable.mistery, true));
        list.add(new Book("El código Da Vinci", "Dan Brown", "Leyendo", 4, R.drawable.mistery, true));
        list.add(new Book("La chica del tren", "Paula Hawkins", "Pendiente", null, R.drawable.mistery, false));

        Collections.shuffle(list);
        return list;
    }
}