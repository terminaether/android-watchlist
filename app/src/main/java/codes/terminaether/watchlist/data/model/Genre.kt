package codes.terminaether.watchlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A simple data class representing a Genre for a [Movie] or [Show].
 * @see [Genres](https://developers.themoviedb.org/3/genres).
 *
 * Created by terminaether on 2020-01-14.
 */
@Entity
data class Genre(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String
)