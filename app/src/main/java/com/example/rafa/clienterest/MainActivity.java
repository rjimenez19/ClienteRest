package com.example.rafa.clienterest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rafa.clienterest.cliente.InterfazCliente;
import com.example.rafa.clienterest.pojo.Actividad;
import com.example.rafa.clienterest.util.Adaptador;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayList<Actividad> listaActividades;
    private Adaptador adp;
    private Retrofit retrofit;
    private int pos;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Aniadir.class);
                startActivityForResult(i, 0);
            }
        });
        init();
        cargarActividades();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == lv.getId()) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Accion");
            menu.add(Menu.NONE, 0, 0, "Borrar");
            menu.add(Menu.NONE, 1, 1,"Editar");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                borrarActividad();
                break;
            case 1:
                editarActividad();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){
            cargarActividades();
        }
    }

    public void init() {
        lv = (ListView) findViewById(R.id.listView);
        listaActividades = new ArrayList<>();
        retrofit = new Retrofit.Builder().baseUrl("http://ieszv.x10.bz/").addConverterFactory(GsonConverterFactory.create()).build();

        registerForContextMenu(lv);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                return false;
            }
        });
    }

    private void cargarActividades() {
        listaActividades = new ArrayList<>();
        InterfazCliente api = retrofit.create(InterfazCliente.class);
        Call<List<Actividad>> call = api.getActividades();

        call.enqueue(new Callback<List<Actividad>>() {
            @Override
            public void onResponse(Response<List<Actividad>> response, Retrofit retrofit) {
                for (Actividad act : response.body()) {
                    listaActividades.add(act);
                }
                adp = new Adaptador(context, R.layout.item, listaActividades);
                lv.setAdapter(adp);
            }
            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }

    private void borrarActividad() {
        InterfazCliente api = retrofit.create(InterfazCliente.class);

        Call<Actividad> call = api.deleteActividad(listaActividades.get(pos).getId() + "");
        call.enqueue(new Callback<Actividad>() {
            @Override
            public void onResponse(Response<Actividad> response, Retrofit retrofit) {
                cargarActividades();
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }

    private void editarActividad(){
        Actividad ac = listaActividades.get(pos);
        Intent i = new Intent(this, Editar.class);
        i.putExtra("actividad", ac);
        startActivityForResult(i, 0);
    }
}
