package android.tvz.hr.listabiljan

import android.tvz.hr.listabiljan.infrastructure.models.Car

interface OnCarClickListener {
    fun handle(car: Car)
}