package com.cheftonic.filmappchef3.model.cine

data class ResultCine( //Nos dará una lista de las peliculas populares
    val id: Int, //id de la pelicula
    val title: String, //titulo de la pelicula
    val overview: String, //sinopsis
    val release_date: String, //Año de lanzamiento, solo cogeremos los 4 primeros caracteres
    val poster_path: String, //Ruta del poster
    val peli_voto: Int?
)