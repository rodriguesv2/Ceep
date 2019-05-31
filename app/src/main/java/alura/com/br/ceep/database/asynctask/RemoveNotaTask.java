package alura.com.br.ceep.database.asynctask;

import android.os.AsyncTask;

import alura.com.br.ceep.database.dao.NotaDAO;
import alura.com.br.ceep.model.Nota;

public class RemoveNotaTask extends AsyncTask<Void, Void, Void> {

    private final NotaDAO dao;
    private final int posicao;
    private final OnPostExecuteListener listener;

    public RemoveNotaTask(NotaDAO dao, int posicao, OnPostExecuteListener listener) {
        this.dao = dao;
        this.posicao = posicao;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Nota nota = dao.pegaNotaPelaPosicao(posicao);
        dao.remove(nota);
        return null;
    }


    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);
        listener.posThread();
    }

    public interface OnPostExecuteListener{
        void posThread();
    }
}
