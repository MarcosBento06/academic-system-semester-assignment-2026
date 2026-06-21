package br.com.sistema.model;

import br.com.sistema.model.enums.PapelUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {

    private String nome;
    private PapelUsuario papel;
}