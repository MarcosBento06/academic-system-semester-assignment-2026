package br.com.sistema.controller;

import br.com.sistema.dto.TurmaDTO;
import br.com.sistema.model.Usuario;
import br.com.sistema.service.TurmaService;

public class TurmaController {

    private TurmaService service;

    public TurmaController(TurmaService service) {
        this.service = service;
    }

    public void registrarTurma(Usuario usuario,TurmaDTO dto) {
        service.registrarTurma(usuario, dto);
    }
}