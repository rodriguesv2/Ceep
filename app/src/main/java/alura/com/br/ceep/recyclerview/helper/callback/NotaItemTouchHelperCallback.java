package alura.com.br.ceep.recyclerview.helper.callback;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import alura.com.br.ceep.database.CeepDatabase;
import alura.com.br.ceep.database.dao.NotaDAO;
import alura.com.br.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ListaNotasAdapter adapter;
    private NotaDAO dao;

    public NotaItemTouchHelperCallback(ListaNotasAdapter adapter, Context context) {
        this.adapter = adapter;
        dao = CeepDatabase.getInstance(context).getNotaDao();
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int marcacoesDeArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolderInicial,
                          @NonNull RecyclerView.ViewHolder viewHolderFinal) {

        int posicaoInicial = viewHolderInicial.getAdapterPosition();
        int posicaoFinal = viewHolderFinal.getAdapterPosition();

        adapter.trocaNoBanco(posicaoInicial, posicaoFinal);
        return true;
    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int posicaoDaNotaDeslizada = viewHolder.getAdapterPosition();
        adapter.remove(posicaoDaNotaDeslizada);
    }
}
