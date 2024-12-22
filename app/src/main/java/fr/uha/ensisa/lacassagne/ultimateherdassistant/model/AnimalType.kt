package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

enum class AnimalType (val displayName: String) {
    MAMMAL("Mammal"),
    BIRD("Bird"),
    REPTILE("Reptile"),
    FISH("Fish"),
    AMPHIBIAN("Amphibian");

    override fun toString(): String = displayName
}