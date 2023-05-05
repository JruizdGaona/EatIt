package com.example.eatit.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.example.eatit.fragments.recetas.FragmentCrearReceta;
import com.example.eatit.fragments.recetas.FragmentRecetas;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.Serializable;
import java.net.URI;

public class ActivityRecetas extends AppCompatActivity implements Serializable {

    private AppCompatImageView imagenRetroceso;
    private TextView nombreReceta;
    private ShapeableImageView imagenReceta;
    private Receta receta;
    private boolean crear = false;
    private String email;
    private Uri uri;
    private static final int REQUEST_CAMERA_CODE = 1;
    private static final int REQUEST_STORAGE_CODE = 2;
    private static final int PICK_CAMERA_CODE = 3;
    private static final int PICK_GALLERY_CODE = 4;
    private final String[] cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final String[] storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_receta);

        inicializarVariables();
        cargarFragmentInicio();
        cerrarActivity();
        addImagenReceta();
    }

    private void inicializarVariables() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        receta = (Receta) intent.getSerializableExtra("receta");
        imagenRetroceso = findViewById(R.id.img_back);
        nombreReceta = findViewById(R.id.recetas);
        imagenReceta = findViewById(R.id.img_receta);
        if (receta != null) {
            nombreReceta.setText(receta.getNombre());
        } else crear = true;
    }

    private void cargarFragmentInicio() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (crear) {
            receta = new Receta();
            fragmentTransaction.replace(R.id.frame_info, new FragmentCrearReceta(email, receta)).commit();
        } else {
            fragmentTransaction.replace(R.id.frame_info, new FragmentRecetas(receta, email)).commit();
        }
    }

    private void addImagenReceta() {
        imagenReceta.setOnClickListener((View) -> {
            mostrarOpcionesImagen();
            Toast.makeText(this, "Seleccionar Imagen", Toast.LENGTH_SHORT).show();
        });
    }

    private void mostrarOpcionesImagen() {
        String[] opciones = {"Cámara", "Galería"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir Imagen");

        // Le añadimos el OnClick a las opciones y comprobamos permisos.
        builder.setItems(opciones, (dialog, which) -> {
            if(which==0) {
                if(!comprobarPermisosCamara()) pedirPermisosCamara();
                else irCamara();
            } else if(which==1) {
                if(!comprobarPermisosAlmacenamiento()) pedirPermisosAlmacenamiento();
                else irGaleria();
            }
        });
        builder.create().show();
    }

    private boolean comprobarPermisosCamara() {
        boolean permisosCamara, permisosAlmacenamiento;
        permisosCamara = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        permisosAlmacenamiento = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return permisosCamara && permisosAlmacenamiento;
    }

    private boolean comprobarPermisosAlmacenamiento() {
        boolean permisos;
        permisos = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return permisos;
    }

    /**
     * Método que le pide los permisos de cámara al usuario.
     */
    private void pedirPermisosCamara() {
        ActivityCompat.requestPermissions(this, cameraPermissions, REQUEST_CAMERA_CODE);
    }

    /**
     * Método que le pide los permisos de almacenamiento al usuario.
     */
    private void pedirPermisosAlmacenamiento() {
        ActivityCompat.requestPermissions(this, storagePermissions, REQUEST_STORAGE_CODE);
    }

    private void irCamara() {
        ContentValues valores = new ContentValues();
        valores.put(MediaStore.Images.Media.TITLE, "Título de la imagen");
        valores.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");

        // En la uri correspondiente a la imagen escogida insertamos los valores de título y descripción
        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, valores);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, PICK_CAMERA_CODE);
    }

    /**
     * Método que lleva al usuario a la Galería.
     */
    private void irGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_GALLERY_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CAMERA_CODE){
            // Si el usuario permite los permisos, vamos a la cámara.
            if(permissions.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                irCamara();
            }
            // Si no, le mostramos un mensaje para que habilite los permisos.
            else{
                Toast.makeText(this, "Es necesario que habilites los permisos", Toast.LENGTH_SHORT).show();
            }

        }
        if(requestCode == REQUEST_STORAGE_CODE){
            // Si el usuario permite esos permisos, vamos a la galería.
            if(permissions.length>0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                irGaleria();
            }
            // Si no, le mostramos un mensaje para que habilite los permisos.
            else{
                Toast.makeText(this, "Es necesario que habilites los permisos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Método que nos añade en el CircleImage la imagen que ha seleccionado el usuario.
     * @param requestCode - Código que mandamos al método, indica si la opción seleccionada es la cámara o la galería.
     * @param resultCode - Código que devuelve el resultado del proceso.
     * @param data - Datos de la imagen que recibimos.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Si el usuario no tiene imagen asociada, la ponemos desde cámara o galeería, dependiendo de lo que haya seleccionado el usuario.
        if (receta.getUri() == null) {
            if(resultCode == Activity.RESULT_OK) {
                if (requestCode == PICK_CAMERA_CODE) {
                    receta.setUri(uri);
                    imagenReceta.setImageURI(uri);
                } else if (requestCode == PICK_GALLERY_CODE) {
                    Uri path = data.getData();
                    receta.setUri(path);
                    imagenReceta.setImageURI(path);
                }
                Toast.makeText(this, "Imagen actualizada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al cambiar la imagen", Toast.LENGTH_SHORT).show();
            }
            // Si ya tiene una imagen asociada, la ponemos.
        } else {
            receta.setUri(receta.getUri());
            imagenReceta.setImageURI(uri);
        }
    }

    private void cerrarActivity() {
        imagenRetroceso.setOnClickListener((view) -> this.finish());
    }
}
