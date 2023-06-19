import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

public class Usuarios {
    String nomeUsuario;
    private String senha;
    public int escolha;

    public void criarUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;

        // Verifica se o arquivo já existe
        File arquivo = new File(nomeUsuario + ".txt");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a senha: ");
        senha = scanner.nextLine();

        // Cria um novo arquivo e registra o usuário e senha
        try {
            FileWriter escritor = new FileWriter(arquivo);
            escritor.write("Usuário: " + nomeUsuario + "\n");
            escritor.write("Senha: " + senha + "\n");
            escritor.close();
            System.out.println("Usuário registrado com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo.");
        }
    }

    public void Gerencia() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome de usuário: ");
        nomeUsuario = scanner.nextLine();
        this.nomeUsuario = nomeUsuario;
        File arquivo = new File(nomeUsuario + ".txt");

        if (arquivo.exists()) {
            System.out.println("Esse usuário existe!");
            autenticarUsuario(arquivo);

        } else {
            criarUsuario(nomeUsuario);
        }
    }

    public void autenticarUsuario(File arquivo) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a senha: ");
        String senhaDigitada = scanner.nextLine();

        // Verifica se o arquivo existe

        if (arquivo.exists()) {
            try {
                Scanner leitor = new Scanner(arquivo);
                String usuarioRegistrado = leitor.nextLine();
                String senhaRegistrada = leitor.nextLine();
                leitor.close();

                if (senhaDigitada.equals(senhaRegistrada.substring(7))) {
                    System.out.println("Usuário autenticado com sucesso!");
                    EscolhasOpc();

                } else {
                    System.out.println("Senha incorreta.");
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo.");
            }
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    public void EscolhasOpc() {
        GerenciadorTarefas gerenciador = new GerenciadorTarefas();
        gerenciador.carregarTarefasDoArquivo(nomeUsuario);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Criar nova tarefa");
            System.out.println("2. Concluir tarefa");
            System.out.println("3. Exibir tarefas pendentes");
            System.out.println("4. Exibir tarefas concluídas");
            System.out.println("5. Buscar tarefas concluídas");
            System.out.println("6. Buscar por categoria das tarefas:");
            System.out.println("7. Buscar:");
            System.out.println("0. Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    // Solicita que o usuário informe o título e a descrição da tarefa
                    System.out.print("Digite o título da tarefa: ");
                    scanner.nextLine();
                    String titulo = scanner.nextLine();

                    System.out.print("Digite a descrição da tarefa: ");
                    String descricao = scanner.nextLine();

                    // Sobre a categoria de tarefas
                    System.out.print("Digite a categoria da tarefa: ");
                    String categoria = scanner.nextLine();

                    // Cria a nova tarefa
                    Tarefa novaTarefa = new Tarefa(titulo, descricao, LocalDate.now(), categoria);

                    // Verifica se deseja adicionar como subtarefa de outra tarefa
                    System.out.print("Deseja adicionar esta tarefa como subtarefa de outra tarefa? (s/n): ");
                    String opcaoSubtarefa = scanner.nextLine();

                    if (opcaoSubtarefa.equalsIgnoreCase("s")) {
                        System.out.print("Digite o título da tarefa pai: ");
                        String tituloTarefaPai = scanner.nextLine();
                        Tarefa tarefaPai = gerenciador.buscarTarefaPorTitulo(tituloTarefaPai);

                        if (tarefaPai != null) {
                            tarefaPai.adicionarSubtarefa(novaTarefa);
                            System.out.println("Tarefa adicionada como subtarefa com sucesso!");
                        } else {
                            System.out.println(
                                    "Tarefa pai não encontrada. A tarefa será adicionada como tarefa independente.");
                        }
                    } else {
                        gerenciador.adicionarTarefa(novaTarefa, null);
                    }

                    System.out.println("\nTarefa criada com sucesso!");
                    break;

                case 2:
                    System.out.println("\n===== TAREFAS PENDENTES =====");
                    gerenciador.exibirTarefasPendentes();

                    // Concluir a tarefa e mover para a lista de tarefas concluídas
                    System.out.print("\nDigite o número da tarefa que deseja concluir: ");
                    int indiceTarefa = scanner.nextInt();

                    if (gerenciador.concluirTarefa(indiceTarefa, nomeUsuario)) {

                        System.out.println("Tarefa concluída com sucesso!");
                        gerenciador.adicionarTarefaArquivo(nomeUsuario);
                    } else {
                        System.out.println("Não foi possível concluir a tarefa.");
                    }
                    break;

                case 3:
                    System.out.println("\n===== TAREFAS PENDENTES =====");
                    gerenciador.exibirTarefasPendentes();
                    break;

                case 4:
                    System.out.println("\n===== TAREFAS CONCLUÍDAS =====");
                    gerenciador.exibirTarefasConcluidas();
                    break;

                case 5:
                    System.out.println("\n===== BUSCAR TAREFAS CONCLUÍDAS =====");
                    buscarTarefasConcluidas();
                    break;

                case 6:
                    System.out.println("\n===== BUSCAR TAREFAS PELA CATEGORIA SELECIONADA =====");
                    scanner.nextLine(); // Limpar o buffer
                    System.out.print("Digite a categoria da tarefa que deseja buscar: ");
                    String categoriaSelecionada = scanner.nextLine();
                    gerenciador.buscarTarefasPorCategoria(categoriaSelecionada);
                    break;

                case 7:
                    System.out.println("\n===== BUSCAR TAREFA POR TÍTULO =====");
                    scanner.nextLine(); // Limpar o buffer
                    System.out.print("Digite o título da tarefa que deseja buscar: ");
                    String buscaTitulo = scanner.nextLine();
                    Tarefa tarefaEncontrada = gerenciador.buscarTarefaPorTitulo(buscaTitulo);
                    if (tarefaEncontrada != null) {
                        System.out.println("Tarefa encontrada:");
                        System.out.println("- Título: " + tarefaEncontrada.getTitulo());
                        System.out.println("- Descrição: " + tarefaEncontrada.getDescricao());
                        System.out.println("- Categoria: " + tarefaEncontrada.getCategoria());
                    } else {
                        System.out.println("Tarefa não encontrada.");
                    }
                    break;

                case 0:
                    System.out.println("\nSaindo do programa...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("\nOpção inválida. Digite novamente.");
                    break;
            }
        }
    }

    private void exibirConteudoArquivo(File arquivo) {
        try {
            Scanner leitor = new Scanner(arquivo);
            // Descarta as duas primeiras linhas
            leitor.nextLine();
            leitor.nextLine();
            // Exibe o conteúdo restante do arquivo
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                System.out.println(linha);
            }
            leitor.close();
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }

    public void buscarTarefasConcluidas() {
        Scanner scanner = new Scanner(System.in);
        GerenciadorTarefas gerenciador = new GerenciadorTarefas();
        System.out.print("Digite uma palavra-chave para buscar nas tarefas concluídas: ");
        String palavraChave = scanner.nextLine();
        List<Tarefa> tarefasEncontradas = gerenciador.buscarTarefasConcluidas(palavraChave);
        if (tarefasEncontradas.isEmpty()) {
            System.out.println("Nenhuma tarefa concluída encontrada com a palavra-chave: " + palavraChave);
        } else {
            System.out.println("Tarefas concluídas encontradas com a palavra-chave: " + palavraChave);
            for (Tarefa tarefa : tarefasEncontradas) {
                System.out.println("- " + tarefa.getTitulo() + " - " + tarefa.getDescricao());
            }
        }
    }
}
