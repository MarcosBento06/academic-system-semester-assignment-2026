package br.com.sistema;

import br.com.sistema.controller.AcademicSystemController;
import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.dto.TurmaDTO;
import br.com.sistema.input.exception.InvalidMenuOptionException;
import br.com.sistema.input.exception.InvalidNumericInputException;
import br.com.sistema.input.exception.KeyboardInputException;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.model.enums.TipoAvaliacao;
import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.security.AuthenticationService;
import br.com.sistema.service.AvaliacaoService;
import br.com.sistema.service.PersistenceService;
import br.com.sistema.service.ReportService;
import br.com.sistema.service.TurmaService;

import java.util.Scanner;

public class Main {

    private static Usuario usuarioAtual;
    private static Scanner scanner = new Scanner(System.in);

    private static TurmaRepository repository           = new TurmaRepository();
    private static TurmaService turmaService            = new TurmaService(repository);
    private static AvaliacaoService avaliacaoService    = new AvaliacaoService(repository);
    private static PersistenceService persistenceService = new PersistenceService(repository);
    private static ReportService reportService          = new ReportService(repository, persistenceService);
    private static AuthenticationService authService    = new AuthenticationService();

    private static AcademicSystemController controller = new AcademicSystemController(
            turmaService, avaliacaoService, persistenceService, reportService);

