package com.ac.superhero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ac.superhero.api.model.SummonerState
import com.ac.superhero.repository.SummonerRepository
import com.ac.superhero.api.model.RiotApiService
import com.ac.superhero.ui.theme.SuperheroTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    // Créez une instance du repository avec le service API
    private val riotApiService: RiotApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotApiService::class.java)
    }

    // Créez le repository avec le service
    private val summonerRepository = SummonerRepository(riotApiService)

    // Utilisez le ViewModel avec un factory pour l'injecter
    private val summonerViewModel: SummonerViewModel by viewModels {
        SummonerViewModelFactory(summonerRepository)
    }

    @Composable
    fun MainScreen(modifier: Modifier = Modifier, summonerViewModel: SummonerViewModel) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Champ pour entrer le nom du summoner
            var summonerName by remember { mutableStateOf("") }
            TextField(
                value = summonerName,
                onValueChange = { summonerName = it },
                label = { Text("Entrez le nom du Summoner") }
            )

            // Champ pour entrer le tag du summoner
            var summonerTag by remember { mutableStateOf("") }
            TextField(
                value = summonerTag,
                onValueChange = { summonerTag = it },
                label = { Text("Entrez le tag du Summoner") }
            )

            // Bouton de recherche
            Button(onClick = {
                summonerViewModel.fetchSummonerData(summonerName, summonerTag) // Passer à la fois le nom et le tag
            }) {
                Text("Rechercher")
            }

            // Observer l'état du summoner
            val summonerState by summonerViewModel.summonerState.observeAsState(SummonerState.LOADING)
            val summonerData by summonerViewModel.summonerData.observeAsState()

            // Afficher l'état
            when (summonerState) {
                SummonerState.LOADING -> {
                    Text(text = "Chargement...")
                }
                SummonerState.SUCCESS -> {
                    if (summonerData != null) {
                        Text("Nom du Summoner: ${summonerData?.name}")
                        Text("Niveau: ${summonerData?.summonerLevel}")
                    }
                }
                SummonerState.ERROR -> {
                    Text(text = "Erreur lors du chargement des données.")
                }
            }
        }
    }

}

// Factory pour créer le ViewModel avec ses dépendances
class SummonerViewModelFactory(private val summonerRepository: SummonerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SummonerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SummonerViewModel(summonerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
