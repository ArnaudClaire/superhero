package com.ac.superhero.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import com.ac.superhero.SummonerViewModel
import com.ac.superhero.api.model.Summoner
import com.ac.superhero.repository.SummonerRepository

@Composable
fun SummonerForm(summonerViewModel: SummonerViewModel, modifier: Modifier = Modifier) {
    var summonerName by remember { mutableStateOf(TextFieldValue("")) }
    var summonerTag by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Champ pour entrer le nom du summoner
        Text(
            text = "Entrez le nom du Summoner:",
            style = MaterialTheme.typography.titleLarge // Utiliser titleLarge ou bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = summonerName,
            onValueChange = { summonerName = it },
            label = { Text("Nom du Summoner") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Champ pour entrer le tag du summoner
        Text(
            text = "Entrez le tag du Summoner:",
            style = MaterialTheme.typography.titleLarge // Style similaire à celui du nom
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = summonerTag,
            onValueChange = { summonerTag = it },
            label = { Text("Tag du Summoner") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton de recherche
        Button(
            onClick = {
                summonerViewModel.fetchSummonerData(summonerName.text, summonerTag.text)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Rechercher")
        }
    }
}


