package kr.ac.kumoh.ce.s20190995.termproject23

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class LoaViewModel(): ViewModel() {
    private val SERVER_URL = "https://port-0-backend-5yc2g32mlomirnbb.sel5.cloudtype.app/"
    private val loaApi: LoaApi
    private val _loaList = MutableLiveData<List<Loa>>()
    val loaList: LiveData<List<Loa>>
        get() = _loaList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        loaApi = retrofit.create(LoaApi::class.java)
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = loaApi.getLoa()
                _loaList.value = response
            } catch (e: Exception){
                Log.e("fetchData()",e.toString())
            }
        }
    }
}