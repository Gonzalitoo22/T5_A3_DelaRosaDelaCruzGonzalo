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

public class ActivityCambios extends Activity {
    EditText txtNC,txtN, txtPA, txtSA, txtE, txtS, txtC;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios);
        txtNC=findViewById(R.id.txtNC);
        txtN=findViewById(R.id.txtN);
        txtPA=findViewById(R.id.txtPA);
        txtSA=findViewById(R.id.txtSA);
        txtE =findViewById(R.id.txtE);
        txtS=findViewById(R.id.txtS);
        txtC=findViewById(R.id.txtC);
    }

    public void modificarAlumnos(View v){
        String nc = txtNC.getText().toString();
        String n = txtN.getText().toString();
        String pa = txtPA.getText().toString();
        String sa = txtSA.getText().toString();
        String e = txtE.getText().toString();
        String s = txtS.getText().toString();
        String c = txtC.getText().toString();

        //comprobar conexion de wifi

        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni= cm.getActiveNetworkInfo();//checar los permisos

        //conectar y enviar datos para guardar en MySQL
        if (ni != null && ni.isConnected()){
            new ModificarAlumno().execute(nc,n,pa,sa,e,s,c);
        }




    }//METODO AGREGAR ALUMNOS


    ///CLASE INTERNA PARA REALIZAR EL ENVIO DE DATOS EN OTRO HILO DE EJECUCION

    class ModificarAlumno extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... args) {
            Map<String,String> mapDatos=new HashMap<String, String>();
            mapDatos.put("nc",args[0]);
            mapDatos.put("n",args[1]);
            mapDatos.put("pa",args[2]);
            mapDatos.put("sa",args[3]);
            mapDatos.put("e",args[4]);
            mapDatos.put("s",args[5]);
            mapDatos.put("c",args[6]);

            Analizador_JSON analizador_json= new Analizador_JSON();
            //url para forma local
            //si se quiere utilizar  el servidor proxmox se tiene que poner la direccion del servido y el puerto
            //10.0.2.2
            String url ="http://192.168.1.69/Semestre_7/Pruebas_PHP/Nueva_carpeta/Sistema_ABCC_MSQL/web_service_http_android/cambios_alumnos.php";
            String metodo="POST";

            JSONObject resultado = analizador_json.peticionHTTP(url,metodo,mapDatos);
            int r=0;
            try {
                r =resultado.getInt("exito");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (r==1){
                Log.i("Msj resultado", "REGISTRO MODIFICADO");
                //  Toast.makeText(getApplicationContext(),"ALUMNO AGREGADO ",Toast.LENGTH_LONG).show();

            }else
                Log.i("Msj resultado", "NO MODIFICADO ERROR");


            return null;
        }
    }
}
