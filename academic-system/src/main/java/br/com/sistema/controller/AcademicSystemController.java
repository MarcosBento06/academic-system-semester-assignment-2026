package br.com.sistema.controller;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.dto.TurmaDTO;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.service.AvaliacaoService;
import br.com.sistema.service.PersistenceService;
import br.com.sistema.service.TurmaService;

public class AcademicSystemController {
    private TurmaRepository repository;
    private TurmaService turmaService;
    private AvaliacaoService avaliacaoService;
    private PersistenceService persistenceService;

    public AcademicSystemController(TurmaRepository repository, TurmaService turmaService, AvaliacaoService avaliacaoService, PersistenceService persistenceService) {
        this.repository = repository;
        this.turmaService = turmaService;
        this.avaliacaoService = avaliacaoService;
        this.persistenceService = persistenceService;
    }

    public void registrarTurma(Usuario usuario, TurmaDTO dto) {
        turmaService.registrarTurma(usuario, dto);
    }

    public void registrarAvaliacao(Usuario usuario, String codigoTurma, AvaliacaoDTO dto) {
        avaliacaoService.registrarAvaliacao(usuario, codigoTurma, dto);
    }

    public void exportarTxt(String nomeArquivo)throws Exception {
        repository.exportarParaTxt(nomeArquivo);
    }

    public Turma buscarTurma(String codigo) {
        return repository.buscarPorCodigo(codigo);
    }
    
    public void configurarPersistencia(Usuario usuario, PersistenceType tipo) {
        persistenceService.configurarPersistencia(usuario, tipo);
    }

    public PersistenceType obterPersistenciaAtual() {
        return persistenceService.getTipoAtual();
    }
}