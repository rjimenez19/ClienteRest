package com.example.rafa.clienterest.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rafa.clienterest.R;
import com.example.rafa.clienterest.pojo.Actividad;

import java.util.List;

public class Adaptador extends ArrayAdapter<Actividad> {

    private int resource;
    private LayoutInflater inflater;
    private List<Actividad> lista;
    private Context context;

    static class ViewHolder {
        public TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    }

    public Adaptador(Context context, int resource, List<Actividad> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.lista = objects;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder gv = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(resource, null);
            gv.tv1 = (TextView) convertView.findViewById(R.id.textView8);
            gv.tv2 = (TextView) convertView.findViewById(R.id.textView9);
            gv.tv3 =  (TextView) convertView.findViewById(R.id.textView10);
            gv.tv4 = (TextView) convertView.findViewById(R.id.textView11);
            gv.tv5 = (TextView) convertView.findViewById(R.id.textView12);
            gv.tv6 = (TextView) convertView.findViewById(R.id.textView13);
            gv.tv7 = (TextView) convertView.findViewById(R.id.textView14);
            convertView.setTag(gv);
        } else {
            gv = (ViewHolder) convertView.getTag();
        }

        Actividad a = lista.get(position);
        gv.tv4.setText(a.getLugarf());
        gv.tv3.setText(a.getLugari());
        gv.tv5.setText(a.getFechai());
        gv.tv6.setText(a.getFechaf());
        gv.tv2.setText(a.getDescripcion());
        gv.tv7.setText(a.getTipo());
        gv.tv1.setText(a.getIdprofesor());
        return convertView;
    }
}
