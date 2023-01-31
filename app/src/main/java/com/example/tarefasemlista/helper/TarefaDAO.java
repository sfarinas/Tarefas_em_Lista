package com.example.tarefasemlista.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tarefasemlista.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context) {
        DbHelper db = new DbHelper( context );
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa() );


        try {
            escreve.insert(DbHelper.TABELA_TAREFAS, null,cv );
            Log.e("INFO", "Tarefa salvar tarefa "  );
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar tarefa " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put( "nome", tarefa.getNomeTarefa() );

        try {
            String[] args = { tarefa.getId().toString() };
            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args );
            Log.e("INFO", "Tarefa Atualizada com Sucesso "  );
        }catch (Exception e){
            Log.e("INFO", "Erro ao Atualizar tarefa " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = { tarefa.getId().toString() };
            escreve.delete(DbHelper.TABELA_TAREFAS, "id=?", args );
            Log.e("INFO", "Tarefa Atualizada com Sucesso "  );
        }catch (Exception e){
            Log.e("INFO", "Erro ao Atualizar tarefa " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> lista() {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;";
        Cursor c = le.rawQuery(sql, null);

        int indice = c.getColumnIndex("id");
        int indiceNome = c.getColumnIndex("nome");

        while ( c.moveToNext() ){

            Tarefa tarefa = new Tarefa();

            //    REVISAAAR        ****************************************************
            Long id = c.getLong( indice );
            String nomeTarefa = c.getString( indiceNome );

            tarefa.setId( id );
            tarefa.setNomeTarefa( nomeTarefa );

            tarefas.add( tarefa );

        }

        return tarefas;
    }
}
