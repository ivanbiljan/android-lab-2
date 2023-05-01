import android.net.Uri
import android.tvz.hr.listabiljan.Helpers
import android.tvz.hr.listabiljan.R
import android.tvz.hr.listabiljan.databinding.CarRecyclerCardItemBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CarAdapter(private val carList: List<Car>, private val listener: (car: Car) -> Unit) :
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_recycler_card_item, parent, false)

        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = carList[position]
        holder.bind(car)
    }

    override fun getItemCount() = carList.size

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private lateinit var car: Car
        private val binding = CarRecyclerCardItemBinding.bind(itemView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(car: Car) {
            this.car = car;

            binding.carName.text = car.name
            binding.carMileage.text = when (car.used) {
                true -> "Used, ${car.mileage} km"
                false -> "Unused"
            }

            binding.carPrice.text = Helpers.formatCurrency(car.price)
            Glide.with(itemView.context)
                .load(car.imageUrl)
                .placeholder(R.drawable.ic_car_placeholder)
                .into(binding.carImage)
            binding.carYear.text = car.yearOfProduction.toString()
        }

        override fun onClick(view: View?) {
            if (view != null) {
                listener.invoke(car)
            }
        }
    }
}