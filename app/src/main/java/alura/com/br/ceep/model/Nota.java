package alura.com.br.ceep.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Nota implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String titulo;
    private String descricao;
    private Cor cor;
    private long posicao;

    public Nota(String titulo, String descricao, Cor cor, long posicao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.cor = cor;
        this.posicao = posicao;
    }

    public Nota(){}

    public long getPosicao() {
        return posicao;
    }

    public void setPosicao(long posicao) {
        this.posicao = posicao;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Cor getCor() {
        return cor;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }
}