    private static int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidNumericInputException("Entrada inválida. Digite um número.");
        }
    }

    private static void mostrarLogin() {
        System.out.println("\n===== LOGIN =====");
        System.out.print("Usuário: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        try {
            usuarioAtual = authService.autenticar(login, senha);
            System.out.println("Login efetuado com sucesso. Bem-vindo, " + usuarioAtual.getNome() + "!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            usuarioAtual = null;
        }
    }

    private static void menuAdmin() {
        while (true) {
            try {
                System.out.println("\n===== MENU ADMIN =====");
                System.out.println("1 - Cadastrar turma");
                System.out.println("2 - Listar turmas");
                System.out.println("3 - Configurar persistência");
                System.out.println("4 - Mostrar persistência atual");
                System.out.println("5 - Salvar dados");
                System.out.println("6 - Relatório: resumo de avaliações");
                System.out.println("7 - Relatório: peso das avaliações");
                System.out.println("8 - Relatório: configuração de persistência");
                System.out.println("9 - Logout");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");

                int opcao = lerInteiro();

                switch (opcao) {
                    case 1:
                        System.out.print("Código: ");
                        String codigo = scanner.nextLine();
                        System.out.print("Disciplina: ");
                        String disciplina = scanner.nextLine();
                        try {
                            controller.registrarTurma(usuarioAtual, new TurmaDTO(codigo, disciplina));
                            System.out.println("Turma cadastrada com sucesso.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 2:
                        controller.listarTurmas().values()
                                .forEach(t -> System.out.println(t.getCodigo() + " - " + t.getDisciplina()));
                        break;

                    case 3:
                        System.out.println("1 - TXT\n2 - XML\n3 - JSON");
                        System.out.print("Escolha: ");
                        int tipo = lerInteiro();
                        switch (tipo) {
                            case 1: controller.configurarPersistencia(usuarioAtual, PersistenceType.TXT); break;
                            case 2: controller.configurarPersistencia(usuarioAtual, PersistenceType.XML); break;
                            case 3: controller.configurarPersistencia(usuarioAtual, PersistenceType.JSON); break;
                            default: throw new InvalidMenuOptionException("Tipo de persistência inválido.");
                        }
                        break;

                    case 4:
                        System.out.println("Persistência atual: " + controller.obterPersistenciaAtual());
                        break;

                    case 5:
                        try {
                            controller.salvarDados();
                            System.out.println("Dados salvos com sucesso.");
                        } catch (Exception e) {
                            System.out.println("Erro ao salvar: " + e.getMessage());
                        }
                        break;

                    case 6:
                        System.out.println(controller.gerarRelatorioResumoAvaliacoes(usuarioAtual));
                        break;

                    case 7:
                        System.out.println(controller.gerarRelatoriopesoAvaliacoes(usuarioAtual));
                        break;

                    case 8:
                        System.out.println(controller.gerarRelatorioConfiguracaoPersistencia(usuarioAtual));
                        break;

                    case 9:
                        authService.logout(usuarioAtual.getNome());
                        usuarioAtual = null;
                        System.out.println("Logout realizado.");
                        return;

                    case 0:
                        System.exit(0);

                    default:
                        throw new InvalidMenuOptionException("Opção de menu inválida.");
                }
            } catch (KeyboardInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void menuProfessor() {
        while (true) {
            try {
                System.out.println("\n===== MENU PROFESSOR =====");
                System.out.println("1 - Registrar avaliação");
                System.out.println("2 - Salvar dados");
                System.out.println("3 - Relatório: resumo de avaliações");
                System.out.println("4 - Relatório: peso das avaliações");
                System.out.println("5 - Logout");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");

                int opcao = lerInteiro();

                switch (opcao) {
                    case 1:
                        try {
                            System.out.print("Código da turma: ");
                            String codigoTurma = scanner.nextLine();
                            System.out.print("Título da avaliação: ");
                            String titulo = scanner.nextLine();
                            System.out.print("Valor: ");
                            double valor = Double.parseDouble(scanner.nextLine());
                            System.out.print("Peso: ");
                            double peso = Double.parseDouble(scanner.nextLine());
                            System.out.println("Tipo:\n1 - EXAME\n2 - TAREFA_PRATICA\n3 - SEMINARIO\n4 - ATRIBUICAO");
                            System.out.print("Escolha: ");
                            int tipoOpcao = lerInteiro();
                            TipoAvaliacao tipo = null;
                            switch (tipoOpcao) {
                                case 1: tipo = TipoAvaliacao.EXAME; break;
                                case 2: tipo = TipoAvaliacao.TAREFA_PRATICA; break;
                                case 3: tipo = TipoAvaliacao.SEMINARIO; break;
                                case 4: tipo = TipoAvaliacao.ATRIBUICAO; break;
                                default: System.out.println("Tipo inválido."); break;
                            }
                            if (tipo != null) {
                                controller.registrarAvaliacao(usuarioAtual, codigoTurma,
                                        new AvaliacaoDTO(titulo, valor, peso, tipo));
                                System.out.println("Avaliação registrada com sucesso.");
                            }
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                        break;

                    case 2:
                        try {
                            controller.salvarDados();
                            System.out.println("Dados salvos com sucesso.");
                        } catch (Exception e) {
                            System.out.println("Erro ao salvar: " + e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.println(controller.gerarRelatorioResumoAvaliacoes(usuarioAtual));
                        break;

                    case 4:
                        System.out.println(controller.gerarRelatoriopesoAvaliacoes(usuarioAtual));
                        break;

                    case 5:
                        authService.logout(usuarioAtual.getNome());
                        usuarioAtual = null;
                        System.out.println("Logout realizado.");
                        return;

                    case 0:
                        System.exit(0);

                    default:
                        throw new InvalidMenuOptionException("Opção de menu inválida.");
                }
            } catch (KeyboardInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            controller.carregarDados();
        } catch (Exception e) {
            System.out.println("Aviso: não foi possível carregar dados anteriores. (" + e.getMessage() + ")");
        }

        while (true) {
            try {
                mostrarLogin();

                if (usuarioAtual == null) continue;

                if (usuarioAtual.getPapel() == PapelUsuario.ADMIN) {
                    menuAdmin();
                } else if (usuarioAtual.getPapel() == PapelUsuario.PROFESSOR) {
                    menuProfessor();
                } else {
                    System.out.println("Papel não suportado: " + usuarioAtual.getPapel());
                }
            } catch (br.com.sistema.security.exception.SecurityException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
