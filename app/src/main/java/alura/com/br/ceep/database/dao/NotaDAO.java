package alura.com.br.ceep.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import alura.com.br.ceep.model.Nota;

@Dao
public interface NotaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insere(Nota nota);

    @Delete
    void remove(Nota nota);

    @Update
    void edita(Nota nota);

    @Query("SELECT * FROM Nota ORDER BY posicao")
    List<Nota> todos();

    @Query("SELECT * FROM Nota WHERE id = :id")
    Nota pegaNota(int id);
}
