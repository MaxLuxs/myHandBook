package by.tms.myhandbook.Model

import androidx.room.Embedded
import androidx.room.Relation
import by.tms.myhandbook.Section

/**Data class для обьединения раздела и секции*/
data class RefAndSec(
    @Embedded val references:References,
    @Relation(parentColumn = "id", entityColumn = "refId")
    val sections: MutableList<Section>
)