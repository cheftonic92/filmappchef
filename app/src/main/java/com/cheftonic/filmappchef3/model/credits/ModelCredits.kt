package com.cheftonic.filmappchef3.model.credits

data class ModelCredits(
    val id: Int,
    val cast: List<ResultCreditsCine>,//Array del reparto y sacamos original_name
    val crew: List<ResultCreditsCine>//Array del equipo y sacamos job para sacar original_name cuando job == director
)
