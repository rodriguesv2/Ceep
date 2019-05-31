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
import alura.com.br.ceep.model.Cor;

public class ListaCoresAdapter extends RecyclerView.Adapter<ListaCoresAdapter.CorViewHolder> {

    private final List<Cor> cores;
    private final Context context;

    private OnItemClickListener onItemClickListener;

    public ListaCoresAdapter(Context context) {
        this.context = context;
        this.cores = Cor.cores();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.botao_cor, viewGroup, false);
        return new CorViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull CorViewHolder corViewHolder, int position) {
        corViewHolder.setaCor(cores.get(position));
    }

    @Override
    public int getItemCount() {
        return cores.size();
    }

    class CorViewHolder extends RecyclerView.ViewHolder{

        private final View circulo;
        private Cor cor;

        CorViewHolder(@NonNull View itemView) {
            super(itemView);
            circulo = itemView.findViewById(R.id.botao_cor_circulo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(cor);
                }
            });
        }

        void setaCor(Cor cor){
            this.cor = cor;
            circulo.getBackground().setColorFilter(
                    Color.parseColor(cor.getCorSelecionada()),
                        PorterDuff.Mode.MULTIPLY);
        }
    }

    public interface OnItemClickListener{

        void onItemClick(Cor cor);
    }
}
