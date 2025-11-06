package com.ivancarrillo.carrillovela_ivn_examenev1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ivancarrillo.carrillovela_ivn_examenev1.R;
import com.ivancarrillo.carrillovela_ivn_examenev1.model.Book;

import java.util.List;
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    private List<Book> bookList; // Lista para los libros.
    private OnBookItemListener listener; // Listener único para los eventos onClick y onLongClick

    public BookAdapter(List<Book> bookList, OnBookItemListener listener) {
        this.bookList = bookList; // Asignamos la lista de libros.
        this.listener = listener; // Asignamos el listener único.
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Enlazamos con el item_book.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book book = bookList.get(position);
        // Llamamos al metodo assignData.
        holder.assignData(book, listener);
    }

    @Override
    public int getItemCount() {
        return bookList.size(); // Devolvemos el tamaño de la lista.
    }

    // Clase Holder para el RecyclerView.
    public class BookHolder extends RecyclerView.ViewHolder {
        // Instanciamos las vistas.
        ImageView imgCover;
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvStatus;
        ImageView imgFavorite;
        RatingBar ratingBar;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            // Enlazamos vistas del item con el contructor.
            imgCover = itemView.findViewById(R.id.imgCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

        // Metodo para asignar datos y listeners.
        public void assignData(final Book book, final OnBookItemListener listener) {
            // Asignamos los datos cogiendolos del objeto book que recibimos.
            tvTitle.setText(book.getNombre());
            tvAuthor.setText(book.getAutor());
            tvStatus.setText(book.getEstado());
            imgCover.setImageResource(book.getImagen());
            ratingBar.setRating(book.getRating());

            // Necesitamos obtener el contexto de la vista actual para poder buscar
            // en los resources los colores ya que solo nos devuelven un identidicador y no el color en si.
            Context context = itemView.getContext();

            // Gestionar icono favorito.
            // Si el libro es favorito muestra el corazón relleno, si no muestra el corazón vacío.
            if (book.getEsFavorito()) {
                imgFavorite.setImageResource(R.drawable.heart);
            } else {
                imgFavorite.setImageResource(R.drawable.heart_empty);
            }

            // Cambiar color del estado utilizando context para poder
            // acceder a los recursos con el id que nodevuelve el color.
            // Si el estado es "Leído" muestra el color verde.
            // Si es "Leyendo" muestra el color amarillo.
            // Para lo demás muestra el color rojo (es decir cuando es "Pendiente").
            if (book.getEstado().equals("Leído")) {
                tvStatus.setBackgroundColor(ContextCompat.getColor(context,R.color.read));
            } else if (book.getEstado().equals("Leyendo")) {
                tvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.reading));
            } else {
                tvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending));
            }

            // Asignar evento Click usando el listener único.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(book);
                }
            });

            // Asignar evento Long Click usando el listener único.
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(book);
                    return true; // Indica que el evento ya se ha consumido para evitar fallos.
                }
            });
        }
    }

    // Interfaz para Click y LongClick
    public interface OnBookItemListener {
        void onItemClick(Book book);
        void onItemLongClick(Book book);
    }
}
