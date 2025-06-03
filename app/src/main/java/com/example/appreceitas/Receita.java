package com.example.appreceitas;

public class Receita {
    private String nome;
    private String ingredientes;
    private String modoPreparo;

    // Construtor com 3 parâmetros
    public Receita(String nome, String ingredientes, String modoPreparo) {
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.modoPreparo = modoPreparo;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }
}
