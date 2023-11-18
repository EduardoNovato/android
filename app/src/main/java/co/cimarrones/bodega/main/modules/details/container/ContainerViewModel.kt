package co.cimarrones.bodega.main.modules.details.container

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.cimarrones.bodega.httpService.JsonParserConfig
import co.cimarrones.bodega.main.modules.details.IModuleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import javax.inject.Inject

@HiltViewModel
class ContainerViewModel @Inject constructor(private val repository: IModuleRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ContainerUIState())
    val uiState: StateFlow<ContainerUIState> = _uiState.asStateFlow()

    private fun setLoading(loading: Boolean) {
        _uiState.update { currentState -> currentState.copy(loading = loading) }
    }

    private fun setContainersList(list: List<Container>) {
        _uiState.update { currentState -> currentState.copy(containerList = list) }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getContainers() {
        setLoading(true)
        viewModelScope.launch {
            try {
                val responseBody = repository.getContainers()
                val response = responseBody.string()
                if (response.isNotEmpty()) {
                    val arr = JsonParserConfig.decodeFromString<List<Container>>(response)
                    setContainersList(arr)
                }
            } catch (_: Exception){

            } finally {
                setLoading(false)
            }

        }
    }
}
