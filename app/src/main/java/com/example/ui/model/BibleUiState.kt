package com.example.ui.model

import com.example.data.model.BookmarkEntity
import com.example.data.model.ChapterContent
import com.example.data.model.HighlightEntity
import com.example.data.model.ReadingPlanProgressEntity
import com.example.data.model.Verse
import com.example.data.model.VerseNoteEntity
import com.example.ui.theme.BibleThemeMode

enum class MainNavTab(val label: String) {
    READER("Scripture"),
    BOOKS("Books"),
    SONGS_PARABLES("Songs & Parables"),
    SAVED("Saved"),
    PLANS("Plans"),
    SEARCH("Search")
}

data class BibleUiState(
    val currentBook: String = "John",
    val currentChapter: Int = 3,
    val currentTranslation: String = "KJV",
    val currentTestament: String = "NT",
    val chapterContent: ChapterContent? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val targetVerseHighlight: Int? = 16,
    val activeTab: MainNavTab = MainNavTab.READER,
    val themeMode: BibleThemeMode = BibleThemeMode.SANCTUARY_DARK,
    val fontSizeSp: Float = 18f,
    val useSerifFont: Boolean = true,
    val selectedVerseForAction: Verse? = null,
    val selectedVerseForExplanation: Verse? = null,
    val isTtsSpeaking: Boolean = false,
    val ttsSpeakingVerseNumber: Int? = null,
    val ttsSpeakingTitle: String? = null,
    val speechRate: Float = 1.0f,
    val ttsStatusMessage: String? = null,
    val isPlayingMelody: Boolean = false,
    val currentSongPlayingId: String? = null,
    val searchQuery: String = "",
    val searchResults: List<Verse> = emptyList(),
    val isSearching: Boolean = false,
    val booksFilterText: String = "",
    val showSettingsDialog: Boolean = false,
    val showVerseOfDayBanner: Boolean = true,
    // Device Custom Wallpaper support
    val customWallpaperUri: String? = null,
    val presetWallpaperResId: Int? = null,
    val wallpaperDimAlpha: Float = 0.65f,
    val wallpaperBlur: Boolean = false,
    // Phone Speaker & Audio hardware support
    val speakerVolumeLevel: Float = 0.8f,
    val speakerDeviceName: String = "🔊 Built-in Phone Speaker",
    val speakerBoostEnabled: Boolean = false,
    val isTestingSpeaker: Boolean = false,
    val isAsanteTwiAudio: Boolean = false,
    val showWallpaperAndSpeakerDialog: Boolean = false
)
