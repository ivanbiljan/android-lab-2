package android.tvz.hr.listabiljan.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "car")
data class Car(
    @PrimaryKey val id: Int,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="make") val make: String,
    @ColumnInfo(name="model") val model: String,
    @ColumnInfo(name="yearOfProduction") val yearOfProduction: Int,
    @ColumnInfo(name="imageUrl") val imageUrl: String,
    @ColumnInfo(name="price") val price: Int,
    @ColumnInfo(name="mileage") val mileage: Int,
    @ColumnInfo(name="isUsed") val used: Boolean
) : Parcelable