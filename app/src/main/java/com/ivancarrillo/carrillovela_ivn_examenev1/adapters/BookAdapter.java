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

    private List<Book> bookList;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private Context context;

    // Interfaces para los eventos onClick y onLongClick
    // Adaptado del patrón OnItemClickListener de RecyclerView.pdf
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Book book);
    }

    public BookAdapter(List<Book> bookList, Context context, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.bookList = bookList;
        this.context = context;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
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
        holder.assignData(book, clickListener, longClickListener);
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
        public void assignData(final Book book, final OnItemClickListener clickListener, final OnItemLongClickListener longClickListener) {
            tvTitle.setText(book.getNombre());
            tvAuthor.setText(book.getAutor());
            tvStatus.setText(book.getEstado());
            imgCover.setImageResource(book.getImagen());
            ratingBar.setRating(book.getRating());

            // Gestionar icono favorito.
            // Si el libro es favorito muestra el corazón relleno, si no muestra el corazón vacío.
            if (book.getEsFavorito()) {
                imgFavorite.setImageResource(R.drawable.heart);
            } else {
                imgFavorite.setImageResource(R.drawable.heart_empty);
            }

            // Cambiar color del estado
            // Si el estado es "Leído" muestra el color verde.
            // Si es "Leyendo" muestra el color amarillo.
            // Para lo demás muestra el color rojo (es decir cuando es "Pendiente").
            if (book.getEstado().equals("Leído")) {
                tvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.read));
            } else if (book.getEstado().equals("Leyendo")) {
                tvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.reading));
            } else {
                tvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending));
            }

            // Asignar evento Click.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(book);
                }
            });

            // Asignar evento Long Click.
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListener.onItemLongClick(book);
                    return true; // Importante: consumir el evento
                }
            });
        }
    }
}
