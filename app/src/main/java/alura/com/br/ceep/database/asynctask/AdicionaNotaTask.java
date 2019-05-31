package alura.com.br.ceep.database.asynctask;

import android.os.AsyncTask;

import alura.com.br.ceep.database.dao.NotaDAO;
import alura.com.br.ceep.model.Nota;

public class AdicionaNotaTask extends AsyncTask<Void, Void, Long> {

    private final NotaDAO dao;
    private final Nota notaRecebida;
    private final OnPostExecuteListener listener;

    public AdicionaNotaTask(NotaDAO dao, Nota notaRecebida, OnPostExecuteListener listener) {
        this.dao = dao;
        this.notaRecebida = notaRecebida;
        this.listener = listener;
    }

    @Override
    protected Long doInBackground(Void... voids) {
        return dao.insere(notaRecebida);
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        listener.posThread(aLong);
    }

    public interface OnPostExecuteListener{
        void posThread(long id);
    }
}
