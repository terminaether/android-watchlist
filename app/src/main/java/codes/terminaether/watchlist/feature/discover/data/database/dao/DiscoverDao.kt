package codes.terminaether.watchlist.feature.discover.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codes.terminaether.watchlist.data.model.Media

/**
 * Access object for the Discover table of the App's database.
 *
 * Created by terminaether on 2020-01-28.
 */
@Dao
interface DiscoverDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiscoverResults(media: List<Media>)

    @Query("SELECT * FROM Media WHERE title != null")
    fun getDiscoverMovieResults(): List<Media>

    @Query("SELECT * FROM Media WHERE name != null")
    fun getDiscoverShowResults(): List<Media>

    @Query("SELECT id FROM Media")
    fun getDiscoverMediaIds(): List<Int>

}