package com.ac.superhero.api.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RiotApiService {
    @GET("riot/account/v1/accounts/by-riot-id/{summonerName}/{tag}")
    suspend fun getSummonerByName(
        @Path("summonerName") summonerName: String,
        @Path("tag") tag: String
    ): Summoner
}
