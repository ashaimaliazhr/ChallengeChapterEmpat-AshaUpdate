package asha.binar.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Status (
    @PrimaryKey(autoGenerate = true)
    var id : Int?,
    var title : String,
    var content : String

) : Serializable