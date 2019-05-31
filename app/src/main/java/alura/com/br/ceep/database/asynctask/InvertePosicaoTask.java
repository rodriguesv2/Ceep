package alura.com.br.ceep.database.asynctask;

import android.os.AsyncTask;

import java.util.List;

import alura.com.br.ceep.database.dao.NotaDAO;
import alura.com.br.ceep.model.Nota;

public class InvertePosicaoTask extends AsyncTask<Void, Void, List<Nota>> {
    private final NotaDAO dao;
    private final int posicaoInicial;
    private final int posicaoFinal;
    private final OnPostExecuteListener listener;

    public InvertePosicaoTask(NotaDAO dao, int posicaoInicial, int posicaoFinal, OnPostExecuteListener listener) {
        this.dao = dao;
        this.posicaoInicial = posicaoInicial;
        this.posicaoFinal = posicaoFinal;
        this.listener = listener;
    }

    @Override
    protected List<Nota> doInBackground(Void... voids) {
        Nota notaInicial = dao.pegaNotaPelaPosicao(posicaoInicial);
        Nota notaFinal = dao.pegaNotaPelaPosicao(posicaoFinal);

        notaInicial.setPosicao(posicaoFinal);
        notaFinal.setPosicao(posicaoInicial);

        dao.edita(notaInicial);
        dao.edita(notaFinal);

        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Nota> notas) {
        super.onPostExecute(notas);
        listener.posThread(notas, posicaoInicial, posicaoFinal);
    }

    public interface OnPostExecuteListener{
        void posThread(List<Nota> notas, int posicaoInicial, int posicaoFinal);
    }
}
