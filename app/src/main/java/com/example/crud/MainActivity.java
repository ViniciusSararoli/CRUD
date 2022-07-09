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
        btnLista = (Button) findViewById(R.id.btnLista);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);

        listViewClientes = (ListView) findViewById(R.id.listViewClientes1);
        limparCampos();
        listarTodos();

        //******RECEBER DADOS DA OUTRA TELA *********

        String idClienteLista = getIntent().getStringExtra("TextIdNumber");
        String nomeLista = getIntent().getStringExtra("TextName");
        String emailLista = getIntent().getStringExtra("TextEmailAddress");
        String phoneLista = getIntent().getStringExtra("TextPhone");

        if(nomeLista != null) {
            TextName.setText(nomeLista);
        }
        if(idClienteLista != null) {
            TextIdNumber.setText(idClienteLista);
        }

        if(emailLista != null) {
            TextEmailAddress.setText(emailLista);
        }
        if(phoneLista != null) {
            TextPhone.setText(phoneLista);
        }


        //*******************************************


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
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idcliente = TextIdNumber.getText().toString();
                String nome = TextName.getText().toString();
                String phone = TextPhone.getText().toString();

                    if(nome.isEmpty() || phone.isEmpty()) {
                        TextName.requestFocus();
                        Toast.makeText(MainActivity.this, "Nome e Telefone são obrigatórios", Toast.LENGTH_LONG).show();
                    } else {
                        if(idcliente.isEmpty()) {
                            salvarDados();
                            listarTodos();
                            limparCampos();
                        }else{
                            alterarDados();
                            listarTodos();
                            limparCampos();
                        }

                    }
            }
        });
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mudarPagina();
                limparCampos();
            }
        });
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirDados();
            }
        });
    }
    public void salvarDados() {
        bd.insertCliente(new Cliente(String.valueOf(TextName.getText()),String.valueOf(TextEmailAddress.getText()),String.valueOf(TextPhone.getText())));
        listarTodos();
        limparCampos();
        Toast.makeText(MainActivity.this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
    }
    public void alterarDados() {
        Cliente cliente = new Cliente();
        cliente.setIdcliente(Integer.parseInt(String.valueOf(TextIdNumber.getText())));
        cliente.setNome(String.valueOf(TextName.getText()));
        cliente.setTelefone(String.valueOf(TextPhone.getText()));
        cliente.setEmail(String.valueOf(TextEmailAddress.getText()));
        bd.updateCliente(cliente);
        listarTodos();
        Toast.makeText(MainActivity.this, "Alterado com sucesso!", Toast.LENGTH_LONG).show();

    }
    public void mudarPagina() {
        Intent listacliente = new Intent(this,ListaCliente.class);
        startActivity(listacliente);

    }
    public void excluirDados() {
        Cliente cliente = new Cliente();
        cliente.setIdcliente(Integer.parseInt(String.valueOf(TextIdNumber.getText())));
        bd.deleteCliente(cliente);
        listarTodos();
        limparCampos();
        Toast.makeText(MainActivity.this, "Excluido com sucesso!", Toast.LENGTH_LONG).show();
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
    public void limparCampos() {
        TextIdNumber.setText("");
        TextName.setText("");
        TextEmailAddress.setText("");
        TextPhone.setText("");

        TextName.requestFocus();
    }
}