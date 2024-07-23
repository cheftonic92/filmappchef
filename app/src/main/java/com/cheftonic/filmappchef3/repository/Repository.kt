package com.cheftonic.filmappchef3.repository

import com.cheftonic.filmappchef3.api.retrofit.RetrofitClient
import com.cheftonic.filmappchef3.model.buscador.ModelBuscador
import com.cheftonic.filmappchef3.model.cine.ModelCine
import com.cheftonic.filmappchef3.model.cine.ResultCine
import com.cheftonic.filmappchef3.model.credits.ModelCredits
import com.cheftonic.filmappchef3.model.tv.DetallesTv
import com.cheftonic.filmappchef3.model.tv.ModelTv
import com.cheftonic.filmappchef3.model.tv.ResultTv
import com.cheftonic.filmappchef3.model.video.ModelVideo
import retrofit2.Response

class Repository {

    suspend fun getBuscador(query: String, page: Int): Response<ModelBuscador> {
        return RetrofitClient.tmdbService.getBuscador(query, page = page)
    }

    //Funciones Cine

    suspend fun getPelicula(movieId: Int): Response<ResultCine> {
        return RetrofitClient.tmdbService.getPelicula(movie_id = movieId)
    }

    suspend fun getPopularCine(page: Int): Response<ModelCine> {
        return RetrofitClient.tmdbService.getPopularCine(page = page)
    }

    suspend fun getTendenciasCine(page: Int): Response<ModelCine> {
        return RetrofitClient.tmdbService.getTendenciasCine(page = page)
    }

    suspend fun getCreditosCine(movieId: Int): Response<ModelCredits> {
        return RetrofitClient.tmdbService.getCreditosCine(movie_id = movieId)
    }

    suspend fun getTrailerCine(movieId: Int): Response<ModelVideo> {
        return RetrofitClient.tmdbService.getTrailerCine(movie_id = movieId)
    }

    suspend fun getRecoCine(movieId: Int): Response<ModelCine> {
        return RetrofitClient.tmdbService.getRecoCine(movie_id = movieId)
    }

    //Funciones TV

    suspend fun getSerie(seriesId: Int): Response<DetallesTv> {
        return RetrofitClient.tmdbService.getSerie(series_id = seriesId)
    }

    suspend fun getMiSerie(seriesId: Int): Response<ResultTv> {
        return RetrofitClient.tmdbService.getMiSerie(seriesId)
    }

    suspend fun getPopularTv(page: Int): Response<ModelTv> {
        return RetrofitClient.tmdbService.getPopularTv(page = page)
    }

    suspend fun getTendenciasTv(page: Int): Response<ModelTv> {
        return RetrofitClient.tmdbService.getTendenciasTv(page = page)
    }

    suspend fun getCreditosTv(seriesId: Int): Response<ModelCredits> {
        return RetrofitClient.tmdbService.getCreditosTv(series_id = seriesId)
    }

    suspend fun getTrailerTv(seriesId: Int): Response<ModelVideo> {
        return RetrofitClient.tmdbService.getTrailerTv(series_id = seriesId)
    }

    suspend fun getRecoTv(seriesId: Int): Response<ModelTv> {
        return RetrofitClient.tmdbService.getRecoTv(series_id = seriesId)
    }
}