package com.example.imoviescompose.common

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Locale

object Extensions {
    fun String.convertToCurrency(amount: Double): String {
        val locale = Locale.getDefault()
        val currency = Currency.getInstance(this)

        val numberFormat = NumberFormat.getCurrencyInstance(locale)
        numberFormat.currency = currency

        return numberFormat.format(amount)
    }

    fun String.formatDate(from: String, to: String): String? {
        val inputDateFormat = SimpleDateFormat(from, Locale.US)
        val outputDateFormat = SimpleDateFormat(to, Locale.US)

        try {
            val date = inputDateFormat.parse(this)
            return date?.let { outputDateFormat.format(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}