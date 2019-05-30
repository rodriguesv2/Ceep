package alura.com.br.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import alura.com.br.ceep.R;
import alura.com.br.ceep.dao.NotaDAO;
import alura.com.br.ceep.database.CeepDatabase;
import alura.com.br.ceep.model.Nota;
import alura.com.br.ceep.preferences.RecycleViewLayoutPreferences;
import alura.com.br.ceep.preferences.TipoRecycleViewEnum;
import alura.com.br.ceep.recyclerview.helper.callback.NotaItemTouchHelperCallback;
import alura.com.br.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

import static alura.com.br.ceep.ui.activity.NotasActivityConstantes.CHAVE_NOTA;
import static alura.com.br.ceep.ui.activity.NotasActivityConstantes.CHAVE_POSICAO;
import static alura.com.br.ceep.ui.activity.NotasActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static alura.com.br.ceep.ui.activity.NotasActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static alura.com.br.ceep.ui.activity.NotasActivityConstantes.POSICAO_INVALIDA;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;
    private RecyclerView listaNotas;
    private alura.com.br.ceep.database.dao.NotaDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        CeepDatabase database = CeepDatabase.getInstance(this);
        dao = database.getNotaDao();

        setTitle("Notas");

        List<Nota> todasNotas = pegaTodasNotas();
        configuraRecyclerView(todasNotas);

        configuraBotaoInsereNota();
    }

    //Inicio - Configuração do item menu de seleção de tipo de RecycleView
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_notas_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_lista_notas_layout_item){
            if (!ehPreferenciaLinear()){
                setaLayoutLinear(item);
                RecycleViewLayoutPreferences.inserePreferencia(TipoRecycleViewEnum.LINEAR, this);
            } else {
                setaLayoutGrid(item);
                RecycleViewLayoutPreferences.inserePreferencia(TipoRecycleViewEnum.GRID, this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (RecycleViewLayoutPreferences.contemChave(this)){
            if (ehPreferenciaLinear()){
                setaLayoutLinear(menu.getItem(0));
            } else {
                setaLayoutGrid(menu.getItem(0));
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private boolean ehPreferenciaLinear() {
        return RecycleViewLayoutPreferences.pegaPreferencia(this) == TipoRecycleViewEnum.LINEAR;
    }

    private void setaLayoutLinear(MenuItem item) {
        listaNotas.setLayoutManager(new LinearLayoutManager(this));
        item.setIcon(R.drawable.ic_action_lista_grid);
    }

    private void setaLayoutGrid(MenuItem item) {
        listaNotas.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        item.setIcon(R.drawable.ic_action_lista_linear);
    }
    //Fim - Configuração do item menu de seleção de tipo de RecycleView


    //Inicio - Configuração do botão de inserir nota
    private void configuraBotaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaFormularioNotaActivityInsere();
            }
        });
    }

    private void vaiParaFormularioNotaActivityInsere() {
        Intent iniciaFormularioNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(iniciaFormularioNota, CODIGO_REQUISICAO_INSERE_NOTA);
    }
    //Fim - Configuração do botão de inserir nota


    private List<Nota> pegaTodasNotas() {
        return dao.todos();
    }


    //Inicio - Logica da nota retornada/criada pelo formulario
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(ehResultadoInsereNota(requestCode, data)){
            if (resultadoOk(resultCode)){
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                adiciona(notaRecebida);
            }
        }

        if(ehResultadoAlteraNota(requestCode, data)){
            if (resultadoOk(resultCode)){
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);

                if(ehPosicaoValida(posicaoRecebida)){
                    altera(notaRecebida, posicaoRecebida);
                } else {
                    Toast.makeText(this, "Ocorreu um problema na alteração da nota", Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean ehResultadoInsereNota(int requestCode,@Nullable Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) && temNota(data);
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) && temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private boolean temNota(@Nullable Intent data) {
        return data != null && data.hasExtra("nota");
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private void altera(Nota nota, int posicao) {
        new NotaDAO().altera(posicao, nota);
        adapter.altera(posicao, nota);
    }

    private void adiciona(Nota notaRecebida) {
        dao.insere(notaRecebida);
        new NotaDAO().insere(notaRecebida);
        adapter.adiciona(notaRecebida);
    }
    //Fim - Logica da nota retornada/criada pelo formulario


    //Inicio - Configuração do RecyclerView de notas
    private void configuraRecyclerView(List<Nota> todasNotas) {
        listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
        configuraItemTouchHelper(listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new ListaNotasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int posicao) {
                vaiParaFormularioNotaActivityAltera(nota, posicao);
            }
        });
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void vaiParaFormularioNotaActivityAltera(Nota nota, int posicao) {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }
    //Fim - Configuração do RecyclerView de notas
}
