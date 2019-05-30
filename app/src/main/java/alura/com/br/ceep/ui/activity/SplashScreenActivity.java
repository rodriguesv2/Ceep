package alura.com.br.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import alura.com.br.ceep.R;

public class SplashScreenActivity extends Activity {

    public static final String CHAVE_SPLASH = "splash";
    private int tempo = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        verificaSePrimeiroAcesso(); //Se for o primeiro acesso, muda o tempo ṕara 2000
        executaThread();
    }

    private void verificaSePrimeiroAcesso() {
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        if (!preferences.contains(CHAVE_SPLASH)){
            tempo = 2000;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(CHAVE_SPLASH, "primeiro acesso realizado");
            editor.apply();
        }
    }

    private void executaThread() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, ListaNotasActivity.class));
                finish();
            }
        }, tempo);
    }
}
