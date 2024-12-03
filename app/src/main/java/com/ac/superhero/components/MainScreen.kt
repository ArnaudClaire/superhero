package com.ac.superhero.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ac.superhero.SummonerViewModel
import com.ac.superhero.api.model.RiotApiService
import com.ac.superhero.api.model.Summoner
import com.ac.superhero.api.model.SummonerState
import com.ac.superhero.repository.MockSummonerRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun MainScreen(modifier: Modifier = Modifier, summonerViewModel: SummonerViewModel) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        SummonerForm(summonerViewModel = summonerViewModel)
        Spacer(modifier = Modifier.height(16.dp))

        when (val state = summonerViewModel.summonerState.value) {
            SummonerState.LOADING -> {
                Text("Chargement des données...", modifier = modifier)
            }
            SummonerState.SUCCESS -> {
                val summoner = summonerViewModel.summonerData.value
                summoner?.let {
                    SummonerInfoView(summoner = it)
                }
            }
            SummonerState.ERROR -> {
                Text("Erreur: Impossible de charger les données", modifier = modifier)
            }
            else -> {
                Text("Entrez un nom pour rechercher un Summoner.", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    // Créer un mock du repository
    // Création d'une instance de Retrofit pour un environnement de mock
    val mockRiotApiService = Retrofit.Builder()
        .baseUrl("https://mockapi.example.com/") // Remplacez par une URL fictive ou une vraie URL de mock
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RiotApiService::class.java)

    // Création du repository mock avec le RiotApiService simulé
    val mockRepository = MockSummonerRepository(riotApiService = mockRiotApiService)

    // Créer une instance du ViewModel avec des données fictives
    val summonerViewModel = SummonerViewModel(mockRepository).apply {
        summonerState.value = SummonerState.SUCCESS
        summonerData.value = Summoner(
            id = "1",
            name = "Dusk Breàker",
            tag="EUW",
            summonerLevel = 42,
            profileIconId = 1234
        )
    }

    // Appel de MainScreen avec ViewModel mocké
    MainScreen(summonerViewModel = summonerViewModel)
}
