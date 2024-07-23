package com.cheftonic.filmappchef3.api.api

import com.cheftonic.filmappchef3.model.buscador.ModelBuscador
import com.cheftonic.filmappchef3.model.cine.ModelCine
import com.cheftonic.filmappchef3.model.cine.ResultCine
import com.cheftonic.filmappchef3.model.credits.ModelCredits
import com.cheftonic.filmappchef3.model.tv.DetallesTv
import com.cheftonic.filmappchef3.model.tv.ModelTv
import com.cheftonic.filmappchef3.model.tv.ResultTv
import com.cheftonic.filmappchef3.model.video.ModelVideo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {
    //Buscador
    //Obtener Busqueda de Pelicula y Series
    @GET("search/multi?include_adult=false&language=es")
    suspend fun getBuscador(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<ModelBuscador>

    //PELICULAS//

    //Obtener Detalles Pelicula
    @GET("movie/{movie_id}?language=es")
    suspend fun getPelicula(
        @Path("movie_id") movie_id: Int
    ): Response<ResultCine>

    //Obtener Lista de Peliculas Populares
    @GET("movie/popular?language=es")
    suspend fun getPopularCine(
        @Query("page") page: Int
    ): Response<ModelCine>

    //Obtener Lista de Peliculas en Tendencia
    @GET("trending/movie/day?language=es")
    suspend fun getTendenciasCine(
        @Query("page") page: Int
    ): Response<ModelCine>

    //Obtener Reparto y Directo de Pelicula
    @GET("movie/{movie_id}/credits?language=es-ES")
    suspend fun getCreditosCine(
        @Path("movie_id") movie_id: Int
    ): Response<ModelCredits>

    //Obtener Trailer Cine
    @GET("movie/{movie_id}/videos?language=es")
    suspend fun getTrailerCine(
        @Path("movie_id") movie_id: Int
    ): Response<ModelVideo>

    //Obtener Recomendaciones
    @GET("movie/{movie_id}/recommendations?language=es&page=1")
    suspend fun getRecoCine(
        @Path("movie_id") movie_id: Int
    ): Response<ModelCine>


    //SERIES

    //Obtener Detalles Serie
    @GET("tv/{series_id}")
    suspend fun getSerie(
        @Path("series_id") series_id: Int
    ): Response<DetallesTv>

    @GET("tv/{series_id}?language=es")
    suspend fun getMiSerie(
        @Path("series_id") series_id: Int
    ): Response<ResultTv>

    //Obtener Series Populares
    @GET("tv/popular?language=es")
    suspend fun getPopularTv(
        @Query("page") page: Int
    ): Response<ModelTv>

    //Obtener Series en tendencia
    @GET("trending/tv/day?language=es")
    suspend fun getTendenciasTv(
        @Query("page") page: Int
    ): Response<ModelTv>

    //Obtener Casting Serie
    @GET("tv/{series_id}/credits")
    suspend fun getCreditosTv(
        @Path("series_id") series_id: Int
    ): Response<ModelCredits>

    //Obtener Trailer Tv
    @GET("tv/{series_id}/videos?language=es")
    suspend fun getTrailerTv(
        @Path("series_id") series_id: Int
    ): Response<ModelVideo>

    //Obtener Recomendaciones
    @GET("tv/{series_id}/recommendations?language=es&page=1")
    suspend fun getRecoTv(
        @Path("series_id") series_id: Int
    ): Response<ModelTv>
}