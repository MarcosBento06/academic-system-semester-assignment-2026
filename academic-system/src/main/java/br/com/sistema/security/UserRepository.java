package br.com.sistema.security;

import br.com.sistema.security.exception.AuthenticationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserRepository {

    public String[] buscarUsuario(String login) {
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados[0].equals(login)) {
                    return dados;
                }
            }
        } catch (IOException e) {
            throw new AuthenticationException("Arquivo de usuários não encontrado ou inacessível.");
        }
        return null;
    }
}
