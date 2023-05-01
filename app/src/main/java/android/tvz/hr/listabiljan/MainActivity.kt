package android.tvz.hr.listabiljan

import Car
import CarAdapter
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.tvz.hr.listabiljan.databinding.ActivityMainBinding
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var carAdapter: CarAdapter

    private val _carList = MutableLiveData<List<Car>>()

    init {
        val cars = mutableListOf<Car>()
        cars.add(Car(1, "Toyota Corolla Garbage", "Toyota", "Corolla", 2000,"https://generatorfun.com/code/uploads/Random-Car-image-12221124.jpg", 2500, 100000, true))
        cars.add(Car(1, "Honda Civic Type R", "Honda", "Civic", 2008, "https://generatorfun.com/code/uploads/Random-Car-image-19.jpg", 5000, 2000, true))
        cars.add(Car(1, "BMW M5 CS 2022", "BMW", "M5", 2022, "https://generatorfun.com/code/uploads/Random-Car-image-12.jpg", 100000, 0, false))
        cars.add(Car(1, "Ford Mustang BRMMM BRMM", "Ford", "Mustang", 1980, "https://generatorfun.com/code/uploads/Random-Car-image-17.jpg", 2500, 100000, true))
        cars.add(Car(1, "Mercedes Benz C-Class", "Mercedes", "Model", 2020, "https://generatorfun.com/code/uploads/Random-Car-image-2.jpg", 50000, 0, false))
        cars.add(Car(1, "Toyota Corolla Garbage", "Toyota", "Corolla", 2000,"https://generatorfun.com/code/uploads/Random-Car-image-12221124.jpg", 2500, 100000, true))
        cars.add(Car(1, "Honda Civic Type R", "Honda", "Civic", 2008, "https://generatorfun.com/code/uploads/Random-Car-image-19.jpg", 5000, 2000, true))
        cars.add(Car(1, "BMW M5 CS 2022", "BMW", "M5", 2022, "https://generatorfun.com/code/uploads/Random-Car-image-12.jpg", 100000, 0, false))
        cars.add(Car(1, "Ford Mustang BRMMM BRMM", "Ford", "Mustang", 1980, "https://generatorfun.com/code/uploads/Random-Car-image-17.jpg", 2500, 100000, true))
        cars.add(Car(1, "Mercedes Benz C-Class", "Mercedes", "Model", 2020, "https://generatorfun.com/code/uploads/Random-Car-image-2.jpg", 50000, 0, false))
        _carList.value = cars
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val carList = _carList.value ?: emptyList()

        carAdapter = CarAdapter(carList) { selectedCar: Car ->
            val intent = Intent(this, CarDetailsActivity::class.java)
            intent.putExtra(CarDetailsActivity.PARCELABLE_EXTRA_KEY, selectedCar)

            val detailsActivityOptions = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent, detailsActivityOptions.toBundle())
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = carAdapter
        }

        applicationContext.registerReceiver(ShareReceiver(), IntentFilter("android.tvz.hr.listabiljan.BROADCAST"))
    }
}