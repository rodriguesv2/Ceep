package alura.com.br.ceep.database.asynctask;

import android.os.AsyncTask;

import alura.com.br.ceep.database.dao.NotaDAO;
import alura.com.br.ceep.model.Nota;

public class EditaNotaTask extends AsyncTask<Void, Void, Void> {

    private final NotaDAO dao;
    private final Nota nota;

    public EditaNotaTask(NotaDAO dao, Nota nota) {
        this.dao = dao;
        this.nota = nota;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.edita(nota);
        return null;
    }
}
