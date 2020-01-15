package codes.terminaether.watchlist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import codes.terminaether.watchlist.data.database.dao.GenreDao
import codes.terminaether.watchlist.data.model.Genre

/**
 * Abstract database class which provides a singleton connection this app's SQL Lite database.
 *
 * Created by terminaether on 2020-01-15.
 */
@Database(entities = [Genre::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun genreDao(): GenreDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "Watchlist Database"
                    )
                    .build()
            }

            return INSTANCE!!
        }
    }

}