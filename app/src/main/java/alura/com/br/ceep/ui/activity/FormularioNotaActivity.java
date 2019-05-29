package alura.com.br.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import alura.com.br.ceep.R;
import alura.com.br.ceep.model.Nota;
import alura.com.br.ceep.ui.recyclerview.adapter.ListaCoresAdapter;
import alura.com.br.ceep.ui.recyclerview.adapter.util.Cor;

import static alura.com.br.ceep.ui.activity.NotasActivityConstantes.CHAVE_NOTA;
import static alura.com.br.ceep.ui.activity.NotasActivityConstantes.CHAVE_POSICAO;
import static alura.com.br.ceep.ui.activity.NotasActivityConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {

    private int posicaoRecebida = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descricao;
    private RecyclerView selecaoDeCor;
    private ConstraintLayout fundo;
    private Nota notaRecebida;
    private Cor cor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        setTitle("Insere nota");

        inicializaCampos();
        configuraRecyclerView();

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)){
            setTitle("Altera nota");
            notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);

            Cor corDaNotaRecebida = notaRecebida.getCor();
            if (corDaNotaRecebida.getCorSelecionada() != Cor.BRANCO){
                mudarCorDeFundo(corDaNotaRecebida);
                this.cor = corDaNotaRecebida;
            } else {
                this.cor = new Cor(Cor.BRANCO);
            }

            preencheCampos();
        } else {
            notaRecebida = new Nota();
            cor = new Cor(Cor.BRANCO);
        }
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
        selecaoDeCor = findViewById(R.id.formulario_nota_selecao_cor);
        fundo = findViewById(R.id.formulario_nota_fundo);
    }

    private void configuraRecyclerView() {
        ListaCoresAdapter adapter = new ListaCoresAdapter(this);
        selecaoDeCor.setAdapter(adapter);

        acaoDeCliqueNosBotoesDeCor(adapter);
    }

    private void acaoDeCliqueNosBotoesDeCor(ListaCoresAdapter adapter) {
        adapter.setOnItemClickListener(new ListaCoresAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Cor corClicada) {
                cor = corClicada;
                mudarCorDeFundo(cor);
            }
        });
    }

    private void mudarCorDeFundo(Cor cor) {
        fundo.setBackgroundColor(Color.parseColor(cor.getCorSelecionada()));
    }

    private void preencheCampos() {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(ehMenuSalvaNota(item)){
            Nota notaCriada = criaNota();
            retornaNota(notaCriada);
            Toast.makeText(this, notaCriada.getCor().getCorSelecionada(), Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }

    private Nota criaNota() {
        return new Nota(titulo.getText().toString(), descricao.getText().toString(), cor);
    }

    private void retornaNota(Nota nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecebida);
        setResult(Activity.RESULT_OK, resultadoInsercao);
    }
}
