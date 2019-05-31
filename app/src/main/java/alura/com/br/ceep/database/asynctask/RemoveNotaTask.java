package alura.com.br.ceep.database.asynctask;

import android.os.AsyncTask;

import alura.com.br.ceep.database.dao.NotaDAO;
import alura.com.br.ceep.model.Nota;

public class RemoveNotaTask extends AsyncTask<Void, Void, Nota> {

    private final NotaDAO dao;
    private final int posicao;
    private OnPostExecuteListener listener;

    public RemoveNotaTask(NotaDAO dao, int posicao, OnPostExecuteListener listener) {
        this.dao = dao;
        this.posicao = posicao;
        this.listener = listener;
    }

    @Override
    protected Nota doInBackground(Void... voids) {
        Nota nota = dao.pegaNotaPelaPosicao(posicao);
        dao.remove(nota);
        return nota;
    }

    @Override
    protected void onPostExecute(Nota nota) {
        super.onPostExecute(nota);
        listener.posThread(nota);
    }

    public interface OnPostExecuteListener{
        void posThread(Nota nota);
    }
}
