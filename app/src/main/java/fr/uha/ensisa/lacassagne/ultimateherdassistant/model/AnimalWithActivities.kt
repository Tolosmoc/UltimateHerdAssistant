package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

import androidx.room.Embedded
import androidx.room.Relation

data class AnimalWithActivities(
    @Embedded val animal: Animal,
    @Relation(
        parentColumn = "id",
        entityColumn = "animal_id"
    )
    val activities: List<Activite>
)