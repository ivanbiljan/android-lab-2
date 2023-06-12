package android.tvz.hr.listabiljan.fragments

import android.tvz.hr.listabiljan.infrastructure.models.Car
import CarAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.tvz.hr.listabiljan.*
import android.tvz.hr.listabiljan.activities.CarDetailsActivity
import android.tvz.hr.listabiljan.activities.ImageActivity
import android.tvz.hr.listabiljan.databinding.DetailsFragmentBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var carAdapter: CarAdapter
    private val _carList = MutableLiveData<List<Car>>()
    private lateinit var clickListener: OnCarClickListener;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)

        val car = arguments?.getParcelable<Car>(CarDetailsActivity.PARCELABLE_EXTRA_KEY)

        car?.let {
            binding.name.text = "${it.name} (${Helpers.formatCurrency(it.price)})"
            Glide.with(this)
                .load(car.imageUrl)
                .placeholder(R.drawable.ic_car_placeholder)
                .into(binding.image)
            binding.yearOfProduction.text = it.yearOfProduction.toString()
            binding.model.text = it.model
            binding.make.text = it.make
            binding.condition.text = when (it.used) {
                true -> "Used"
                false -> "New"
            }
            binding.mileage.text = it.mileage.toString()
        }

        binding.image.setOnClickListener {
            val intent = Intent(requireActivity(), ImageActivity::class.java)
            intent.putExtra(ImageActivityExtra, car?.imageUrl)
            startActivity(intent)
        }

        binding.websiteButton.setOnClickListener {
            val url = "https://www.tvz.hr"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

