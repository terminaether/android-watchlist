package codes.terminaether.watchlist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import codes.terminaether.watchlist.data.database.dao.MovieDao
import codes.terminaether.watchlist.data.database.dao.ShowDao
import codes.terminaether.watchlist.data.model.Movie
import codes.terminaether.watchlist.data.model.Show
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Abstract database class which provides a singleton connection this app's SQL Lite database.
 *
 * Created by terminaether on 2020-01-15.
 */
@Database(entities = [Movie::class, Show::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    //TODO (Refactor): Single MediaDao
    abstract fun movieDao(): MovieDao
    abstract fun showDao(): ShowDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private var NUMBER_OF_THREADS = 4

        var DATABASE_WRITER: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

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