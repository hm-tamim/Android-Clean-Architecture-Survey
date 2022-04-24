package co.hmtamim.survey.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.hmtamim.survey.R
import co.hmtamim.survey.data.observer.GlobalObserver
import co.hmtamim.survey.data.resource.ResourcesProvider
import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.domain.model.toIds
import co.hmtamim.survey.domain.usecase.*
import co.hmtamim.survey.ui.base.BaseViewModel
import co.hmtamim.survey.ui.base.NavigationEvent
import co.hmtamim.survey.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Output {
    val surveyListModels: StateFlow<List<SurveyModel>>
    val selectedSurveyPosition: StateFlow<Int>
    fun navigateToPreview(bundle: SurveyModel)
    fun navigateToLogin()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchSurveysUseCase: FetchSurveysUseCase,
    private val getSurveysUseCase: GetSurveysUseCase,
    private val insertSurveysUseCase: InsertSurveysUseCase,
    private val deleteOldSurveysUseCase: DeleteOldSurveysUseCase,
    private val resourcesProvider: ResourcesProvider,
    private val globalObserver: GlobalObserver,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers), Output {

    private val _surveyListModels: MutableStateFlow<List<SurveyModel>> =
        MutableStateFlow(emptyList())
    override val surveyListModels: StateFlow<List<SurveyModel>> get() = _surveyListModels
    private val _selectedSurveyPosition = MutableStateFlow(-1)
    override val selectedSurveyPosition: StateFlow<Int> get() = _selectedSurveyPosition

    init {
        getSurveyList()
        fetchSurveyList()
    }

    fun requestLogout() {
        execute {
            globalObserver.onRefreshTokenExpiredLogout.send(true)
        }
    }

    override fun navigateToPreview(bundle: SurveyModel) {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.Preview(bundle))
        }
    }

    override fun navigateToLogin() {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.Login)
        }
    }

    fun setSelectedSurveyPosition(position: Int) {
        _selectedSurveyPosition.value = position
    }

    fun getSelectedSurveyModel(): SurveyModel {
        return surveyListModels.value[selectedSurveyPosition.value]
    }

    private fun getSurveyList() {
        execute {
            when (val result = getSurveysUseCase.execute()) {
                is UseCaseResult.Success -> {
                    result.data.collectLatest {
                        _surveyListModels.value = it
                    }
                }
                is UseCaseResult.Failure -> {
                    _error.emit(resourcesProvider.getString(R.string.error_survey_database))
                }
                is UseCaseResult.Error -> {
                    _error.emit(result.exception.message.orEmpty())
                }
            }
        }
    }

    fun fetchSurveyList() {
        showLoading()
        execute {
            when (val result = fetchSurveysUseCase.execute()) {
                is ApiResponse.Success -> {
                    result.data.let {
                        insertSurveys(it)
                        deleteOdSurveys(it.toIds())
                        hideLoading()
                    }
                }
                is ApiResponse.Failure -> {
                    _error.emit(result.message)
                }
            }
            hideLoading()
        }
    }

    private fun insertSurveys(list: List<SurveyModel>) {
        execute {
            when (val result = insertSurveysUseCase.execute(list)) {
                is UseCaseResult.Success -> {

                }
                is UseCaseResult.Failure -> {
                    _error.emit(resourcesProvider.getString(R.string.error_survey_insert))
                }
                is UseCaseResult.Error -> {
                    _error.emit(result.exception.message.orEmpty())
                }
            }
            hideLoading()
        }
    }

    private fun deleteOdSurveys(surveyIds: List<String>) {
        execute {
            deleteOldSurveysUseCase.execute(surveyIds)
        }
    }
}
