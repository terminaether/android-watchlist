package codes.terminaether.watchlist.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Abstract database class which provides a singleton connection this app's SQL Lite database.
 *
 * Created by terminaether on 2020-01-15.
 */
//TODO (Code): Add @Database annotation
abstract class AppDatabase : RoomDatabase() {

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