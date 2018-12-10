package com.example.gonzo.abcc_http_mysql1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnAgregar, btnEliminar, btnBuscar, btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnEliminar = (Button) findViewById(R.id.btnActualizar);
        btnModificar = (Button) findViewById(R.id.btnModificar);
    }

    public void abrirActivities(View v){

        Intent i;
        switch (v.getId()){
            case R.id.btnAgregar:
                i = new Intent(MainActivity.this, ActivityAltas.class);
                startActivity(i);
            break;
            case R.id.btnBuscar:
                i = new Intent(MainActivity.this, ActivityConsultas.class);
                startActivity(i);
            break;
            case R.id.btnActualizar:
                i = new Intent(MainActivity.this, ActivityBajas.class);
                startActivity(i);
            break;
            case R.id.btnModificar:
                i = new Intent(MainActivity.this, ActivityCambios.class);
                startActivity(i);
            break;
        }

    }
}
