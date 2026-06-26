package br.com.sistema.service;

import br.com.sistema.logging.AcademicLogger;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.repository.JsonTurmaRepository;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.repository.XmlTurmaRepository;
import br.com.sistema.security.exception.AuthorizationException;

import java.util.Collection;
import java.util.logging.Logger;

public class PersistenceService {

    private static final Logger logger = AcademicLogger.getLogger();

    private PersistenceType tipoAtual = PersistenceType.TXT;
    private TurmaRepository turmaRepository;
    private final XmlTurmaRepository xmlRepository;
    private final JsonTurmaRepository jsonRepository;

    public PersistenceService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
        this.xmlRepository = new XmlTurmaRepository();
        this.jsonRepository = new JsonTurmaRepository();
    }

    public void configurarPersistencia(Usuario usuario, PersistenceType tipo) {
        if (usuario == null || usuario.getPapel() != PapelUsuario.ADMIN) {
            logger.warning("AUTHZ_FAILURE - configurarPersistencia negado para: "
                    + (usuario != null ? usuario.getNome() : "null"));
            throw new AuthorizationException("Apenas administradores podem configurar a persistência.");
        }
        tipoAtual = tipo;
        logger.info("PERSISTENCE_CONFIG - tipo alterado para: " + tipo + " por: " + usuario.getNome());
    }

    public void carregarDados() throws Exception {
        switch (tipoAtual) {
            case TXT:
                turmaRepository.carregarDeTxt("avaliacoes.txt");
                break;
            case XML:
                xmlRepository.carregarDeXml("avaliacoes.xml")
                        .forEach(turmaRepository::salvar);
                break;
            case JSON:
                jsonRepository.carregarDeJson("avaliacoes.json")
                        .forEach(turmaRepository::salvar);
                break;
        }
        logger.info("PERSISTENCE_LOAD - dados carregados do formato: " + tipoAtual);
    }

    public void salvarDados() throws Exception {
        Collection<Turma> turmas = turmaRepository.listarTurmas().values();
        switch (tipoAtual) {
            case TXT:
                turmaRepository.exportarParaTxt("avaliacoes.txt");
                break;
            case XML:
                xmlRepository.exportarParaXml("avaliacoes.xml", turmas);
                break;
            case JSON:
                jsonRepository.exportarParaJson("avaliacoes.json", turmas);
                break;
        }
        logger.info("PERSISTENCE_SAVE - dados salvos em formato: " + tipoAtual);
    }

    public void exportarTxt(String nomeArquivo) throws Exception {
        turmaRepository.exportarParaTxt(nomeArquivo);
    }

    public PersistenceType getTipoAtual() {
        return tipoAtual;
    }
}
