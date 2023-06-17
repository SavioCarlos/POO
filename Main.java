import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorTarefas gerenciador = new GerenciadorTarefas();
        System.out.print("Digite o nome de usuário: ");
        String nomeUsuario = scanner.nextLine();
        Usuarios usuarios = new Usuarios();
        File arquivo = new File(nomeUsuario + ".txt");
        if (arquivo.exists()) {
            System.out.println("Esse usuário existe!");
            usuarios.autenticarUsuario(arquivo);
        } else {
            usuarios.criarUsuario(nomeUsuario);
        }
    }
}