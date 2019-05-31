package alura.com.br.ceep.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class RecycleViewLayoutPreferences {

    private static final String CHAVE_PREFERENCIA = "listaEscolhida";
    private static final String USER_PREFERENCES = "user_preferences";

    public static void inserePreferencia(TipoRecycleViewEnum valor, Context context) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(CHAVE_PREFERENCIA, valor.name());
        editor.apply();
    }

    public static boolean contemChave(Context context) {
        return getPreferences(context).contains(CHAVE_PREFERENCIA);
    }

    public static TipoRecycleViewEnum pegaPreferencia(Context context) {
        String tipoLista = getPreferences(context).getString(CHAVE_PREFERENCIA, TipoRecycleViewEnum.LINEAR.name());
        return TipoRecycleViewEnum.valueOf(tipoLista);
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }
}
