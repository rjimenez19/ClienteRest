package com.example.rafa.clienterest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;


import com.example.rafa.clienterest.cliente.InterfazCliente;
import com.example.rafa.clienterest.pojo.Actividad;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Aniadir extends AppCompatActivity{

    private EditText ed1,ed2,ed3,ed4,ed5,ed6;
    private String tipo;
    private Retrofit retrofit;
    private RadioButton rb1,rb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aniadir);
        init();

        retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/").addConverterFactory(GsonConverterFactory.create()).build();
    }

    public void init(){
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editText3);
        ed4 = (EditText) findViewById(R.id.editText4);
        ed5 = (EditText) findViewById(R.id.editText5);
        ed6 = (EditText) findViewById(R.id.editText7);
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
    }

    public void aceptar(View v){

        if(rb1.isChecked()){
            tipo = "complementaria";
        }else if(rb2.isChecked()){
            tipo = "extraescolar";
        }
        Actividad act = new Actividad(tipo, ed2.getText().toString(),  ed3.getText().toString(), ed4.getText().toString() ,  ed5.getText().toString(), ed6.getText().toString(), "rafaj", "",ed1.getText().toString());

        InterfazCliente api = retrofit.create(InterfazCliente.class);
        Call<Actividad> call = api.createActividad(act);

        call.enqueue(new Callback<Actividad>() {
            @Override
            public void onResponse(Response<Actividad> response, Retrofit retrofit) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }
}
