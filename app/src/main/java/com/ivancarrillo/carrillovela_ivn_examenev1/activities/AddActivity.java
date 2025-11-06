package com.ivancarrillo.carrillovela_ivn_examenev1.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.ivancarrillo.carrillovela_ivn_examenev1.R;
import com.ivancarrillo.carrillovela_ivn_examenev1.model.Book;

import io.realm.Realm;

public class AddActivity extends AppCompatActivity {

    private Realm realm; // Instancia de Realm para gestionar la BBDD.
    private TextInputEditText etTitle, etAuthor; // Instancias de TextInputEditText para los campos de texto.
    private Spinner spStatus; // Instancia de Spinner para el estado del libro.
    private Slider sliderRating; // Instancia de Slider para el rating del libro.
    private MaterialSwitch switchFavorite; // Instancia de MaterialSwitch para el favorito del libro.
    private MaterialButtonToggleGroup groupCovers; // Instancia de MaterialButtonToggleGroup para los géneros.
    private Button btnCancel, btnSave; // Instancias de Button para los botones.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        realm = Realm.getDefaultInstance(); // Iniciamos la conexión con la BBDD.

        // Recogemos las vistas y las guardamos en su variable correspondiente.
        // Casteamos como buena práctica aun que no sería necesario.
        etTitle = (TextInputEditText) findViewById(R.id.etTitle);
        etAuthor = (TextInputEditText) findViewById(R.id.etAuthor);
        spStatus = (Spinner) findViewById(R.id.spStatus);
        sliderRating = (Slider) findViewById(R.id.sliderRating);
        switchFavorite = (MaterialSwitch) findViewById(R.id.switchFavorite);
        groupCovers = (MaterialButtonToggleGroup) findViewById(R.id.groupCovers);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave = (Button) findViewById(R.id.btnSave);

        // Configurar botones.
        // Botón cancelar.
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando hace click volvemos sin guardar ya que queremos que se cancele.
                finish();
            }
        });

        // Botón guardar.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando hace click llamamos al metodo para guardar el libro.
                saveNewBook();
            }
        });

        // Seleccionamos el primer género por defecto (fantasía).
        groupCovers.check(R.id.btnCoverFantasy);
    }

    private void saveNewBook() {
        // 1. Leer datos de las vistas [cite: 146]
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();

        // 2. Validación de errores (campos obligatorios) [cite: 138]
        if (title.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "Título y Autor son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        String status = spStatus.getSelectedItem().toString();
        // El slider devuelve float, lo convertimos a Integer
        Integer rating = (int) sliderRating.getValue();
        // Si el rating es 0, lo guardamos como null (según la BBDD de ejemplo)
        if (rating == 0) {
            rating = null;
        }

        boolean isFavorite = switchFavorite.isChecked();
        int imageResource = getSelectedImageResource();

        // 3. Guardar en BBDD [cite: 147]
        realm.beginTransaction(); // [cite: 927]
        Book newBook = new Book(title, author, status, rating, imageResource, isFavorite);
        realm.copyToRealm(newBook); // [cite: 929]
        realm.commitTransaction(); // [cite: 930]

        // 4. Volver a MainActivity [cite: 103]
        finish();
    }

    // Metodo helper para obtener el drawable del ToggleGroup.
    private int getSelectedImageResource() {
        int checkedId = groupCovers.getCheckedButtonId();

        if (checkedId == R.id.btnCoverFantasy) {
            return R.drawable.fantasy;
        } else if (checkedId == R.id.btnCoverScifi) {
            return R.drawable.scifi;
        } else if (checkedId == R.id.btnCoverYA) {
            return R.drawable.ya;
        } else if (checkedId == R.id.btnCoverHistory) {
            return R.drawable.history;
        } else if (checkedId == R.id.btnCoverTerror) {
            return R.drawable.terror;
        } else if (checkedId == R.id.btnCoverMistery) {
            return R.drawable.mistery;
        }
        // Devuelve fantasía por defecto si algo falla.
        return R.drawable.fantasy;
    }

    // Metodo para cerrar la conexión con la BBDD y destruir la actividad.
    @Override
    protected void onDestroy() {
        realm.close(); // Cerramos la conexión con la BBDD.
        super.onDestroy(); // Cerramos la actividad.
    }

}
