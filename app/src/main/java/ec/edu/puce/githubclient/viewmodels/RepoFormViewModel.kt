package ec.edu.puce.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.puce.githubclient.models.RepositoryPayload
import ec.edu.puce.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoFormViewModel: ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg = _errorMsg.asStateFlow()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess = _isSuccess.asStateFlow()

    fun createRepository (name: String, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                // Sanitize description to remove control characters (like \n) that GitHub API rejects
                val sanitizedDescription = description.replace(Regex("[\\x00-\\x1F\\x7F]"), " ")
                val repositoryBody = RepositoryPayload(name, sanitizedDescription)
                RetrofitClient.apiService.createRepository(repositoryBody)
                _isSuccess.value = true
            }catch (e: Exception){
                _errorMsg.value = "Error al crear repositorio: ${e.localizedMessage}"
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun updateRepository(owner: String, oldName: String, newName: String, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                // Sanitize description to remove control characters (like \n) that GitHub API rejects
                val sanitizedDescription = description.replace(Regex("[\\x00-\\x1F\\x7F]"), " ")
                val repositoryBody = RepositoryPayload(newName, sanitizedDescription)
                RetrofitClient.apiService.updateRepository(owner, oldName, repositoryBody)
                _isSuccess.value = true
            } catch (e: Exception) {
                _errorMsg.value = "Error al actualizar repositorio: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetSuccess(){
        _isSuccess.value = false
    }

    fun resetError(){
        _errorMsg.value = null
    }
}