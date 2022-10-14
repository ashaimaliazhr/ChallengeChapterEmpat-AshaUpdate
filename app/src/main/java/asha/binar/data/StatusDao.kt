package asha.binar.data

import androidx.room.*

@Dao
interface StatusDao {

    @Insert
    fun insertNote(status: Status)

    @Query("SELECT * FROM Status ORDER BY id DESC")
    fun getDataNote(): List<Status>

    @Delete
    fun deleteNote(note: Status)

    @Update
    fun updateNote(note: Status)
}