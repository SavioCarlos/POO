import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GerenciadorTarefas {
    private List<Tarefa> tarefasPendentes;
    private List<Tarefa> tarefasConcluidas;
    Scanner scanner = new Scanner(System.in);

    public GerenciadorTarefas() {
        this.tarefasPendentes = new ArrayList<>();
        this.tarefasConcluidas = new ArrayList<>();
    }

    // métodos de acesso às listas de tarefas
    public List<Tarefa> getTarefasPendentes() {
        return tarefasPendentes;
    }

    public List<Tarefa> getTarefasConcluidas() {
        return tarefasConcluidas;
    }

    //public void adicionarTarefa(Tarefa tarefa) {
        //tarefasPendentes.add(tarefa);
    //}
    
    public void adicionarTarefa(Tarefa tarefa, Tarefa tarefaPai) {
      if (tarefaPai != null) {
         tarefaPai.adicionarSubtarefa(tarefa);
      } else {
         tarefasPendentes.add(tarefa);
      }
    }

    public boolean concluirTarefa(String nomeUsuario, int indice) {
      if (indice < 1 || indice > tarefasPendentes.size()) {
         return false;
      }
    
      Tarefa tarefa = tarefasPendentes.get(indice - 1);
      tarefa.setConcluida(true);
      tarefa.setDataConclusao(LocalDate.now());
      tarefasConcluidas.add(tarefa);
    
      // Verificar e concluir as subtarefas
      if (!tarefa.getSubtarefas().isEmpty()) {
         for (Tarefa subtarefa : tarefa.getSubtarefas()) {
            subtarefa.setConcluida(true);
            subtarefa.setDataConclusao(LocalDate.now());
            tarefasConcluidas.add(subtarefa);
        }
      }
    
      tarefasPendentes.remove(tarefa);
      return true;
    }

    
    public void adicionarTarefaArquivo() {
        System.out.println("Digite em qual usuario vc quer salvar a tarefa : ");
        String nomeUsuario = scanner.nextLine();

        try {
            File arquivo = new File(nomeUsuario + ".txt");
            FileWriter escritor = new FileWriter(arquivo, true);
            GerenciadorTarefas gerenciador = new GerenciadorTarefas();

            
            for (int i = 0; i < tarefasConcluidas.size(); i++) {
                Tarefa tarefa = tarefasConcluidas.get(i);
                String texto = (". " + tarefa.getTitulo() + " - " + tarefa.getDescricao());
                escritor.write(texto + "\n");

            }
            escritor.close();

            System.out.println("A Tarefa foi adicionada ao arquivo.");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public void exibirTarefasPendentes() {
        if (tarefasPendentes.isEmpty()) {
            System.out.println("Não há tarefas pendentes.");
        } else {
            for (int i = 0; i < tarefasPendentes.size(); i++) {
                Tarefa tarefa = tarefasPendentes.get(i);
                System.out.println((i + 1) + ". " + tarefa.getTitulo() + " - " + tarefa.getDescricao());
            }
        }
    }

    public void exibirTarefasConcluidas() {
        if (tarefasConcluidas.isEmpty()) {
            System.out.println("Não há tarefas concluídas.");
        } else {
            for (int i = 0; i < tarefasConcluidas.size(); i++) {
                Tarefa tarefa = tarefasConcluidas.get(i);
                System.out.println((i + 1) + ". " + tarefa.getTitulo() + " - " + tarefa.getDescricao());
            }
        }
    }

    public List<Tarefa> buscarTarefasConcluidas(String palavraChave) {
        List<Tarefa> tarefasEncontradas = new ArrayList<>();

        for (Tarefa tarefa : tarefasConcluidas) {
            if (tarefa.getTitulo().contains(palavraChave) || tarefa.getDescricao().contains(palavraChave)) {
                tarefasEncontradas.add(tarefa);
            }
        }

        return tarefasEncontradas;
    }

    // metodo de buscar tarefas pela categoria que o usuario digitar
    public void buscarTarefasPorCategoria(String categoria) {
        List<Tarefa> tarefasPorCategoriaEncontradas = new ArrayList<>();

        for (Tarefa tarefa : tarefasPendentes) {
            if (tarefa.getCategoria().equalsIgnoreCase(categoria)) {
                tarefasPorCategoriaEncontradas.add(tarefa);
            }
        }

        for (Tarefa tarefa : tarefasConcluidas) {
            if (tarefa.getCategoria().equalsIgnoreCase(categoria)) {
                tarefasPorCategoriaEncontradas.add(tarefa);
            }
        }

        if (tarefasPorCategoriaEncontradas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada para a categoria: " + categoria);
        } else {
            System.out.println("Tarefas encontradas para a categoria: " + categoria);
            for (Tarefa tarefa : tarefasPorCategoriaEncontradas) {
                System.out.println("- " + tarefa.getTitulo() + " - " + tarefa.getDescricao());
            }
        }
    }

    public Tarefa buscarTarefaPorTitulo(String titulo) {
        for (Tarefa tarefa : tarefasPendentes) {
            if (tarefa.getTitulo().equalsIgnoreCase(titulo)) {
                return tarefa;
            }
        }

        for (Tarefa tarefa : tarefasConcluidas) {
            if (tarefa.getTitulo().equalsIgnoreCase(titulo)) {
                return tarefa;
            }
        }

        return null; // Caso a tarefa não seja encontrada
    }
       
}
