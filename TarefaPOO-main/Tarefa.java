import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tarefa {
    private String titulo;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataConclusao;
    private boolean concluida;
    private String categoria;
    private List<Tarefa> subtarefas;

    // construtor
    public Tarefa(String titulo, String descricao, LocalDate dataCriacao, String categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.concluida = false; // ao criar a tarefa, ela inicia como pendente
        this.categoria = categoria;
        this.subtarefas = new ArrayList<>();
    }

    // métodos de acesso e modificação
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public List<Tarefa> getSubtarefas() {
        return subtarefas;
    }

    public void setSubtarefas(List<Tarefa> subtarefas) {
        this.subtarefas = subtarefas;
    }
    
    //metodo para adicionar subtarefas 
    public void adicionarSubtarefa(Tarefa subtarefa) {
      subtarefas.add(subtarefa);
    }

    
}