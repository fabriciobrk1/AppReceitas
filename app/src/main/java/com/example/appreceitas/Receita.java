package com.example.appreceitas;

public class Receita {
    private String nome;
    private String ingredientes;

    public Receita(String nome, String ingredientes) {
        this.nome = nome;
        this.ingredientes = ingredientes;
    }

    public String getNome() {
        return nome;
    }

    public String getIngredientes(){
        return ingredientes;
    }
}