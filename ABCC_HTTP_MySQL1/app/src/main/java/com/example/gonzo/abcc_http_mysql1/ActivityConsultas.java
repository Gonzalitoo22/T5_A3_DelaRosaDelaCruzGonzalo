package com.example.gonzo.abcc_http_mysql1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import controlador.Analizador_JSON;

public class ActivityConsultas extends Activity {
    ListView lsl_consulta;
    ArrayAdapter <String> adapter;
    ArrayList<String>arrayList=new ArrayList<>();
    RadioGroup rbGr;
    RadioButton rbNum,rbNombre,rbPrimerAp;
    EditText txtNC, txtN, txtPA;
    volatile String dato;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);
        lsl_consulta=findViewById(R.id.lsl_consulta);
        rbGr= findViewById(R.id.rbGr);
        rbNum= findViewById(R.id.rbNum);
        rbNombre= findViewById(R.id.rbNombre);
        rbPrimerAp= findViewById(R.id.rbPrimerAp);
        txtNC = findViewById(R.id.txtNC);
        txtN = findViewById(R.id.txtN);
        txtPA = findViewById(R.id.txtPA);

        rbGr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbNum:
                        dato = "";
                        dato = txtNC.getText().toString();
                        new MostrarAlumnos().execute(dato);

                    break;
                    case R.id.rbNombre:
                        dato = "";
                        dato = txtN.getText().toString();
                        new MostrarAlumnos().execute(dato);
                    break;
                    case R.id.rbPrimerAp:
                        dato = "";
                        dato = txtPA.getText().toString();
                        new MostrarAlumnos().execute(dato);
                    break;
                }
            }
        });

        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        lsl_consulta.setAdapter(adapter);
    }//onCreate
    class MostrarAlumnos extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            Analizador_JSON analizador_json = new Analizador_JSON();

            //cambiar el nombre del archivo php, debe de ser el de consulta
            String url="http://192.168.1.69/Semestre_7/Pruebas_PHP/Nueva_carpeta/Sistema_ABCC_MSQL/web_service_http_android/consultas_alumnos.php";
            JSONObject jsonObject= analizador_json.peticionHTTP(url);


            try {
                JSONArray jsonArray = jsonObject.getJSONArray("alumnos");
                String cadena="";
                for (int i=0; i<jsonArray.length();i++){
                    if(strings[0].equals(jsonArray.getJSONObject(i).getString("nc"))){
                        cadena=jsonArray.getJSONObject(i).getString("nc")+"|"+
                                jsonArray.getJSONObject(i).getString("n")+"|"+
                                jsonArray.getJSONObject(i).getString("pa")+"|"+
                                jsonArray.getJSONObject(i).getString("sa")+"|"+
                                jsonArray.getJSONObject(i).getString("e")+"|"+
                                jsonArray.getJSONObject(i).getString("s")+"|"+
                                jsonArray.getJSONObject(i).getString("c");

                        //se agrega String ´por String al array list, esto llena el array lista para despues meterlo al adaptador
                        arrayList.add(cadena);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }//mostrar alumnos
    }
}
