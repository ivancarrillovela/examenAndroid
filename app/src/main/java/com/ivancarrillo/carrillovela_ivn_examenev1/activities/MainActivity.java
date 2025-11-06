package com.ivancarrillo.carrillovela_ivn_examenev1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ivancarrillo.carrillovela_ivn_examenev1.R;
import com.ivancarrillo.carrillovela_ivn_examenev1.adapters.BookAdapter;
import com.ivancarrillo.carrillovela_ivn_examenev1.model.Book;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm; // Instancia de Realm para gestionar la BBDD.
    private RecyclerView recyclerView; // Instancia de RecyclerView para mostrar la lista de libros.
    private BookAdapter adapter; // Instancia de BookAdapter para poder configurar el RecyclerView.
    private RealmResults<Book> bookList; // Lista de libros que se mostrará en el RecyclerView.
    private FloatingActionButton fabAddBook; // Botón flotante para ir a otro activity y añadir un nuevo libro.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance(); // Iniciamos la conexión con la BBDD.

        // Casteamos como buena práctica aun que no sería necesario.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain); // Recogemos el RecyclerView de la vista.
        fabAddBook = (FloatingActionButton) findViewById(R.id.fabAddBook); // Recogemos el FloatingActionButton de la vista.

        // Cargar todos los libros de la BBDD (realm).
        bookList = realm.where(Book.class).findAll();

        // Configurar Adaptador para el RecyclerView
        adapter = new BookAdapter(bookList, this,
                new BookAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Book book) {
                        // Cuando clickamos en un libro cambia su estado
                        // Utilizamos una llamada a un metodo para cambiar el estado del libro (Pendiente, Leyendo, Leído).
                        changeBookStatus(book);
                    }
                },
                new BookAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(Book book) {
                        // Cuando hacemos un click largo en un libro cambia su estado favorito
                        // Utilizamos una llamada a un metodo para cambiar el estado de favorito.
                        toggleBookFavorite(book);
                    }
                });

        // Configurar RecyclerView
        // Le asignamos un LinearLayoutManager al RecyclerView.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Le asignamos nuestro adapter al RecyclerView.
        recyclerView.setAdapter(adapter);

        // 4. Listener para el boton flotante (nos manda a AddActivity)
        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando hace click en el botón flotante nos redirige a AddActivity.
                // Usamos un Intent para la redirección.
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        // Listener de Realm para actualizar los datos de forma automática cuando algo cambia en la BBDD.
        bookList.addChangeListener(new RealmChangeListener<RealmResults<Book>>() {
            @Override
            public void onChange(RealmResults<Book> books) {
                adapter.notifyDataSetChanged(); // Notificamos al adapter que los datos han cambiado.
            }
        });
    }

    // Metodo para cambiar el estado de un libro.
    private void changeBookStatus(Book book) {
        // Iniciamos la transacción.
        realm.beginTransaction();

        // Guardamos el estado actual del libro pasado por parametro en una variable.
        // El estado lo recibimos gracias al getter getEstado de la clase Book.
        String currentStatus = book.getEstado();

        // Cambiamos el estado dependiendo de en cual esté actualmente.
        // Si está en "Pendiente" lo cambiamos a "Leyendo".
        // Si está en "Leyendo" lo cambiamos a "Leído".
        // Si está en "Leído" lo cambiamos a "Pendiente".
        if (currentStatus.equals("Pendiente")) {
            book.setEstado("Leyendo");
        } else if (currentStatus.equals("Leyendo")) {
            book.setEstado("Leído");
        } else { // "Leído"
            book.setEstado("Pendiente");
        }

        // Hacemos el commit de la transacción para actualizar los datos en BBDD.
        realm.commitTransaction();
    }

    // Metodo para cambiar el estado favorito (favorito o no) de un libro.
    private void toggleBookFavorite(Book book) {
        // Iniciamos la transacción.
        realm.beginTransaction();

        // Miramos si el libro es favorito o no gracias al getter getEsFavorito de la clase Book.
        // Asignamos en el setter el valor contrario del que recibimos del getter.
        book.setEsFavorito(!book.getEsFavorito());

        // Hacemos el commit de la transacción para actualizar los datos en BBDD.
        realm.commitTransaction();
    }

    // Metodo para cerrar la conexión con la BBDD y destruir la actividad.
    @Override
    protected void onDestroy() {
        realm.close(); // Cerramos la conexión con la BBDD.
        super.onDestroy(); // Cerramos la actividad.
    }
}