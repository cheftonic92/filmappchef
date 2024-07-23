package com.cheftonic.filmappchef3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheftonic.filmappchef3.model.buscador.ModelBuscador
import com.cheftonic.filmappchef3.model.cine.ModelCine
import com.cheftonic.filmappchef3.model.credits.ModelCredits
import com.cheftonic.filmappchef3.model.tv.DetallesTv
import com.cheftonic.filmappchef3.model.tv.ModelTv
import com.cheftonic.filmappchef3.model.tv.ResultTv
import com.cheftonic.filmappchef3.model.video.ModelVideo
import com.cheftonic.filmappchef3.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    //Buscador
    private val _buscarResultados: MutableLiveData<Response<ModelBuscador>> = MutableLiveData()
    val buscarResultados: LiveData<Response<ModelBuscador>> = _buscarResultados

    //Peliculas

    private val _misPeliculas: MutableLiveData<Response<ModelCine>> = MutableLiveData()
    val misPeliculas: LiveData<Response<ModelCine>> = _misPeliculas

    private val _popularCine: MutableLiveData<Response<ModelCine>> = MutableLiveData()
    val popularCine: LiveData<Response<ModelCine>> = _popularCine

    private val _tendenciasCine: MutableLiveData<Response<ModelCine>> = MutableLiveData()
    val tendenciasCine: LiveData<Response<ModelCine>> = _tendenciasCine

    private val _detallesPelicula: MutableLiveData<Response<ModelCine>> = MutableLiveData()
    val detallesPelicula: LiveData<Response<ModelCine>> = _detallesPelicula

    private val _creditosCine: MutableLiveData<Response<ModelCredits>> = MutableLiveData()
    val creditosCine: LiveData<Response<ModelCredits>> = _creditosCine

    private val _directorCine: MutableLiveData<Response<ModelCredits>> = MutableLiveData()
    val directorCine: LiveData<Response<ModelCredits>> = _directorCine

    private val _trailerCine: MutableLiveData<Response<ModelVideo>> = MutableLiveData()
    val trailerCine: LiveData<Response<ModelVideo>> = _trailerCine

    private val _recoCine: MutableLiveData<Response<ModelCine>> = MutableLiveData()
    val recoCine: LiveData<Response<ModelCine>> = _recoCine

    private val _posterCine: MutableLiveData<Response<ModelCine>> = MutableLiveData()
    val posterCine: LiveData<Response<ModelCine>> = _posterCine

    //SERIES
    private val _serie: MutableLiveData<Response<DetallesTv>> = MutableLiveData()
    val serie: LiveData<Response<DetallesTv>> = _serie

    private val _miSerie: MutableLiveData<Response<ResultTv>> = MutableLiveData()
    val miSerie: LiveData<Response<ResultTv>> = _miSerie

    private val _popularTv: MutableLiveData<Response<ModelTv>> = MutableLiveData()
    val popularTv: LiveData<Response<ModelTv>> = _popularTv

    private val _tendenciasTv: MutableLiveData<Response<ModelTv>> = MutableLiveData()
    val tendenciasTv: LiveData<Response<ModelTv>> = _tendenciasTv

    private val _repartoTv: MutableLiveData<Response<ModelCredits>> = MutableLiveData()
    val repartoTv: LiveData<Response<ModelCredits>> = _repartoTv

    private val _trailerTv: MutableLiveData<Response<ModelVideo>> = MutableLiveData()
    val trailerTv: LiveData<Response<ModelVideo>> = _trailerTv

    private val _recoTv: MutableLiveData<Response<ModelTv>> = MutableLiveData()
    val recoTv: LiveData<Response<ModelTv>> = _recoTv

    private val _posterTv: MutableLiveData<Response<ModelTv>> = MutableLiveData()
    val posterTv: LiveData<Response<ModelTv>> = _posterTv

    //FUNCIONES

    // BUSCADOR

    fun getBuscador(query: String, page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getBuscador(query, page)
                _buscarResultados.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al buscar: ${e.message}")
            }
        }
    }

    // PELICULA

    fun getPopularCine(page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getPopularCine(page)
                _popularCine.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al obtener Populares: ${e.message}")
            }
        }
    }

    fun getTendenciasCine(page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getTendenciasCine(page)
                _tendenciasCine.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al obtener Tendencias: ${e.message}")
            }
        }
    }

    fun getCreditosCine(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getCreditosCine(movieId)
                _creditosCine.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al obtener Reparto: ${e.message}")
            }
        }
    }

    fun getTrailerCine(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getTrailerCine(movieId)
                _trailerCine.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al obtener trailer: ${e.message}")
            }
        }
    }

    fun getRecoCine(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getRecoCine(movieId)
                _recoCine.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al obtener trailer: ${e.message}")
            }
        }
    }

    //SERIES

    fun getSerie(seriesId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getSerie(seriesId)
                _serie.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al obtener película: ${e.message}")
            }
        }
    }


    fun getPopularTv(page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getPopularTv(page)
                _popularTv.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al obtener Populares: ${e.message}")
            }
        }
    }

    fun getTendenciasTv(page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getTendenciasTv(page)
                _tendenciasTv.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al obtener Tendencias: ${e.message}")
            }
        }
    }

    fun getRepartoTv(seriesId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getCreditosTv(seriesId)
                _repartoTv.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al obtener Reparto: ${e.message}")
            }
        }
    }

    fun getTrailerTv(seriesId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getTrailerTv(seriesId)
                _trailerTv.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al obtener trailer: ${e.message}")
            }
        }
    }

    fun getRecoTv(seriesId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getRecoTv(seriesId)
                _recoTv.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error al obtener trailer: ${e.message}")
            }
        }
    }
}