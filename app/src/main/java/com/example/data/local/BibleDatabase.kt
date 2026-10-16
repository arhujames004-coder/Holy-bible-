package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.BookmarkEntity
import com.example.data.model.CachedChapterEntity
import com.example.data.model.HighlightEntity
import com.example.data.model.ReadingPlanProgressEntity
import com.example.data.model.ScriptureEntity
import com.example.data.model.TranslationEntity
import com.example.data.model.VerseNoteEntity

@Database(
    entities = [
        BookmarkEntity::class,
        HighlightEntity::class,
        VerseNoteEntity::class,
        CachedChapterEntity::class,
        ReadingPlanProgressEntity::class,
        ScriptureEntity::class,
        TranslationEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class BibleDatabase : RoomDatabase() {
    abstract fun bibleDao(): BibleDao

    companion object {
        @Volatile
        private var INSTANCE: BibleDatabase? = null

        fun getDatabase(context: Context): BibleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BibleDatabase::class.java,
                    "holy_bible_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
