package alura.com.br.ceep.database.asynctask;

import android.os.AsyncTask;

import alura.com.br.ceep.database.dao.NotaDAO;
import alura.com.br.ceep.model.Nota;

public class BuscaNotaTask extends AsyncTask<Void, Void, Nota> {

    private final NotaDAO dao;
    private final Nota nota;
    private OnPostExecuteListener listener;

    public BuscaNotaTask(NotaDAO dao, Nota nota, OnPostExecuteListener listener) {
        this.dao = dao;
        this.nota = nota;
        this.listener = listener;
    }

    @Override
    protected Nota doInBackground(Void... voids) {
        return dao.pegaNota(nota.getId());
    }

    @Override
    protected void onPostExecute(Nota nota) {
        super.onPostExecute(nota);
        listener.posThread(nota);
    }

    public interface OnPostExecuteListener{
        void posThread(Nota notaPersistida);
    }
}
