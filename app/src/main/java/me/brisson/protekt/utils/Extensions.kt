package me.brisson.protekt.utils

fun Char.isNumeric(): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return this.toString().matches(regex)
}