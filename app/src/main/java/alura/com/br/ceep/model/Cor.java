package alura.com.br.ceep.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Cor implements Serializable {

    public static final String AZUL = "#408EC9";
    public static final String BRANCO = "#FFFFFF";
    public static final String VERMELHO = "#EC2F4B";
    public static final String VERDE = "#9ACD32";
    public static final String AMARELO = "#F9F256";
    public static final String LILAS = "#F1CBFF";
    public static final String CINZA = "#D2D4DC";
    public static final String MARROM = "#A47C48";
    public static final String ROXO = "#BE29EC";

    private String corSelecionada;

    public Cor(String corSelecionada) {
        this.corSelecionada = corSelecionada;
    }

    public String getCorSelecionada() {
        return corSelecionada;
    }

    public static List<Cor> cores(){
        List<Cor> cores = Arrays.asList(
                new Cor(AZUL),
                new Cor(BRANCO),
                new Cor(VERMELHO),
                new Cor(VERDE),
                new Cor(AMARELO),
                new Cor(LILAS),
                new Cor(CINZA),
                new Cor(MARROM),
                new Cor(ROXO)
        );

        return cores;
    }
}
