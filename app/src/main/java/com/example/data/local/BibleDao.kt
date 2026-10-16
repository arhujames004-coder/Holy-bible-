package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.BookmarkEntity
import com.example.data.model.CachedChapterEntity
import com.example.data.model.HighlightEntity
import com.example.data.model.ReadingPlanProgressEntity
import com.example.data.model.VerseNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BibleDao {

    // --- Bookmarks ---
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE book = :book AND chapter = :chapter AND verse = :verse AND translation = :translation")
    suspend fun deleteBookmark(book: String, chapter: Int, verse: Int, translation: String)

    @Query("DELETE FROM bookmarks WHERE id = :id")
    suspend fun deleteBookmarkById(id: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE book = :book AND chapter = :chapter AND verse = :verse AND translation = :translation)")
    fun isBookmarked(book: String, chapter: Int, verse: Int, translation: String): Flow<Boolean>

    // --- Highlights ---
    @Query("SELECT * FROM highlights ORDER BY timestamp DESC")
    fun getAllHighlights(): Flow<List<HighlightEntity>>

    @Query("SELECT * FROM highlights WHERE book = :book AND chapter = :chapter")
    fun getHighlightsForChapter(book: String, chapter: Int): Flow<List<HighlightEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHighlight(highlight: HighlightEntity)

    @Query("DELETE FROM highlights WHERE book = :book AND chapter = :chapter AND verse = :verse")
    suspend fun deleteHighlight(book: String, chapter: Int, verse: Int)

    // --- Notes ---
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<VerseNoteEntity>>

    @Query("SELECT * FROM notes WHERE book = :book AND chapter = :chapter")
    fun getNotesForChapter(book: String, chapter: Int): Flow<List<VerseNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: VerseNoteEntity)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    // --- Chapter Cache ---
    @Query("SELECT * FROM cached_chapters WHERE book = :book AND chapter = :chapter AND translation = :translation LIMIT 1")
    suspend fun getCachedChapter(book: String, chapter: Int, translation: String): CachedChapterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedChapter(cachedChapter: CachedChapterEntity)

    // --- Reading Plan Progress ---
    @Query("SELECT * FROM reading_plan_progress")
    fun getAllReadingPlanProgress(): Flow<List<ReadingPlanProgressEntity>>

    @Query("SELECT * FROM reading_plan_progress WHERE planId = :planId")
    fun getReadingPlanProgress(planId: String): Flow<List<ReadingPlanProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setReadingPlanProgress(progress: ReadingPlanProgressEntity)

    // --- Translations ---
    @Query("SELECT * FROM translations ORDER BY orderIndex ASC, name ASC")
    fun getAllTranslations(): Flow<List<com.example.data.model.TranslationEntity>>

    @Query("SELECT * FROM translations WHERE code = :code LIMIT 1")
    fun getTranslationByCode(code: String): Flow<com.example.data.model.TranslationEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(translation: com.example.data.model.TranslationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslations(translations: List<com.example.data.model.TranslationEntity>)

    @Query("SELECT COUNT(*) FROM translations")
    suspend fun getTranslationCount(): Int

    // --- Scriptures ---
    @Query("SELECT * FROM scriptures WHERE translation = :translation AND bookName = :bookName AND chapter = :chapter ORDER BY verse ASC")
    fun getScripturesForChapter(bookName: String, chapter: Int, translation: String): Flow<List<com.example.data.model.ScriptureEntity>>

    @Query("SELECT * FROM scriptures WHERE translation = :translation AND bookName = :bookName ORDER BY chapter ASC, verse ASC")
    fun getScripturesByBook(bookName: String, translation: String): Flow<List<com.example.data.model.ScriptureEntity>>

    @Query("SELECT * FROM scriptures WHERE isDailyVerseEligible = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomDailyScripture(): com.example.data.model.ScriptureEntity?

    @Query("SELECT * FROM scriptures WHERE translation = :translation AND text LIKE '%' || :query || '%' ORDER BY chapter ASC, verse ASC")
    fun searchScriptures(query: String, translation: String): Flow<List<com.example.data.model.ScriptureEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScripture(scripture: com.example.data.model.ScriptureEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScriptures(scriptures: List<com.example.data.model.ScriptureEntity>)

    @Query("SELECT COUNT(*) FROM scriptures")
    suspend fun getScriptureCount(): Int
}
