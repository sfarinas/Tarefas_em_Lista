package com.example.tarefasemlista.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.tarefasemlista.R;
import com.example.tarefasemlista.adapter.TarefaAdapter;
import com.example.tarefasemlista.helper.DbHelper;
import com.example.tarefasemlista.helper.RecyclerItemClickListener;
import com.example.tarefasemlista.helper.TarefaDAO;
import com.example.tarefasemlista.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton BotaoSalvar = findViewById(R.id.BotaoSalvar);

        //Configurar recycler
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                // Recuperar a tarefa para edicao.
                                Tarefa tarefaSelecionada = listaTarefas.get( position );

                                // Enviar tarefa para tela adicionar tarefa
                                Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                                startActivity( intent );

                                //Log.i("clique", "Edicao do texto, no Recycler");
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                // Recuperar a tarefa para deletar
                                tarefaSelecionada = listaTarefas.get( position );

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                //Configure titulo e mensagem
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja Realmente Excluir a tarefa: " + tarefaSelecionada.getNomeTarefa() + "?" );

                                // SIM
                                dialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        TarefaDAO tarefaDAO = new TarefaDAO( getApplicationContext() );
                                        if ( tarefaDAO.deletar(tarefaSelecionada) ){

                                            carregarListaTarefas();
                                            Toast.makeText(getApplicationContext(), "Sucesso ao Excluir tarefa !",Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "Erro ao Excluir tarefa !",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                                // NAO
                                dialog.setNegativeButton("NAO", null);

                                //Exibir nossa Dialog
                                dialog.create();
                                dialog.show();

                                Log.i("clique", "Delecao do texto, no Recycler");
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        BotaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),AdicionarTarefaActivity.class);
                startActivity(intent);

                Snackbar.make(view,"Botao apertado", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    public void carregarListaTarefas(){

        // Listar Tarefas do Banco de dados, mais primeiro irei listar de forma istatica
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext() );
        listaTarefas = tarefaDAO.lista();

        //Exibe a lista de tarefas no RecycleView


        // Configurar o Adapter
        tarefaAdapter = new TarefaAdapter( listaTarefas );

        // Configurar o RecycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter( tarefaAdapter );

    }

    @Override
    protected void onStart() {
        carregarListaTarefas();
        super.onStart();
    }
}