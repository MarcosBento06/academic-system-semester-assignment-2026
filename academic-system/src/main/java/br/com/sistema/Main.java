package br.com.sistema;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.model.enums.TipoAvaliacao;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.service.AvaliacaoService;

public class Main {
    public static void main(String[] args) {
        TurmaRepository repository = new TurmaRepository();
        AvaliacaoService service = new AvaliacaoService(repository);
        
        Turma turmaJava = new Turma("T-101", "Programação Orientada a Objetos");
        repository.salvar(turmaJava);

        Usuario professor = new Usuario("Dr. João", PapelUsuario.PROFESSOR);
        Usuario aluno = new Usuario("Maria", PapelUsuario.ALUNO);

        System.out.println("--- Testando AC1, AC2, AC3 e AC7 (Cenário de Sucesso) ---");
        AvaliacaoDTO exameDTO = new AvaliacaoDTO("Prova Final", 10.0, 2.0, TipoAvaliacao.EXAME);
        service.registrarAvaliacao(professor, "T-101", exameDTO);
        
        Turma turmaSalva = repository.buscarPorCodigo("T-101");
        turmaSalva.exibirInformacoes(); 

        System.out.println("\n--- Testando AC8 (Usuário sem privilégio) ---");
        try {
            service.registrarAvaliacao(aluno, "T-101", exameDTO);
        } catch (Exception e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }

        System.out.println("\n--- Testando AC4 (Turma inexistente) ---");
        try {
            service.registrarAvaliacao(professor, "T-999", exameDTO);
        } catch (Exception e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }

        System.out.println("\n--- Testando AC6 (Dados inválidos) ---");
        try {
            AvaliacaoDTO dadosInvalidos = new AvaliacaoDTO("Trabalho Ruim", -5.0, 0, TipoAvaliacao.TAREFA_PRATICA);
            service.registrarAvaliacao(professor, "T-101", dadosInvalidos);
        } catch (Exception e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }
    }
}