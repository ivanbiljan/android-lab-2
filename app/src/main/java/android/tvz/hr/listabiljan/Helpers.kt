package android.tvz.hr.listabiljan

import java.text.NumberFormat

class Helpers {
    companion object {
        /**
         * Formats amount in string to human-readable amount (separated with commas
         * & prepends currency symbol)
         *
         * @param amount The amount to format in String
         * @return The formatted amount complete with separators & currency symbol added
         */
        fun formatCurrency(amount: Number): String? {
            var formattedAmount = "N/A"
            formattedAmount = try {
                val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance()
                numberFormat.setMaximumFractionDigits(2)
                numberFormat.setMinimumFractionDigits(2)
                numberFormat.format(amount)
            } catch (exception: Exception) {
                exception.printStackTrace()
                return formattedAmount
            }

            return formattedAmount
        }
    }
}