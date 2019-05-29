package alura.com.br.ceep.model;

import java.io.Serializable;

import alura.com.br.ceep.ui.recyclerview.adapter.util.Cor;

public class Nota implements Serializable {

    private String titulo;
    private String descricao;
    private Cor cor;

    public Nota(String titulo, String descricao, Cor cor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.cor = cor;
    }

    public Nota(){}

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }
}