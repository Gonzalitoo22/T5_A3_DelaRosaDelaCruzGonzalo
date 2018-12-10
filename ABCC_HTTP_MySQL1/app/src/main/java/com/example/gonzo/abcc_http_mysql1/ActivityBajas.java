package com.example.gonzo.abcc_http_mysql1;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controlador.Analizador_JSON;

public class ActivityBajas extends Activity {

    EditText txtNC;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajas);
        txtNC=findViewById(R.id.txtNC);
    }

    public void eliminarAlumnos(View v){
        String nc= txtNC.getText().toString();

        //comprobar conexion de wifi

        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni= cm.getActiveNetworkInfo();//checar los permisos

        //conectar y enviar datos para guardar en MySQL
        if (ni != null && ni.isConnected()){
            new EliminarAlumno().execute(nc);
        }

    }//METODO ELIMINAR ALUMNOS

    class EliminarAlumno extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... args) {
            Map<String,String> mapDatos=new HashMap<String, String>();
            mapDatos.put("nc",args[0]);

            Analizador_JSON analizador_json= new Analizador_JSON();
            //url para forma local
            //si se quiere utilizar  el servidor proxmox se tiene que poner la direccion del servido y el puerto
            //10.0.2.2
            String url ="http://192.168.1.69/Semestre_7/Pruebas_PHP/Nueva_carpeta/Sistema_ABCC_MSQL/web_service_http_android/bajas_alumnos.php";
            String metodo="POST";

            JSONObject resultado = analizador_json.peticionHTTP(url,metodo,mapDatos);
            int r=0;
            try {
                r =resultado.getInt("exito");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (r==1){
                Log.i("Msj resultado", "REGISTRO ELIMINADO");
                //  Toast.makeText(getApplicationContext(),"ALUMNO ELIMINADO ",Toast.LENGTH_LONG).show();

            }else
                Log.i("Msj resultado", "NO ELIMINADO ERROR");


            return null;
        }
    }
}
