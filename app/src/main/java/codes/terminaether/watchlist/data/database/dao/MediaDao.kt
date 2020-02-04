package codes.terminaether.watchlist.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import codes.terminaether.watchlist.data.model.Media

/**
 * Access object for the Media table of the App's database.
 *
 * Created by terminaether on 2020-01-27.
 */
@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscoverResults(media: List<Media>)

    @get:Query("SELECT * FROM Media WHERE title NOT null ORDER BY popularity DESC")
    val discoverMovieResults: LiveData<List<Media>>

    @get:Query("SELECT * FROM Media WHERE name NOT null ORDER BY popularity DESC")
    val discoverShowResults: LiveData<List<Media>>

    @Update
    suspend fun updateMedia(media: Media)

}