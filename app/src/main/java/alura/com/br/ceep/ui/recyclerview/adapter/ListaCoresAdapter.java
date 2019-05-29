package alura.com.br.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import alura.com.br.ceep.R;
import alura.com.br.ceep.ui.recyclerview.adapter.util.Cor;

public class ListaCoresAdapter extends RecyclerView.Adapter<ListaCoresAdapter.CorViewHolder> {

    private final List<Cor> cores;
    private final Context context;

    public ListaCoresAdapter(Context context) {
        this.context = context;
        this.cores = Cor.cores();
    }

    @NonNull
    @Override
    public CorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.botao_cor, viewGroup, false);
        return new CorViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull CorViewHolder corViewHolder, int position) {
        corViewHolder.mudaCor(cores.get(position));
    }

    @Override
    public int getItemCount() {
        return cores.size();
    }

    class CorViewHolder extends RecyclerView.ViewHolder{

        private final View circulo;

        public CorViewHolder(@NonNull View itemView) {
            super(itemView);
            circulo = itemView.findViewById(R.id.botao_cor_circulo);
        }

        public void mudaCor(Cor cor){
            circulo.getBackground().setColorFilter(
                    Color.parseColor(cor.getCorSelecionada()),
                        PorterDuff.Mode.MULTIPLY);
        }
    }
}
