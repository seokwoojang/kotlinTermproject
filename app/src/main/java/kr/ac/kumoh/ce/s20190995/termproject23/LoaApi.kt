package kr.ac.kumoh.ce.s20190995.termproject23

import retrofit2.http.GET

interface LoaApi {
    @GET("loa")
    suspend fun getLoa(): List<Loa>
}