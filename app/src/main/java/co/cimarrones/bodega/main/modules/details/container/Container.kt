package co.cimarrones.bodega.main.modules.details.container

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Container(
    @SerialName("id")
    val id: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("type_container")
    val typeContainer: String = "",
    @SerialName("seal_state")
    val isSeal: Boolean = false
)
