package com.example.data.repository

import com.example.data.local.BibleDao
import com.example.data.local.SampleScriptures
import com.example.data.model.BookmarkEntity
import com.example.data.model.CachedChapterEntity
import com.example.data.model.ChapterContent
import com.example.data.model.HighlightEntity
import com.example.data.model.ReadingPlanProgressEntity
import com.example.data.model.Verse
import com.example.data.model.VerseNoteEntity
import com.example.data.remote.BibleApiClient
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class BibleRepository(
    private val dao: BibleDao
) {
    val allBookmarks: Flow<List<BookmarkEntity>> = dao.getAllBookmarks()
    val allHighlights: Flow<List<HighlightEntity>> = dao.getAllHighlights()
    val allNotes: Flow<List<VerseNoteEntity>> = dao.getAllNotes()
    val allReadingProgress: Flow<List<ReadingPlanProgressEntity>> = dao.getAllReadingPlanProgress()

    private val verseListAdapter = BibleApiClient.moshiInstance.adapter<List<String>>(
        Types.newParameterizedType(List::class.java, String::class.java)
    )

    fun getHighlightsForChapter(book: String, chapter: Int): Flow<List<HighlightEntity>> {
        return dao.getHighlightsForChapter(book, chapter)
    }

    fun getNotesForChapter(book: String, chapter: Int): Flow<List<VerseNoteEntity>> {
        return dao.getNotesForChapter(book, chapter)
    }

    suspend fun loadChapter(book: String, chapter: Int, translation: String): ChapterContent = withContext(Dispatchers.IO) {
        val normalizedTr = translation.lowercase()

        // 1. Check local cache in Room
        val cached = dao.getCachedChapter(book, chapter, normalizedTr)
        if (cached != null) {
            try {
                val verseTexts = verseListAdapter.fromJson(cached.versesJson)
                if (verseTexts != null && verseTexts.isNotEmpty()) {
                    val verses = verseTexts.mapIndexed { index, text ->
                        Verse(book, chapter, index + 1, text, translation.uppercase())
                    }
                    return@withContext ChapterContent(
                        book = book,
                        chapter = chapter,
                        translation = translation.uppercase(),
                        verses = verses,
                        isOffline = false
                    )
                }
            } catch (ignored: Exception) {
                // If deserialization fails, continue to API
            }
        }

        // 2. If translation has local preloaded sample (KJV, NKJV, TWI)
        val preloadedSample = SampleScriptures.getOfflineVerses(book, chapter, translation)
        if (preloadedSample != null) {
            val label = when (normalizedTr) {
                "twi", "asante twi" -> "Asante Twi (Twerɛ Kronkron - Ghana)"
                "nkjv" -> "New King James Version (NKJV)"
                else -> "King James Version (Offline KJV)"
            }
            return@withContext ChapterContent(
                book = book,
                chapter = chapter,
                translation = translation.uppercase(),
                verses = preloadedSample,
                isOffline = true,
                note = label
            )
        }

        // For Asante Twi, provide localized scripture text
        if (normalizedTr in listOf("twi", "asante twi")) {
            val twiVerses = SampleScriptures.createOfflineFallbackVerses(book, chapter, "TWI")
            return@withContext ChapterContent(
                book = book,
                chapter = chapter,
                translation = "TWI",
                verses = twiVerses,
                isOffline = true,
                note = "Asante Twi (Twerɛ Kronkron - Ghana)"
            )
        }

        // 3. Try Remote API (Primary bible-api.com)
        try {
            val apiTr = when (normalizedTr) {
                "nkjv" -> "kjv" // fallback query
                else -> normalizedTr
            }
            val response = BibleApiClient.apiService.getChapter(
                reference = "$book $chapter",
                translation = apiTr
            )
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                val apiVerses = body.verses
                if (apiVerses != null && apiVerses.isNotEmpty()) {
                    val verses = apiVerses.map {
                        Verse(
                            book = book,
                            chapter = chapter,
                            verseNumber = it.verse,
                            text = it.text.trim(),
                            translation = (body.translationId ?: translation).uppercase()
                        )
                    }

                    // Cache in Room
                    try {
                        val textList = verses.map { it.text }
                        val json = verseListAdapter.toJson(textList)
                        dao.insertCachedChapter(
                            CachedChapterEntity(
                                book = book,
                                chapter = chapter,
                                translation = normalizedTr,
                                versesJson = json
                            )
                        )
                    } catch (ignored: Exception) {}

                    return@withContext ChapterContent(
                        book = book,
                        chapter = chapter,
                        translation = (body.translationId ?: translation).uppercase(),
                        verses = verses,
                        isOffline = false,
                        note = body.translationName ?: translation.uppercase()
                    )
                }
            }
        } catch (e: Exception) {
            // Primary network attempt failed
        }

        // 4. Secondary CDN API Fallback (wldeh/bible-api CDN)
        try {
            val versionMap = mapOf(
                "kjv" to "en-kjv",
                "web" to "en-web",
                "bbe" to "en-bbe",
                "asv" to "en-asv",
                "nkjv" to "en-kjv"
            )
            val cdnVer = versionMap[normalizedTr] ?: "en-web"
            val bookObj = com.example.data.model.BibleCatalog.findBook(book)
            val bookId = bookObj?.id ?: book.lowercase().replace(" ", "")
            val cdnUrl = "https://cdn.jsdelivr.net/gh/wldeh/bible-api/bibles/$cdnVer/books/$bookId/chapters/$chapter.json"

            val cdnResponse = BibleApiClient.apiService.getCdnChapter(cdnUrl)
            if (cdnResponse.isSuccessful && cdnResponse.body() != null) {
                val cdnVerses = cdnResponse.body()?.verses
                if (cdnVerses != null && cdnVerses.isNotEmpty()) {
                    val verses = cdnVerses.map {
                        Verse(
                            book = book,
                            chapter = chapter,
                            verseNumber = it.verse,
                            text = it.text.trim(),
                            translation = translation.uppercase()
                        )
                    }

                    // Cache in Room
                    try {
                        val textList = verses.map { it.text }
                        val json = verseListAdapter.toJson(textList)
                        dao.insertCachedChapter(
                            CachedChapterEntity(
                                book = book,
                                chapter = chapter,
                                translation = normalizedTr,
                                versesJson = json
                            )
                        )
                    } catch (ignored: Exception) {}

                    return@withContext ChapterContent(
                        book = book,
                        chapter = chapter,
                        translation = translation.uppercase(),
                        verses = verses,
                        isOffline = false,
                        note = "$translation (Sacred Scripture)"
                    )
                }
            }
        } catch (e: Exception) {
            // Secondary CDN fallback failed
        }

        // 5. Graceful offline fallback
        val fallback = SampleScriptures.createOfflineFallbackVerses(book, chapter, translation)
        ChapterContent(
            book = book,
            chapter = chapter,
            translation = translation.uppercase(),
            verses = fallback,
            isOffline = true,
            note = "Offline scripture preview. Connect to network for full translation."
        )
    }

    suspend fun toggleBookmark(verse: Verse) = withContext(Dispatchers.IO) {
        dao.insertBookmark(
            BookmarkEntity(
                book = verse.book,
                chapter = verse.chapter,
                verse = verse.verseNumber,
                text = verse.text,
                translation = verse.translation
            )
        )
    }

    suspend fun removeBookmark(book: String, chapter: Int, verse: Int, translation: String) = withContext(Dispatchers.IO) {
        dao.deleteBookmark(book, chapter, verse, translation)
    }

    suspend fun deleteBookmarkById(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteBookmarkById(id)
    }

    suspend fun setHighlight(book: String, chapter: Int, verse: Int, colorHex: String) = withContext(Dispatchers.IO) {
        dao.insertHighlight(
            HighlightEntity(
                book = book,
                chapter = chapter,
                verse = verse,
                colorHex = colorHex
            )
        )
    }

    suspend fun removeHighlight(book: String, chapter: Int, verse: Int) = withContext(Dispatchers.IO) {
        dao.deleteHighlight(book, chapter, verse)
    }

    suspend fun saveNote(
        book: String,
        chapter: Int,
        verse: Int,
        note: String,
        verseText: String,
        translation: String
    ) = withContext(Dispatchers.IO) {
        dao.insertNote(
            VerseNoteEntity(
                book = book,
                chapter = chapter,
                verse = verse,
                note = note,
                verseText = verseText,
                translation = translation
            )
        )
    }

    suspend fun deleteNoteById(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteNoteById(id)
    }

    suspend fun toggleReadingPlanDay(planId: String, dayIndex: Int, isCompleted: Boolean) = withContext(Dispatchers.IO) {
        dao.setReadingPlanProgress(
            ReadingPlanProgressEntity(
                planId = planId,
                dayIndex = dayIndex,
                isCompleted = isCompleted
            )
        )
    }

    // --- Translations & Scriptures Room Operations ---
    val allTranslations: Flow<List<com.example.data.model.TranslationEntity>> = dao.getAllTranslations()

    fun getScripturesForChapter(bookName: String, chapter: Int, translation: String): Flow<List<com.example.data.model.ScriptureEntity>> {
        return dao.getScripturesForChapter(bookName, chapter, translation)
    }

    suspend fun getRandomDailyScripture(): com.example.data.model.ScriptureEntity? = withContext(Dispatchers.IO) {
        dao.getRandomDailyScripture()
    }

    suspend fun seedDatabaseIfNeeded() = withContext(Dispatchers.IO) {
        if (dao.getTranslationCount() == 0) {
            val defaultTranslations = listOf(
                com.example.data.model.TranslationEntity(
                    code = "ASW",
                    name = "Asante Twi (Twerɛ Kronkron)",
                    shortName = "Twi",
                    language = "Asante Twi / Akan",
                    languageCode = "ak",
                    description = "Authentic Akan Sacred Scriptures with native phonetic audio synthesizer",
                    isAudioSupported = true,
                    isOfflineAvailable = true,
                    totalBooks = 66,
                    isDefault = true,
                    orderIndex = 0
                ),
                com.example.data.model.TranslationEntity(
                    code = "KJV",
                    name = "King James Version",
                    shortName = "KJV",
                    language = "English",
                    languageCode = "en",
                    description = "The classic Authorized King James Version (1611) with historic reverent prose",
                    isAudioSupported = true,
                    isOfflineAvailable = true,
                    totalBooks = 66,
                    isDefault = false,
                    orderIndex = 1
                ),
                com.example.data.model.TranslationEntity(
                    code = "WEB",
                    name = "World English Bible",
                    shortName = "WEB",
                    language = "English",
                    languageCode = "en",
                    description = "Modern public domain translation written in clear, accessible English",
                    isAudioSupported = true,
                    isOfflineAvailable = true,
                    totalBooks = 66,
                    isDefault = false,
                    orderIndex = 2
                ),
                com.example.data.model.TranslationEntity(
                    code = "BBE",
                    name = "Bible in Basic English",
                    shortName = "BBE",
                    language = "English",
                    languageCode = "en",
                    description = "Simplified English translation using 1,000 foundational words",
                    isAudioSupported = true,
                    isOfflineAvailable = true,
                    totalBooks = 66,
                    isDefault = false,
                    orderIndex = 3
                ),
                com.example.data.model.TranslationEntity(
                    code = "NKJV",
                    name = "New King James Version",
                    shortName = "NKJV",
                    language = "English",
                    languageCode = "en",
                    description = "Modern language update preserving classic KJV flow and dignity",
                    isAudioSupported = true,
                    isOfflineAvailable = true,
                    totalBooks = 66,
                    isDefault = false,
                    orderIndex = 4
                )
            )
            dao.insertTranslations(defaultTranslations)
        }

        if (dao.getScriptureCount() == 0) {
            val sampleScriptures = listOf(
                com.example.data.model.ScriptureEntity(
                    bookId = "JHN",
                    bookName = "John",
                    testament = "New Testament",
                    chapter = 3,
                    verse = 16,
                    text = "For God so loved the world, that he gave his only begotten Son, that whosoever believeth in him should not perish, but have everlasting life.",
                    translation = "KJV",
                    sectionTitle = "God's Love for the World",
                    isDailyVerseEligible = true
                ),
                com.example.data.model.ScriptureEntity(
                    bookId = "JHN",
                    bookName = "John",
                    testament = "New Testament",
                    chapter = 3,
                    verse = 16,
                    text = "Na sɛnea Onyankopɔn dɔɔ wiase ni, sɛ ɔde ne Ba a ɔwooo no koro no mae, na obiara a ɔgye no di no anyera, na mmom wanya daa nkwa.",
                    translation = "ASW",
                    sectionTitle = "Onyankopɔn Dɔ a Ɔdɔɔ Wiase",
                    isDailyVerseEligible = true
                ),
                com.example.data.model.ScriptureEntity(
                    bookId = "PSA",
                    bookName = "Psalms",
                    testament = "Old Testament",
                    chapter = 23,
                    verse = 1,
                    text = "The LORD is my shepherd; I shall not want.",
                    translation = "KJV",
                    sectionTitle = "The Lord is My Shepherd",
                    isDailyVerseEligible = true
                ),
                com.example.data.model.ScriptureEntity(
                    bookId = "PSA",
                    bookName = "Dwom",
                    testament = "Old Testament",
                    chapter = 23,
                    verse = 1,
                    text = "AWURADE ne me hwɛfo, hwee renhia me.",
                    translation = "ASW",
                    sectionTitle = "AWURADE Ne Me Hwɛfo",
                    isDailyVerseEligible = true
                ),
                com.example.data.model.ScriptureEntity(
                    bookId = "MAT",
                    bookName = "Matthew",
                    testament = "New Testament",
                    chapter = 6,
                    verse = 9,
                    text = "After this manner therefore pray ye: Our Father which art in heaven, Hallowed be thy name.",
                    translation = "KJV",
                    sectionTitle = "The Lord's Prayer",
                    isDailyVerseEligible = true
                ),
                com.example.data.model.ScriptureEntity(
                    bookId = "MAT",
                    bookName = "Matefo",
                    testament = "New Testament",
                    chapter = 6,
                    verse = 9,
                    text = "Enti mobɔ mpae a, mommɔ no sɛɛ: Yɛn Agya a wowɔ soro, wo din ho nte.",
                    translation = "ASW",
                    sectionTitle = "Awurade Mpaebɔ",
                    isDailyVerseEligible = true
                ),
                com.example.data.model.ScriptureEntity(
                    bookId = "ROM",
                    bookName = "Romans",
                    testament = "New Testament",
                    chapter = 8,
                    verse = 38,
                    text = "For I am persuaded, that neither death, nor life, nor angels, nor principalities, nor powers, nor things present, nor things to come,",
                    translation = "KJV",
                    sectionTitle = "More Than Conquerors",
                    isDailyVerseEligible = true
                ),
                com.example.data.model.ScriptureEntity(
                    bookId = "ROM",
                    bookName = "Romafo",
                    testament = "New Testament",
                    chapter = 8,
                    verse = 38,
                    text = "Na migye midi paa sɛ, owuo oo, nkwa oo, abɔfo oo, atumfo oo, nneɛma a ɛwɔ hɔ seesei oo, nneɛma a ɛbɛba oo,",
                    translation = "ASW",
                    sectionTitle = "Nkonimdi a Ɛsen Nkonimdi",
                    isDailyVerseEligible = true
                )
            )
            dao.insertScriptures(sampleScriptures)
        }
    }
}
