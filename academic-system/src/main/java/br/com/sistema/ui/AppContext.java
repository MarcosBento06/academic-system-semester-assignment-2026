package br.com.sistema.ui;

import br.com.sistema.controller.AcademicSystemController;
import br.com.sistema.model.Usuario;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.security.AuthenticationService;
import br.com.sistema.service.AvaliacaoService;
import br.com.sistema.service.PersistenceService;
import br.com.sistema.service.ReportService;
import br.com.sistema.service.TurmaService;

public class AppContext {

    private static volatile AppContext instance;

    private final TurmaRepository repository             = new TurmaRepository();
    private final TurmaService turmaService              = new TurmaService(repository);
    private final AvaliacaoService avaliacaoService      = new AvaliacaoService(repository);
    private final PersistenceService persistenceService  = new PersistenceService(repository);
    private final ReportService reportService            = new ReportService(repository, persistenceService);

    private final AcademicSystemController controller = new AcademicSystemController(
            turmaService, avaliacaoService, persistenceService, reportService);

    private final AuthenticationService authService = new AuthenticationService();

    private Usuario usuarioAtual;

    private AppContext() {}

    public static AppContext getInstance() {
        if (instance == null) {
            synchronized (AppContext.class) {
                if (instance == null) {
                    instance = new AppContext();
                }
            }
        }
        return instance;
    }

    public AcademicSystemController getController() {
        return controller;
    }

    public AuthenticationService getAuthService() {
        return authService;
    }

    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }

    public void setUsuarioAtual(Usuario usuario) {
        this.usuarioAtual = usuario;
    }
}
