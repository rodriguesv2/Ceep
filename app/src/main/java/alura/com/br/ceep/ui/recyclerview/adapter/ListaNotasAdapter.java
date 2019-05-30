package alura.com.br.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alura.com.br.ceep.R;
import alura.com.br.ceep.database.CeepDatabase;
import alura.com.br.ceep.database.dao.NotaDAO;
import alura.com.br.ceep.model.Cor;
import alura.com.br.ceep.model.Nota;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private List<Nota> notas;
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private NotaDAO dao;

    public ListaNotasAdapter(Context context, List<Nota> notas) {
        this.notas = notas;
        this.context = context;

        dao = CeepDatabase.getInstance(context).getNotaDao();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, viewGroup, false);

        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder viewHolder, int position) {
        Nota nota = notas.get(position);
        viewHolder.mudarCor(nota.getCor());
        viewHolder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void adiciona(Nota nota){
        notas.add(0, nota);
        atualizarPosicoesAoAdicionar();
        notifyDataSetChanged();
    }

    private void atualizarPosicoesAoAdicionar(){
        for (int i = 0; i < notas.size(); i++) {
            Nota nota = notas.get(i);
            nota.setPosicao(i);
            dao.edita(nota);
        }
    }

    public void altera(Nota nota) {
        dao.edita(nota);
        notas.set(nota.getPosicao(), nota);
        notifyDataSetChanged();
    }

    public void remove(Nota nota) {
        int posicao = nota.getPosicao();

        notas.remove(posicao);
        dao.remove(nota);
        atualizaPosicoesAoRemover(posicao);
        notifyItemRemoved(posicao);
    }

    private void atualizaPosicoesAoRemover(int posicao){
        for (int i = 0; i < notas.size(); i++) {
            Nota nota = notas.get(i);
            if (nota.getPosicao() > posicao){
                nota.setPosicao(nota.getPosicao()-1);
                dao.edita(nota);
            }
        }
    }

    public void troca(RecyclerView.ViewHolder viewHolderInicial, RecyclerView.ViewHolder viewHolderFinal) {
        int posicaoInicial = viewHolderInicial.getAdapterPosition();
        int posicaoFinal = viewHolderFinal.getAdapterPosition();

        notas = trocaNoBanco(posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    private List<Nota> trocaNoBanco(int posicaoInicial, int posicaoFinal){
        Nota notaInicial = dao.pegaNotaPelaPosicao(posicaoInicial);
        Nota notaFinal = dao.pegaNotaPelaPosicao(posicaoFinal);

        notaInicial.setPosicao(posicaoFinal);
        notaFinal.setPosicao(posicaoInicial);

        dao.edita(notaInicial);
        dao.edita(notaFinal);

        return dao.todos();
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private Nota nota;
        private final ConstraintLayout fundo;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
            fundo = itemView.findViewById(R.id.item_nota_fundo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Nota notaPersistida = dao.pegaNota((int) nota.getId());
                    onItemClickListener.onItemClick(notaPersistida);
                }
            });
        }

        public void vincula(Nota nota){
            this.nota = nota;
            preencheCampo(nota);
        }

        public void mudarCor(Cor cor){
            fundo.setBackgroundColor(Color.parseColor(cor.getCorSelecionada()));
        }

        private void preencheCampo(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }
    }

    public interface OnItemClickListener {

        void onItemClick(Nota nota);
    }
}
