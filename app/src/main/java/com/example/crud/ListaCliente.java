package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaCliente extends AppCompatActivity {

    ListView listViewClientes;

    BD bd = new BD(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cliente);

        listViewClientes = (ListView) findViewById(R.id.listViewClientes2);

        listarTodos();

        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int i, long l) {
                MainActivity ma = new MainActivity();
                Cliente cl = new Cliente();
                String conteudo = (String) listViewClientes.getItemAtPosition(i);
                String codigo = conteudo.substring(0, conteudo.indexOf("-"));
                Cliente cliente = bd.fetchCliente(Integer.parseInt(codigo));

                Intent main = new Intent(ListaCliente.this,MainActivity.class);

                main.putExtra("TextIdNumber",String.valueOf(cliente.getIdcliente()));
                main.putExtra("TextName",cliente.getNome());
                main.putExtra("TextEmailAddress",cliente.getEmail());
                main.putExtra("TextPhone",cliente.getTelefone());

                startActivity(main);
                finish();
            }
        });
    }
    public void listarTodos() {
        List<Cliente> clientes = bd.vetorCliente();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(ListaCliente.this, android.R.layout.simple_expandable_list_item_1, arrayList);
        listViewClientes.setAdapter(adapter);

        for (Cliente c : clientes) {
            arrayList.add(c.getIdcliente() + "-" + c.getNome()); // + " " + c.getEmail() + " " + c.getTelefone()
            adapter.notifyDataSetChanged();
        }
    }
}