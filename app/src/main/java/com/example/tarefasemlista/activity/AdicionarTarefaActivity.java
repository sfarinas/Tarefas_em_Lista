package com.example.tarefasemlista.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tarefasemlista.R;
import com.example.tarefasemlista.helper.TarefaDAO;
import com.example.tarefasemlista.model.Tarefa;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.textTarefa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.itemSalvar:
                //Executar acao para o item salvar
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                Tarefa tarefa = new Tarefa();
                tarefa.setNomeTarefa("Ir ao mercado");
                tarefaDAO.salvar( tarefa );

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}