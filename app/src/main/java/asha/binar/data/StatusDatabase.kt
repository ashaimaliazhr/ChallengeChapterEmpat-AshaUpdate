package asha.binar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Status::class], version = 1)
abstract class StatusDatabase : RoomDatabase() {

    abstract fun statusDao() : StatusDao

    companion object {
        private var INSTANCE: StatusDatabase? = null
        fun getInstance(requireContext: Context): StatusDatabase? {
            if (INSTANCE == null) {
                synchronized(StatusDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        requireContext.applicationContext,
                        StatusDatabase::class.java, "Status.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}
