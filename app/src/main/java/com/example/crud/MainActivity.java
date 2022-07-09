package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText TextName, TextIdNumber, TextEmailAddress, TextPhone;

    Button btnSalvar, btnAlterar, btnLista, btnExcluir;

    ListView listViewClientes;

    BD bd = new BD(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextName = findViewById(R.id.TextTexName);
        TextEmailAddress = findViewById(R.id.TextEmailAddress);
        TextPhone = findViewById(R.id.TextPhone);
        TextIdNumber = findViewById(R.id.TextIdNumber);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnLista = (Button) findViewById(R.id.btnLista);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);

        listViewClientes = (ListView) findViewById(R.id.listViewClientes2);

        listarTodos();

        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int i, long l) {

                String conteudo = (String) listViewClientes.getItemAtPosition(i);
                String codigo = conteudo.substring(0, conteudo.indexOf("-"));
                Cliente cliente = bd.fetchCliente(Integer.parseInt(codigo));

                TextIdNumber.setText("" + cliente.getIdcliente());
                TextName.setText(cliente.getNome());
                TextEmailAddress.setText(cliente.getEmail());
                TextPhone.setText(cliente.getTelefone());
            }
        });

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudarPagina();
            }
        });
    }
    public void mudarPagina() {
        Intent listacliente = new Intent(this,ListaCliente.class);
        startActivity(listacliente);

    }
    public void listarTodos() {
        List<Cliente> clientes = bd.vetorCliente();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, arrayList);
        listViewClientes.setAdapter(adapter);

        for (Cliente c : clientes) {
            arrayList.add(c.getIdcliente() + "-" + c.getNome()); // + " " + c.getEmail() + " " + c.getTelefone()
            adapter.notifyDataSetChanged();
        }
    }
}