package alura.com.br.ceep.database.converter;

import android.arch.persistence.room.TypeConverter;

import alura.com.br.ceep.model.Cor;

public class ConversorCor {

    @TypeConverter
    public String paraString(Cor cor){
        if (cor != null)
            return cor.getCorSelecionada();
        return null;
    }

    @TypeConverter
    public Cor paraCor(String corHexa){
        if (corHexa != null)
            return new Cor(corHexa);
        return null;
    }
}
