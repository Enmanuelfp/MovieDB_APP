package com.bootcamp.moviedb_app.util

class Constants {
    companion object{
        const val BASE_URL="https://api.themoviedb.org/3/"
        const val ENDPOINT="movie/popular"
        const val IMAGE_BASE_URL="https://image.tmdb.org/t/p/w500/"
        const val API_KEY ="75c3a3aa08c3e1c6d92d1ff54e71c2b4"
    }
}

//https://api.themoviedb.org/3/movie/popular?language=en-US&page=1&api_key=75c3a3aa08c3e1c6d92d1ff54e71c2b4
//https://image.tmdb.org/t/p/w500/kKgQzkUCnQmeTPkyIwHly2t6ZFI.jpg

//https://api.themoviedb.org/3/movie/popular?language=en-US&page=1/image.tmdb.org/t/p/w500/kKgQzkUCnQmeTPkyIwHly2t6ZFI.jpg&api_key=75c3a3aa08c3e1c6d92d1ff54e71c2b4