package alura.com.br.ceep.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import alura.com.br.ceep.database.converter.ConversorCor;
import alura.com.br.ceep.database.dao.NotaDAO;
import alura.com.br.ceep.model.Nota;

@Database(entities = {Nota.class}, version = 1, exportSchema = false)
@TypeConverters({ConversorCor.class})
public abstract class CeepDatabase extends RoomDatabase {

    public abstract NotaDAO getNotaDao();

    public static CeepDatabase getInstance(Context context){
        return Room
                .databaseBuilder(context, CeepDatabase.class, "ceep.db")
                .build();
    }

}
