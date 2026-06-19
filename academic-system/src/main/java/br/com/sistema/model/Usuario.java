package br.com.sistema.model;

import br.com.sistema.model.enums.PapelUsuario;

public class Usuario {
    private String nome;
    private PapelUsuario papel;

    public Usuario(String nome, PapelUsuario papel) {
        this.nome = nome;
        this.papel = papel;
    }

    public PapelUsuario getPapel() { return papel; }
    public String getNome() { return nome; }
}