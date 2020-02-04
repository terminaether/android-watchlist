package codes.terminaether.watchlist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import codes.terminaether.watchlist.data.database.dao.MediaDao
import codes.terminaether.watchlist.data.model.Media

/**
 * Abstract database class which provides a singleton connection to this app's SQL Lite database.
 *
 * Created by terminaether on 2020-01-15.
 */
@Database(entities = [Media::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mediaDao(): MediaDao

    companion object {
        private var INSTANCE: AppDatabase? = null

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