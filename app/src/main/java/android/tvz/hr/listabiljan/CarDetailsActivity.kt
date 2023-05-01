package android.tvz.hr.listabiljan

import Car
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.tvz.hr.listabiljan.databinding.ActivityCarDetailsBinding
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class CarDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val car = intent.getParcelableExtra<Car>(PARCELABLE_EXTRA_KEY)

        car?.let {
            supportActionBar?.title = it.name
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
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra(ImageActivityExtra, car?.imageUrl)
            startActivity(intent)
        }

        binding.websiteButton.setOnClickListener {
            val url = "https://www.tvz.hr"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.labos_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share -> showShareDialog()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun showShareDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to trigger the broadcast?")
            .setCancelable(true)
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                val intent = Intent("android.tvz.hr.listabiljan.BROADCAST")
                sendBroadcast(intent)
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        const val PARCELABLE_EXTRA_KEY = "car_key"
    }
}