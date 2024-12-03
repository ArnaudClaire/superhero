package com.ac.superhero

import android.util.Log
import androidx.lifecycle.*
import com.ac.superhero.api.model.Summoner
import com.ac.superhero.api.model.SummonerState
import com.ac.superhero.repository.SummonerRepository
import kotlinx.coroutines.launch

class SummonerViewModel(private val summonerRepository: SummonerRepository) : ViewModel() {

    // Les LiveData qui stockent l'état et les données
    val summonerState: MutableLiveData<SummonerState> = MutableLiveData(SummonerState.LOADING)
    val summonerData: MutableLiveData<Summoner?> = MutableLiveData(null)

    fun fetchSummonerData(summonerName: String, summonerTag: String) {
        viewModelScope.launch {
            summonerState.value = SummonerState.LOADING
            try {
                // Log pour vérifier si la fonction est appelée
                Log.d("SummonerViewModel", "Fetching data for $summonerName#$summonerTag")
                val summoner = summonerRepository.getSummonerByName(summonerName, summonerTag)
                summonerData.value = summoner
                summonerState.value = SummonerState.SUCCESS
            } catch (e: Exception) {
                Log.e("SummonerViewModel", "Error fetching data", e)
                summonerState.value = SummonerState.ERROR
            }
        }
    }
}
