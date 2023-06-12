package android.tvz.hr.listabiljan.infrastructure.viewmodels

import android.tvz.hr.listabiljan.infrastructure.api.RetrofitHelper
import android.tvz.hr.listabiljan.infrastructure.models.Car
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    suspend fun getAllCars(): List<Car> {
        return RetrofitHelper.carsApi.getCars()
    }

    private val mutableSelectedItem = MutableLiveData<Car>()
    val selectedItem: LiveData<Car> get() = mutableSelectedItem

    fun selectItem(item: Car) {
        mutableSelectedItem.value = item
    }
}