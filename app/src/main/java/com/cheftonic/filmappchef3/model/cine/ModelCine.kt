package com.cheftonic.filmappchef3.model.cine

data class ModelCine (
    val page: Int,
    val results: List<ResultCine>,//Array de resultados
    val total_pages: Int,
    val total_results: Int
)