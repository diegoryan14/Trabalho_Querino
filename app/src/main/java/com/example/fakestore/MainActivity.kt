package com.example.fakestore

import android.graphics.ColorSpace.Rgb
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fakestore.Model.Agent
import com.example.fakestore.Model.FakeProduct
import com.example.fakestore.ui.theme.FakeStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FakeStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onBackground
                ) {
                    ListaDeProdutos()
                }
            }
        }
    }
}

@Composable
fun ListaDeProdutos() {
    val fakeStoreViewModel: FakeStoreViewModel = viewModel()
    val agents by fakeStoreViewModel.agent //ALT+ENTER aqui para importar getValue

    var searchQuery by remember { mutableStateOf("") }
    val filteredAgents = agents.filter { agent ->
        agent.displayName.contains(searchQuery, ignoreCase = true)
    }

    Column {

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text(text = "Buscar por Nome") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        LazyColumn() {
            val filteredAgents = agents.filter { agent ->
                agent.displayName.contains(searchQuery, ignoreCase = true)
            }

            items(filteredAgents) { agent ->
                CardProduto(agent = agent)
            }
        }
        DisposableEffect(Unit) {
            fakeStoreViewModel.fetchAgents()
            onDispose { }
        }
    }
}

@Composable
fun CardProduto(agent: Agent) {

    Card(
        elevation = CardDefaults
            .cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(16.dp)
            //.height(300.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(red = 128, green = 0, blue = 32))
        ) {
            // Background Image
            AsyncImage(
                model = agent.background,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .height(200.dp)
                    .padding(top = 70.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = agent.displayName,
                    fontSize = 35.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(red = 255, green = 255, blue = 255),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp)
                )
                AsyncImage(
                    model = ImageRequest.Builder(
                        context = LocalContext.current
                    ).data(agent.displayIconSmall).build(),
                    contentDescription = agent.id,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp)
                        .padding(top = 10.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = if (agent.description.length > 200) {
                        agent.description.substring(0..199) + "..."
                    } else {
                        agent.description
                    },
                    fontWeight = FontWeight.Bold,
                    color = Color(red = 255, green = 255, blue = 255),
                    modifier = Modifier
                        .padding(12.dp)
                )
            }
        }
    }
}