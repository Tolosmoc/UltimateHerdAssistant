package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

enum class StockType (val displayName: String) {
    FOOD("Food"),
    MEDICINE("Medicine");

    override fun toString(): String = displayName
}