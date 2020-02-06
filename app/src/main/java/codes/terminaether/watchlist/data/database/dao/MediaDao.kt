package codes.terminaether.watchlist.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import codes.terminaether.watchlist.data.model.Media

/**
 * Access object for the [Media] table of the App's database.
 *
 * Created by terminaether on 2020-01-27.
 */
@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(media: List<Media>): List<Long>

    @get:Query("SELECT * FROM Media WHERE title NOT null ORDER BY popularity DESC")
    val popularMoviesList: LiveData<List<Media>>

    @get:Query("SELECT * FROM Media WHERE name NOT null ORDER BY popularity DESC")
    val popularShowsList: LiveData<List<Media>>

    @get:Query("SELECT id FROM Media WHERE isFavourite = 1")
    val favouriteIds: List<Int>

    @get:Query("SELECT * FROM Media WHERE isFavourite = 1")
    val favouritesList: LiveData<List<Media>>

    //TODO (Database): Find a way of simplifying this method
    @Transaction
    suspend fun upsertAll(media: List<Media>) {
        val insertResults = insertAll(media)
        val updateList: ArrayList<Media> = arrayListOf()

        //Add Media items that already exist in the database to the update list
        val insertResultsIterator = insertResults.iterator().withIndex()
        insertResultsIterator.forEach { rowId ->
            if (rowId.value == -1L) updateList.add(media[rowId.index])
        }

        //Mark existing Media items that are saved before updating
        val updateListIterator = updateList.iterator()
        updateListIterator.forEach { media ->
            if (favouriteIds.contains(media.id)) media.isFavourite = true
        }

        if (!updateList.isNullOrEmpty()) {
            updateAll(updateList)
        }
    }

    @Update
    suspend fun update(media: Media)

    @Update
    suspend fun updateAll(media: List<Media>)

}