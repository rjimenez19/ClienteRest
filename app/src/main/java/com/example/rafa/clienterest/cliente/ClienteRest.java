package com.example.rafa.clienterest.cliente;

import android.os.AsyncTask;
import android.util.Log;

import com.example.rafa.clienterest.pojo.Actividad;


import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ClienteRest {

    class Tarea extends AsyncTask<String, Long, String> {

        Tarea() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/").addConverterFactory(GsonConverterFactory.create()).build();
            InterfazCliente api = retrofit.create(InterfazCliente.class);
            Call<List<Actividad>> call = api.getActividades();

            call.enqueue(new Callback<List<Actividad>>() {
                @Override
                public void onResponse(Response<List<Actividad>> response, Retrofit retrofit) {
                    for (Actividad a : response.body()) {
                        Log.v("HOLA", a.toString());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.getLocalizedMessage();
                }
            });
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
