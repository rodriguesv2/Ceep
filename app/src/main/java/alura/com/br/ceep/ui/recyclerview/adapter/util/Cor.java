package alura.com.br.ceep.ui.recyclerview.adapter.util;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public class Cor {

    private static final String AZUL = "#408EC9";
    private static final String BRANCO = "#FFFFFF";
    private static final String VERMELHO = "#EC2F4B";
    private static final String VERDE = "#9ACD32";
    private static final String AMARELO = "#F9F256";
    private static final String LILAS = "#F1CBFF";
    private static final String CINZA = "#D2D4DC";
    private static final String MARROM = "#A47C48";
    private static final String ROXO = "#BE29EC";

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
