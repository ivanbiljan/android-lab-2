package android.tvz.hr.listabiljan.infrastructure.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val id: Int,
    val name: String,
    val make: String,
    val model: String,
    val yearOfProduction: Int,
    val imageUrl: String,
    val price: Int,
    val mileage: Int,
    val used: Boolean
) : Parcelable