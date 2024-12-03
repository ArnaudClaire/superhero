package com.ac.superhero.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ac.superhero.api.model.Summoner

@Composable
fun SummonerInfoView(summoner: Summoner, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nom du Summoner: ${summoner.name}",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Niveau: ${summoner.summonerLevel}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

