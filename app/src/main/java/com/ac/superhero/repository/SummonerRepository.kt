package com.ac.superhero.repository

import com.ac.superhero.api.model.RiotApiService
import com.ac.superhero.api.model.Summoner

open class SummonerRepository(private val riotApiService: RiotApiService) {

    // Appel API pour récupérer les données du summoner par son nom
    open suspend fun getSummonerByName(summonerName: String, tag: String): Summoner {
        try {
            return riotApiService.getSummonerByName(summonerName, tag)
        } catch (e: Exception) {
            throw Exception("Error fetching data: ${e.message}", e)
        }
    }
}


class MockSummonerRepository(riotApiService: RiotApiService) : SummonerRepository(riotApiService) {
    override suspend fun getSummonerByName(name: String, tag: String): Summoner {
        // Retourne une donnée fictive pour la prévisualisation
        return Summoner(
            id = "1",
            name = "Toto",
            tag="5555",
            summonerLevel = 42,
            profileIconId = 1234
        )
    }
}
