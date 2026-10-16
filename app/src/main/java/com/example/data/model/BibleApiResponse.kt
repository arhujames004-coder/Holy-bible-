package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BibleApiResponse(
    @Json(name = "reference") val reference: String?,
    @Json(name = "verses") val verses: List<ApiVerseItem>?,
    @Json(name = "text") val text: String?,
    @Json(name = "translation_id") val translationId: String?,
    @Json(name = "translation_name") val translationName: String?,
    @Json(name = "translation_note") val translationNote: String?
)

@JsonClass(generateAdapter = true)
data class ApiVerseItem(
    @Json(name = "book_id") val bookId: String?,
    @Json(name = "book_name") val bookName: String?,
    @Json(name = "chapter") val chapter: Int,
    @Json(name = "verse") val verse: Int,
    @Json(name = "text") val text: String
)

@JsonClass(generateAdapter = true)
data class CdnChapterResponse(
    @Json(name = "verses") val verses: List<CdnVerseItem>?
)

@JsonClass(generateAdapter = true)
data class CdnVerseItem(
    @Json(name = "verse") val verse: Int,
    @Json(name = "text") val text: String
)
