package android.tvz.hr.listabiljan

import android.content.Context
import android.tvz.hr.listabiljan.models.Car
import androidx.room.*


@Database(entities = [Car::class], version = 2)
abstract class AppDb : RoomDatabase() {
    // below line is to create
    // abstract variable for dao.
    abstract fun Dao(): CarDao

    companion object {
        // below line is to create instance
        // for our database class.
        private var instance: AppDb? = null

        // on below line we are getting instance for our database.
        @Synchronized
        fun getInstance(context: Context): AppDb? {
            // below line is to check if
            // the instance is null or not.
            if (instance == null) {
                // if the instance is null we
                // are creating a new instance
                instance =  // for creating a instance for our database
                        // we are creating a database builder and passing
                        // our database class with our database name.
                    Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDb::class.java, "cars"
                    ) // below line is use to add fall back to
                        // destructive migration to our database.
                        // to our database.
                        // build our database.
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .createFromAsset("database/cars.db")
                        .build()
            }
            // after creating an instance
            // we are returning our instance
            return instance
        }
    }
}

@Dao
interface CarDao {
    @Query("SELECT * FROM car")
    fun getAll(): List<Car>

    @Query("SELECT * FROM car WHERE id = :id")
    fun getById(id: Int): Car

    @Insert
    fun insertAll(vararg cars: Car)

    @Delete
    fun delete(picture: Car)
}

