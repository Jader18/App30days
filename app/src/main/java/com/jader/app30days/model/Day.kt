package com.jader.app30days.model
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Day(
    val dayId: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int
)