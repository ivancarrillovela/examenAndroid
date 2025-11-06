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
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private Context context;

    // Interfaces para los eventos onClick y onLongClick [cite: 70, 72]
    // Adaptado del patrón OnItemClickListener de RecyclerView.pdf [cite: 376]
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
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Enlazamos con el item_book.xml [cite: 327]
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        // Llamamos al metodo bind (assignData en el PDF)
        holder.bind(book, clickListener, longClickListener);
    }

    @Override
    public int getItemCount() {
        return bookList.size(); // [cite: 347]
    }

    // Clase ViewHolder [cite: 310]
    public class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCover;
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvStatus;
        ImageView imgFavorite;
        RatingBar ratingBar;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            // Enlazamos vistas del item [cite: 314]
            imgCover = itemView.findViewById(R.id.imgCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

        // Metodo para asignar datos y listeners [cite: 335]
        public void bind(final Book book, final OnItemClickListener clickListener, final OnItemLongClickListener longClickListener) {
            tvTitle.setText(book.getNombre());
            tvAuthor.setText(book.getAutor());
            tvStatus.setText(book.getEstado());
            imgCover.setImageResource(book.getImagen());

            // Gestionar icono favorito
            if (book.getEsFavorito()) {
                imgFavorite.setImageResource(R.drawable.heart);
            } else {
                imgFavorite.setImageResource(R.drawable.heart_empty);
            }

            // Gestionar rating (manejando nulos)
            if (book.getRating() != null) {
                ratingBar.setVisibility(View.VISIBLE);
                ratingBar.setRating(book.getRating());
            } else {
                // Ocultamos el RatingBar si el rating es nulo (como en "Juego de Tronos")
                ratingBar.setVisibility(View.INVISIBLE);
            }

            // Cambiar color del estado
            if (book.getEstado().equals("Leído")) {
                tvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.read));
            } else if (book.getEstado().equals("Leyendo")) {
                tvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.reading));
            } else {
                tvStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending));
            }

            // Asignar evento Click [cite: 401]
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(book);
                }
            });

            // Asignar evento Long Click (adaptación estándar de Android)
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
