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
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.textTarefa);

        // Recuperar tarefa, caso seja edicao
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if ( tarefaAtual != null ){
            editTarefa.setText( tarefaAtual.getNomeTarefa() );

        }
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

                if ( tarefaAtual != null ){ // edicao
                    String nomeTarefa = editTarefa.getText().toString();
                    if ( !nomeTarefa.isEmpty() ){

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa( nomeTarefa );
                        tarefa.setId( tarefaAtual.getId() );

                        // Atualizar no banco de dados
                        if ( tarefaDAO.atualizar( tarefa ) ){
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao Atualizar tarefa !",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Erro ao Atualizar tarefa !",Toast.LENGTH_SHORT).show();
                        }

                    }

                }else { // Salvar

                    String nomeTarefa = editTarefa.getText().toString();
                    if ( !nomeTarefa.isEmpty() ){

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);

                        if ( tarefaDAO.salvar( tarefa ) ){
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao salvar tarefa !",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Erro ao salvar tarefa", Toast.LENGTH_SHORT).show();
                        }

                        finish();
                    }
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}