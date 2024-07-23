package com.cheftonic.filmappchef3.model.buscador

data class ResultBuscador(
    val id: Int, //id de la pelicula o serie
    val title: String?, // titulo de la pelicula
    val overview: String, // sinopsis de la pelicula o serie
    val poster_path: String, //Ruta del poster de la pelicula o serie
    val release_date: String?, //Fecha de lanzamiento de la peli
    val name: String?, //titutlo de la serie
    val first_air_date: String?, //Año de lanzamiento de la serie
    val media_type: String //Pelicula o serie
)