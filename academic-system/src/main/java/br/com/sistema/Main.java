package br.com.sistema;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.model.enums.TipoAvaliacao;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.service.AvaliacaoService;
import br.com.sistema.controller.AcademicSystemController;
import br.com.sistema.controller.TurmaController;
import br.com.sistema.dto.TurmaDTO;
import br.com.sistema.exception.AcademicSystemException;
import br.com.sistema.input.exception.InvalidMenuOptionException;
import br.com.sistema.input.exception.InvalidNumericInputException;
import br.com.sistema.input.exception.KeyboardInputException;
import br.com.sistema.service.TurmaService;
import java.util.Scanner;
import br.com.sistema.security.AuthenticationService;
import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.service.PersistenceService;

public class Main {
	private static Usuario usuarioAtual;
	private static Scanner scanner = new Scanner(System.in);
	private static TurmaRepository repository = new TurmaRepository();
	private static AvaliacaoService avaliacaoService = new AvaliacaoService(repository);
	private static TurmaService turmaService = new TurmaService(repository);
	private static TurmaController turmaController = new TurmaController(turmaService);
	private static AuthenticationService authService = new AuthenticationService();
	private static PersistenceService persistenceService = new PersistenceService();
	private static AcademicSystemController academicController = new AcademicSystemController(repository, turmaService, avaliacaoService, persistenceService);
	
	private static void mostrarLogin() {
		System.out.println("\n===== LOGIN =====");
		System.out.print("Usuário: ");
		String login = scanner.nextLine();

		System.out.print("Senha: ");
		String senha = scanner.nextLine();

		try {
			usuarioAtual = authService.autenticar(login, senha);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			usuarioAtual = null;
		}
	}

	private static int lerInteiro() {
		try {
	        return Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        throw new InvalidNumericInputException("Entrada inválida. Digite um número.");
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
				System.out.println("5 - Logout");
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
							academicController.registrarTurma(usuarioAtual, new TurmaDTO(codigo, disciplina));
							System.out.println("Turma cadastrada com sucesso.");
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case 2:
						repository.listarTurmas().values().forEach(t -> System.out.println(t.getCodigo() + " - " + t.getDisciplina()));
						break;
					case 3:
						System.out.println("1 - TXT");
						System.out.println("2 - XML");
						System.out.println("3 - JSON");
						System.out.print("Escolha: ");
	
						int tipo = lerInteiro();
	
						switch (tipo) {
							case 1:
								academicController.configurarPersistencia(usuarioAtual, PersistenceType.TXT);
						    break;
						    case 2:
						        academicController.configurarPersistencia(usuarioAtual, PersistenceType.XML);
						    break;
						    case 3:
						        academicController.configurarPersistencia(usuarioAtual, PersistenceType.JSON);
						    break;
						    default:
						       throw new InvalidMenuOptionException("Tipo de persistência inválido.");
						}
					break;
					case 4:
					    System.out.println("Persistência atual: " + academicController.obterPersistenciaAtual());
					    break;
					case 5:
					    usuarioAtual = null;
					    return;
					case 0:
						System.exit(0);
					default:
						 throw new InvalidMenuOptionException("Opção de menu inválida.");
				}
			}catch (KeyboardInputException e) {
	            System.out.println(e.getMessage());
	        }
		}
	}

	private static void menuProfessor() {
		while (true) {
			try {
				System.out.println("\n===== MENU PROFESSOR =====");
				System.out.println("1 - Registrar avaliação");
				System.out.println("2 - Exportar TXT");
				System.out.println("3 - Logout");
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
	
						System.out.println("Tipo da avaliação:");
						System.out.println("1 - EXAME");
						System.out.println("2 - TAREFA_PRATICA");
						System.out.println("3 - SEMINARIO");
						System.out.println("4 - ATRIBUICAO");
						System.out.print("Escolha: ");
	
						int tipoOpcao = lerInteiro();
	
						TipoAvaliacao tipo = null;
						switch (tipoOpcao) {
						case 1:
							tipo = TipoAvaliacao.EXAME;
							break;
						case 2:
							tipo = TipoAvaliacao.TAREFA_PRATICA;
							break;
						case 3:
							tipo = TipoAvaliacao.SEMINARIO;
							break;
						case 4:
							tipo = TipoAvaliacao.ATRIBUICAO;
							break;
						default:
							System.out.println("Tipo inválido.");
							break;
						}
	
						AvaliacaoDTO dto = new AvaliacaoDTO(titulo, valor, peso, tipo);
	
						academicController.registrarAvaliacao(usuarioAtual, codigoTurma, dto);
	
						System.out.println("Avaliação registrada com sucesso.");
	
					} catch (Exception e) {
						System.out.println("Erro: " + e.getMessage());
					}
					break;
				case 2:
					try {
						 switch (academicController.obterPersistenciaAtual()) {
				            case TXT:
				                academicController.exportarTxt("avaliacoes.txt");
				                System.out.println("Arquivo TXT gerado.");
				                break;
				            case XML:
				                System.out.println("Persistência XML configurada.");
				                break;
				            case JSON:
				                System.out.println("Persistência JSON configurada.");
				                break;
				        }
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				case 3:
					usuarioAtual = null;
					return;
				case 0:
					System.exit(0);
				default:
					throw new InvalidMenuOptionException("Opção de menu inválida.");
				}
			}catch (KeyboardInputException e) {
	            System.out.println(e.getMessage());
	        }
		}
	}

	public static void main(String[] args) {

		while (true) {
			try {
				mostrarLogin();
	
				if (usuarioAtual == null) {
					continue;
				}
	
				if (usuarioAtual.getPapel() == PapelUsuario.ADMIN) {
					menuAdmin();
				} else if (usuarioAtual.getPapel() == PapelUsuario.PROFESSOR) {
					menuProfessor();
				}
			}catch (SecurityException e) {
			    System.out.println(e.getMessage());
			}
		}
	}
}