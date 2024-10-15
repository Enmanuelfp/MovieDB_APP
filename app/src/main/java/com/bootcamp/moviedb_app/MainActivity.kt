package com.bootcamp.moviedb_app

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.bootcamp.moviedb_app.ui.theme.MovieDB_APPTheme
import com.bootcamp.moviedb_app.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MoviesViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            MovieDB_APPTheme {
                MoviesScreen(viewModel)
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MoviesScreen(viewModel: MoviesViewModel = hiltViewModel()) {
//    val state by viewModel.state.collectAsState()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Simple Rest API + Room", color = Color.White) },
//                actions = {
//                    IconButton(
//                        onClick = { viewModel.addMovie() }
//                    ) {
//                        Icon(Icons.Default.Add, "Add", tint = Color.White)
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFFFF8A65)
//                )
//            )
//        }
//    ) {
//        ContentHomeView(it, viewModel)
//    }
//}
//
//@Composable
//fun ContentHomeView(
//    paddingValues:PaddingValues,
//    viewModel: MoviesViewModel
//) {
//    val movies by viewModel.movies.collectAsState(listOf())
//    val isLoading by viewModel.isLoading.collectAsState(false)
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(paddingValues)
//            .padding(8.dp)
//    ) {
//        var moviesCount = movies.size
//        if (isLoading as Boolean) moviesCount++
//
//        items(count = moviesCount) { index ->
//            var auxIndex = index
//
//            val movie = movies[auxIndex]
//            Card(
//                shape = RoundedCornerShape(8.dp),
//                elevation = CardDefaults.cardElevation(1.dp),
//                modifier = Modifier
//                    .padding(horizontal = 8.dp, vertical = 4.dp)
//                    .fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier
//                        .padding(8.dp)
//                ) {
//                    Image(
//                        modifier = Modifier.size(50.dp),
//                        painter = rememberAsyncImagePainter(
//                            model = movie.poster_path
//                        ),
//                        contentDescription = null,
//                        contentScale = ContentScale.FillHeight
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Column(
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(movie.original_title)
//                        Text(movie.release_date)
//                        Text(movie.vote_average.toString())
//                    }
//                    Spacer(modifier = Modifier.height(16.dp))
//                    IconButton(
//                        onClick = { viewModel.deleteMovie(movie) }
//                    ) {
//                        Icon(Icons.Default.Delete, "Delete")
//                    }
//                }
//            }
//        }
//    }
//}



