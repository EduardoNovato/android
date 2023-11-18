package co.cimarrones.bodega.main.modules.details.container

data class ContainerUIState(
    var loading: Boolean = false,
    val errorMsgStringID: Int = 0,
    var containerList: List<Container> = listOf(),
    var currentContainer: Container = Container(),
)