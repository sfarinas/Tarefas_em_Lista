package com.example.tarefasemlista.model;

import java.io.Serializable;

public class Tarefa implements Serializable {

    private Long id;
    private String nomeTarefa;
    private Double cauculoTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public Double getCauculoTotal() {
        return cauculoTotal;
    }

    public void setCauculoTotal(Double cauculoTotal) {
        this.cauculoTotal = cauculoTotal;
    }
}
