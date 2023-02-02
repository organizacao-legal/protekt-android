package me.brisson.protekt.domain.model

data class Password(val value: String) {
    val hasLower = this.value.contains(".*[a-z].*".toRegex())
    val hasUpper = this.value.contains(".*[A-Z].*".toRegex())
    val hasNumeric = this.value.contains(".*\\d.*".toRegex())
    val hasSpecialChar = this.value.contains(".*[-+_!@#\$%^&*., ?].*".toRegex())
    val hasAtLeast8Char = this.value.length >= 8

    fun calculatePasswordSafety(): Float {
        listOf(hasLower, hasUpper, hasNumeric, hasSpecialChar, hasAtLeast8Char).let { conditionList ->
            val singleItemPercentage = 1f/conditionList.size
            val filteredListSize = conditionList.filter { it }.size
            return  filteredListSize * singleItemPercentage
        }
    }

    fun hasAllCharTypes() : Boolean {
        return (hasLower && hasUpper && hasNumeric)
    }

    companion object {
        //todo: Make a better generator
        fun generateRandomPassword(): Password {
            val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9') + ('0'..'9')
            return Password(List(18) { chars.random() }.joinToString(""))
        }
    }
}