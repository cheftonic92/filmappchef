package com.cheftonic.filmappchef3.model.tv

data class ModelTv(
    val page: Int,
    val results: List<ResultTv>,//Array de resultados
    val total_pages: Int,
    val total_results: Int
)