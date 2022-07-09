package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

//https://developer.android.com/training/data-storage/sqlite?hl=pt-br#java
public class BD extends SQLiteOpenHelper {
    public static final String BANCO_NAME = "crud";
    public static int BANCO_VERCAO = 1;


    public static final String TABELA_NAME = "cliente";
    public static final String COLUNA_IDCLIENTE = "idcliente";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_TELEFONE = "telefone";

    public BD(Context context) { //, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version
        super(context, BANCO_NAME, null, BANCO_VERCAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABELA_NAME + " (" +
                        COLUNA_IDCLIENTE + " INTEGER PRIMARY KEY," +
                        COLUNA_NOME + " TEXT," +
                        COLUNA_TELEFONE + " TEXT," +
                        COLUNA_EMAIL + " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        BANCO_VERCAO++;
        db.execSQL("DROP TABLE IF EXISTS " +  TABELA_NAME);
        onCreate(db);
    }

    void  insertCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_EMAIL, cliente.getEmail());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());

        db.insert(TABELA_NAME, null, values);
        db.close();
    }

    void deleteCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_NAME, COLUNA_IDCLIENTE + " = ?", new String[] {String.valueOf(cliente.getIdcliente()) });
        db.close();
    }

    Cliente fetchCliente(int idcliente) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_NAME, new String[] {
                COLUNA_IDCLIENTE, COLUNA_NOME,COLUNA_EMAIL,COLUNA_TELEFONE}, COLUNA_IDCLIENTE + " = ? ",
                new String[] {String.valueOf(idcliente)}, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }

        Cliente cliente = new Cliente(Integer.parseInt(
                cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        return cliente;
    }

    void updateCliente(Cliente cliente) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_EMAIL, cliente.getEmail());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());

        db.update(TABELA_NAME, values, COLUNA_IDCLIENTE + " = ? ",
                new String[] {String.valueOf(cliente.getIdcliente()) });
        db.close();
    }

    public List<Cliente> vetorCliente() {
        List<Cliente> listaCliente = new ArrayList<Cliente>();

        String query = "SELECT * FROM " + TABELA_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()) {
            do {
                Cliente cl = new Cliente();
                cl.setIdcliente(Integer.parseInt(c.getString(0)));
                cl.setNome(c.getString(1));
                cl.setEmail(c.getString(2));
                cl.setTelefone(c.getString(3));

                listaCliente.add(cl);

            } while (c.moveToNext());
        }
        return listaCliente;
    }
}
