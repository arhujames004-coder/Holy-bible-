package com.example.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookmarks",
    indices = [Index(value = ["book", "chapter", "verse", "translation"], unique = true)]
)
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val book: String,
    val chapter: Int,
    val verse: Int,
    val text: String,
    val translation: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "highlights",
    indices = [Index(value = ["book", "chapter", "verse"], unique = true)]
)
data class HighlightEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val book: String,
    val chapter: Int,
    val verse: Int,
    val colorHex: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "notes",
    indices = [Index(value = ["book", "chapter", "verse"])]
)
data class VerseNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val book: String,
    val chapter: Int,
    val verse: Int,
    val note: String,
    val verseText: String,
    val translation: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "cached_chapters",
    indices = [Index(value = ["book", "chapter", "translation"], unique = true)]
)
data class CachedChapterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val book: String,
    val chapter: Int,
    val translation: String,
    val versesJson: String,
    val cachedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "reading_plan_progress",
    indices = [Index(value = ["planId", "dayIndex"], unique = true)]
)
data class ReadingPlanProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val planId: String,
    val dayIndex: Int,
    val isCompleted: Boolean,
    val completedAt: Long = System.currentTimeMillis()
)

/**
 * Room Entity representing a Translation version (e.g. Asante Twi, KJV, WEB).
 * Includes detailed localization metadata, audio capability flags, and offline availability.
 */
@Entity(
    tableName = "translations"
)
data class TranslationEntity(
    @PrimaryKey val code: String, // e.g. "ASW", "KJV", "WEB", "BBE", "NKJV"
    val name: String,             // e.g. "Asante Twi (Twerɛ Kronkron)", "King James Version"
    val shortName: String,        // e.g. "Twi", "KJV", "WEB"
    val language: String,         // e.g. "Asante Twi / Akan", "English"
    val languageCode: String,     // e.g. "ak", "en"
    val description: String,      // Rich description of the translation
    val isAudioSupported: Boolean = true, // Whether audio narration / TTS is supported
    val isOfflineAvailable: Boolean = true, // Whether full offline texts are bundled
    val totalBooks: Int = 66,     // Number of canonical books
    val isDefault: Boolean = false,
    val orderIndex: Int = 0,
    val copyrightOrSource: String = "Public Domain / Sacred Heritage",
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Room Entity representing individual Scripture verses with cross-referencing,
 * testament classification, daily verse eligibility, audio references, and pericope headings.
 */
@Entity(
    tableName = "scriptures",
    indices = [
        Index(value = ["translation", "bookName", "chapter", "verse"], unique = true),
        Index(value = ["translation", "isDailyVerseEligible"]),
        Index(value = ["bookName", "chapter"])
    ]
)
data class ScriptureEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookId: String,           // e.g. "JHN", "GEN", "PSA", "MAT", "ROM"
    val bookName: String,         // e.g. "John", "Genesis", "Dwom / Psalms", "Matthew"
    val testament: String,        // "Old Testament" or "New Testament"
    val chapter: Int,             // 1-indexed chapter number
    val verse: Int,               // 1-indexed verse number
    val text: String,             // Verse scripture text
    val translation: String,      // Translation code matching TranslationEntity.code (e.g. "ASW", "KJV")
    val sectionTitle: String? = null, // Optional heading / pericope (e.g. "The Word Became Flesh")
    val isDailyVerseEligible: Boolean = true, // Whether eligible for Verse of the Day selection
    val isFavorite: Boolean = false,
    val audioUrl: String? = null, // Optional audio asset / streaming URL
    val notes: String? = null,    // Optional devotional commentary note
    val timestamp: Long = System.currentTimeMillis()
)
