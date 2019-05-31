package alura.com.br.ceep.database.asynctask;

import android.os.AsyncTask;

import java.util.List;

import alura.com.br.ceep.database.dao.NotaDAO;
import alura.com.br.ceep.model.Nota;

public class BuscaTodasNotasTask extends AsyncTask<Void, Void, List<Nota>> {

    private final NotaDAO dao;
    private final OnPostExecuteListener listener;

    public BuscaTodasNotasTask(NotaDAO dao, OnPostExecuteListener listener) {
        this.dao = dao;
        this.listener = listener;
    }

    @Override
    protected List<Nota> doInBackground(Void... voids) {
        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Nota> notas) {
        super.onPostExecute(notas);
        listener.posThread(notas);
    }

    public interface OnPostExecuteListener{
        void posThread(List<Nota> notas);
    }
}
