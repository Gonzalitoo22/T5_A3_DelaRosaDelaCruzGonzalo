package controlador;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class Analizador_JSON {

    InputStream is = null;
    JSONObject jsonObject=null;
    String json=null;
    OutputStream os;
    ///metodo para alta, baja y cambios


    public JSONObject peticionHTTP(String url, String metodo, Map datos){
        HttpURLConnection conexion=null;
        URL miurl =null;

        String cadenaJSON= null;
        try {

            //las claves del lado izquierdo son como se escribieron en el archivo PHP
            cadenaJSON = "{\"nc\":\""+ URLEncoder.encode(String.valueOf(datos.get("nc")),"UTF-8")+
                    "\",\"n\":\""+ URLEncoder.encode(String.valueOf(datos.get("n")),"UTF-8")+
                    "\",\"pa\":\""+ URLEncoder.encode(String.valueOf(datos.get("pa")),"UTF-8")+
                    "\",\"sa\":\""+ URLEncoder.encode(String.valueOf(datos.get("sa")),"UTF-8")+
                    "\",\"e\":\""+ URLEncoder.encode(String.valueOf(datos.get("e")),"UTF-8")+
                    "\",\"s\":\""+ URLEncoder.encode(String.valueOf(datos.get("s")),"UTF-8")+
                    "\",\"c\":\""+ URLEncoder.encode(String.valueOf(datos.get("c")),"UTF-8")+"\"}";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            miurl= new URL(url);
            conexion= (HttpURLConnection) miurl.openConnection();
            conexion.setDoOutput(true);
            conexion.setRequestMethod(metodo);
            conexion.setFixedLengthStreamingMode(cadenaJSON.getBytes().length);
            conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
             os=new BufferedOutputStream(conexion.getOutputStream());

             //esta linea se agrego
            os.write(cadenaJSON.getBytes());
            os.flush();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is=new BufferedInputStream(conexion.getInputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder cadena= new StringBuilder();
            String linea;
            while ((linea=br.readLine())!=null){
                cadena.append(linea+"\n");
            }
            is.close();

            json=cadena.toString();
            Log.i("Mensaje uno >>> ","RESPUESTA JSON: "+json);

             jsonObject= new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }//metodo para peticion HTTP
    // /*************
    // *****************
    // *****************
    // ***************
    // ******************/


    public JSONObject peticionHTTP(String url){
        HttpURLConnection conexion=null;
        URL miurl =null;
        //OutputStream os;
        String cadenaJSON= null;


        try {
            miurl= new URL(url);
            conexion= (HttpURLConnection) miurl.openConnection();
            //activamos el envio de datos atraves de POST
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");

           // conexion.setFixedLengthStreamingMode(cadenaJSON.getBytes().length);

            conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            os=new BufferedOutputStream(conexion.getOutputStream());
            os.flush();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is=new BufferedInputStream(conexion.getInputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder cadena= new StringBuilder();
            String linea;

            while ((linea=br.readLine())!=null){
                cadena.append(linea+"\n");
            }


            is.close();

            json=cadena.toString();
            Log.i("Mensaje uno >>> ","RESPUESTA JSON: "+json);

            jsonObject= new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }//metodo para peticion HTTP


}
