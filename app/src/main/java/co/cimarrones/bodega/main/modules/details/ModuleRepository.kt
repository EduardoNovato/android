package co.cimarrones.bodega.main.modules.details

import co.cimarrones.bodega.httpService.IRestAPIService
import co.cimarrones.bodega.main.modules.details.container.Container
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleRepository @Inject constructor(private val apiService: IRestAPIService) : IModuleRepository {

    override suspend fun getContainers(): ResponseBody {
        return apiService.getContainers()
    }
}