package com.example.data.remote

import com.example.data.model.BibleApiResponse
import com.example.data.model.CdnChapterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface BibleApiService {

    @GET("{reference}")
    suspend fun getChapter(
        @Path(value = "reference", encoded = false) reference: String,
        @Query("translation") translation: String = "kjv"
    ): Response<BibleApiResponse>

    @GET
    suspend fun getCdnChapter(
        @Url url: String
    ): Response<CdnChapterResponse>
}
