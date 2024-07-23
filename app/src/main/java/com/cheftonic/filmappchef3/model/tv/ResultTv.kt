package com.cheftonic.filmappchef3.model.tv

data class ResultTv(
    val id: Int, //id de la serie
    val name: String, //nombre de la serie en español
    val overview: String, //sinopsis
    val poster_path: String, //Ruta del poster
    val first_air_date: String, //año de lanzamiento (4 primeros char)
    val number_of_seasons: Int, //numero de temporadas
    val key: String?, //Ruta del video https://www.youtube.com/watch?v=KEY
    val serie_voto: Int?
)