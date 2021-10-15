package com.jackBeBack.kronos

data class CardEntity(
    var description: String
) {
    companion object{
        val default = CardEntity("Test Description")
    }
}