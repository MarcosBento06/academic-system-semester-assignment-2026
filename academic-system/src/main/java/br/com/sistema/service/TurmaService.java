package br.com.sistema.service;

import br.com.sistema.dto.TurmaDTO;
import br.com.sistema.exception.AcademicSystemException;
import br.com.sistema.exception.InvalidClassException;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.security.exception.AuthorizationException;
import br.com.sistema.validation.DomainValidator;

public class TurmaService {

    private TurmaRepository repository;

    public TurmaService(TurmaRepository repository) {
        this.repository = repository;
    }

    public void registrarTurma(Usuario usuario, TurmaDTO dto) {
        if (usuario == null || usuario.getPapel() != PapelUsuario.ADMIN) {
            System.out.println("[AUDIT] Cadastro negado.");
            throw new AuthorizationException("Apenas administradores podem cadastrar turmas.");
        }

        /*if (dto.getCodigo() == null || dto.getCodigo().isBlank() || dto.getDisciplina() == null || dto.getDisciplina().isBlank()) {
            System.out.println("[AUDIT] Dados inválidos.");
            //throw new AcademicSystemException("Código e disciplina são obrigatórios.");
            throw new InvalidClassException("Dados da turma inválidos.");
        }*/

        Turma turma = new Turma(dto.getCodigo(), dto.getDisciplina());
        DomainValidator.validarTurma(turma);
        repository.salvar(turma);

        System.out.println("[AUDIT] Turma cadastrada: "+ dto.getCodigo());
    }
}