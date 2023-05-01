import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val id: Int,
    val name: String,
    val make: String,
    val model: String,
    val yearOfProduction: Number,
    val imageUrl: String,
    val price: Number,
    val mileage: Number,
    val used: Boolean
) : Parcelable