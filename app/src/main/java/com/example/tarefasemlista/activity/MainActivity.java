package com.example.tarefasemlista.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tarefasemlista.R;
import com.example.tarefasemlista.adapter.TarefaAdapter;
import com.example.tarefasemlista.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton BotaoSalvar = findViewById(R.id.BotaoSalvar);
        recyclerView = findViewById(R.id.recyclerView);

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
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setNomeTarefa("Ir ao Mercado");
        listaTarefas.add( tarefa1 );

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setNomeTarefa("Ir a feira");
        listaTarefas.add( tarefa2 );

        //Evibe a lista de tarefas no RecycleView


        // Configurar o Adapter
        tarefaAdapter = new TarefaAdapter( listaTarefas );

        // Configurar o RecycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        //recyclerView.setAdapter(  );

    }
}