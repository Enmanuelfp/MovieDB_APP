package com.bootcamp.moviedb_app

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.bootcamp.moviedb_app.datasource.RestDataSource
import com.bootcamp.moviedb_app.model.MoviesDao
import com.bootcamp.moviedb_app.model.MoviesEntity
import com.bootcamp.moviedb_app.repository.MoviesRepositoryImp
import com.bootcamp.moviedb_app.util.Constants.Companion.ENDPOINT
import junit.framework.Assert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

val testMovie1 = MoviesEntity(
    id = 1L,
    original_title = "The Great Adventure",
    poster_path = "https://example.com/images/great_adventure.jpg",
    release_date = "2023-07-21",
    vote_average = 8.2f
)

val testMovie2 = MoviesEntity(
    id = 2L,
    original_title = "Mystery in the Dark",
    poster_path = "https://example.com/images/mystery_dark.jpg",
    release_date = "2022-11-15",
    vote_average = 7.5f
)

val testMovie3 = MoviesEntity(
    id = 3L,
    original_title = "Space Odyssey: Beyond Earth",
    poster_path = "https://example.com/images/space_odyssey.jpg",
    release_date = "2024-01-10",
    vote_average = 9.0f
)

class MovieRepositoryTest {
    private val mockWebServer = MockWebServer().apply {
        url("/")
        dispatcher = myDispatcher
    }

    private val restDataSource = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RestDataSource::class.java)

    private val newsRepository = MoviesRepositoryImp(restDataSource,MockMoviesDao())

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun `Verificar si obtenemos correctamente las movies de la DB`() = runBlocking {
        val movie = newsRepository.getMoviesFromDb().first()
        Assert.assertEquals(3,movie.size)
    }

    @Test
    fun `Verificar si se elimina correctamente una movie de la DB`() = runBlocking {

        var movies = newsRepository.getMoviesFromDb().first()
        Assert.assertEquals(3, movies.size)


        newsRepository.deleteOneMovie(testMovie1)


        movies = newsRepository.getMoviesFromDb().first()
        Assert.assertEquals(2, movies.size)


        assertFalse(movies.contains(testMovie1))
    }

}

class MockMoviesDao: MoviesDao{

    private val movie = MutableStateFlow<List<MoviesEntity>>(listOf(testMovie1, testMovie2, testMovie3))


    override suspend fun insertOneMovie(moviesEntity: MoviesEntity) {
        movie.value = movie.value.toMutableList().apply{add(moviesEntity)}
    }

    override fun getMovies(): Flow<List<MoviesEntity>> = movie

    override suspend fun deleteMovie(moviesEntity: MoviesEntity) {
        movie.value = movie.value.toMutableList().apply { remove(moviesEntity) }
    }

}

val myDispatcher: Dispatcher = object : Dispatcher(){
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path){
            "/$ENDPOINT" -> MockResponse().apply { addResponse("top_movies.json") }
            else -> MockResponse().setResponseCode(404)
        }
    }

}

fun MockResponse.addResponse(filePath: String): MockResponse {
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
    val source = inputStream?.source()?.buffer()
    source?.let {
        setResponseCode(200)
        setBody(it.readString(StandardCharsets.UTF_8))
    }
    return this
}