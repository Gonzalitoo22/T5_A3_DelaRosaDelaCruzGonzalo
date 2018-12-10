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

public class ActivityAltas extends Activity {
	EditText cajaNumControl,cajaNombre,cajaPrimerAp,cajaSegundoAp,cajaEdad,cajaSemestre,cajaCarrera;

protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_altas);
	cajaNumControl=findViewById(R.id.cajaNumControl);
	cajaNombre=findViewById(R.id.txtN);
	cajaPrimerAp=findViewById(R.id.txtPA);
	cajaSegundoAp=findViewById(R.id.txtSA);
	cajaEdad=findViewById(R.id.txtE);
	cajaSemestre=findViewById(R.id.txtS);
	cajaCarrera=findViewById(R.id.txtC);


}

public void agregarAlumnos(View v){
	String nc=cajaNumControl.getText().toString();
	String n=cajaNombre.getText().toString();
	String pa=cajaPrimerAp.getText().toString();
	String sa=cajaSegundoAp.getText().toString();
	String e=cajaEdad.getText().toString();
	String s=cajaSemestre.getText().toString();
	String c=cajaCarrera.getText().toString();

	//comprobar conexion de wifi

	ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo ni= cm.getActiveNetworkInfo();//checar los permisos

	//conectar y enviar datos para guardar en MySQL
    if (ni != null && ni.isConnected()){
        new  AgregarAlumno().execute(nc,n,pa,sa,e,s,c);
    }




}//METODO AGREGAR ALUMNOS


	///CLLASE INTERNA PARA REALIZAR EL ENVIO DE DATOS EN OTRO HILO DE EJECUCION

	class AgregarAlumno extends AsyncTask<String,String,String>{


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
            String url ="http://192.168.1.69/Semestre_7/Pruebas_PHP/Nueva_carpeta/Sistema_ABCC_MSQL/web_service_http_android/altas_alumnos.php";
            String metodo="POST";

            JSONObject resultado = analizador_json.peticionHTTP(url,metodo,mapDatos);
            int r=0;
            try {
                 r =resultado.getInt("exito");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (r==1){
                Log.i("Msj resultado", "REGISTRO AGREGADO");
              //  Toast.makeText(getApplicationContext(),"ALUMNO AGREGADO ",Toast.LENGTH_LONG).show();

            }else
                Log.i("Msj resultado", "NO AGREGADO ERROR");


            return null;
        }
    }
}
