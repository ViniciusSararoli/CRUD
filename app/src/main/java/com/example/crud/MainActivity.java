package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText TextName;
    private EditText TextIdNumber;
    private EditText TextEmailAddress;
    private EditText TextPhone;

    Button btnSalvar, btnAlterar, btnLista, btnExcluir;

    BD bd = new BD(this);

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
}