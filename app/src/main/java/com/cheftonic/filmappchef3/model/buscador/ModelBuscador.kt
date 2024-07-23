package com.cheftonic.filmappchef3.model.buscador

data class ModelBuscador(
    val page: Int,
    val results: List<ResultBuscador>,
    val total_pages: Int,
    val total_results: Int
)