package me.brisson.protekt.domain.model

data class Password(val value: String) {

    fun calculatePasswordSafety(): Float {
        val lower = this.value.contains(".*[a-z].*".toRegex())
        val upper = this.value.contains(".*[A-Z].*".toRegex())
        val numeric = this.value.contains(".*\\d.*".toRegex())
        val specialChar = this.value.contains(".*[-+_!@#\$%^&*., ?].*".toRegex())
        val hasAtLeast8Char = this.value.length >= 8

        listOf(lower, upper, numeric, specialChar, hasAtLeast8Char).let { conditionList ->
            val singleItemPercentage = 1f/conditionList.size
            val filteredListSize = conditionList.filter { it }.size
            return  filteredListSize * singleItemPercentage
        }
    }

    companion object {
        //todo: Make a better generator
        fun generateRandomPassword(): Password {
            val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9') + ('0'..'9')
            return Password(List(18) { chars.random() }.joinToString(""))
        }
    }
}