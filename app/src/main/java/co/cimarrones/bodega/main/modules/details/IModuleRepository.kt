package co.cimarrones.bodega.main.modules.details

import co.cimarrones.bodega.main.modules.details.container.Container
import okhttp3.ResponseBody
import retrofit2.Response

interface IModuleRepository {
    suspend fun getContainers(): ResponseBody
}