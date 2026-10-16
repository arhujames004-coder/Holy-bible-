package com.example.data.model

data class Verse(
    val book: String,
    val chapter: Int,
    val verseNumber: Int,
    val text: String,
    val translation: String = "KJV"
) {
    val reference: String
        get() = "$book $chapter:$verseNumber"
}

data class ChapterContent(
    val book: String,
    val chapter: Int,
    val translation: String,
    val verses: List<Verse>,
    val isOffline: Boolean = false,
    val note: String? = null
)
