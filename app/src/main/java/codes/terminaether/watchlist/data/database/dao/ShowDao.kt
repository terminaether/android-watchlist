package codes.terminaether.watchlist.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import codes.terminaether.watchlist.data.model.Show

/**
 * Provides access to and methods for [Show] database.
 *
 * Created by terminaether on 2020-01-16.
 */
@Dao
interface ShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShow(show: Show)

}