package com.example.viewmodel

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.BibleAudioPlayer
import com.example.data.local.BibleDatabase
import com.example.data.local.SampleScriptures
import com.example.data.model.BibleCatalog
import com.example.data.model.BibleSongItem
import com.example.data.model.BookmarkEntity
import com.example.data.model.HighlightEntity
import com.example.data.model.ReadingPlanProgressEntity
import com.example.data.model.Verse
import com.example.data.model.VerseNoteEntity
import com.example.data.repository.BibleRepository
import com.example.ui.model.BibleUiState
import com.example.ui.model.MainNavTab
import com.example.ui.theme.BibleThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BibleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BibleRepository
    private val audioPlayer: BibleAudioPlayer
    private val speakerHelper: com.example.audio.DeviceSpeakerHelper = com.example.audio.DeviceSpeakerHelper(application)

    private val _uiState = MutableStateFlow(
        BibleUiState(
            speakerVolumeLevel = speakerHelper.getStreamVolumePercent(),
            speakerDeviceName = speakerHelper.getActiveAudioDeviceName()
        )
    )
    val uiState: StateFlow<BibleUiState> = _uiState.asStateFlow()

    init {
        val db = BibleDatabase.getDatabase(application)
        repository = BibleRepository(db.bibleDao())

        audioPlayer = BibleAudioPlayer(
            context = application,
            onVerseStart = { verseNum ->
                _uiState.update {
                    it.copy(
                        isTtsSpeaking = true,
                        ttsSpeakingVerseNumber = verseNum
                    )
                }
            },
            onPlaybackDone = {
                _uiState.update {
                    it.copy(
                        isTtsSpeaking = false,
                        isPlayingMelody = false,
                        currentSongPlayingId = null,
                        ttsSpeakingVerseNumber = null,
                        ttsSpeakingTitle = null,
                        isAsanteTwiAudio = false,
                        isTestingSpeaker = false
                    )
                }
            },
            onError = { msg ->
                _uiState.update {
                    it.copy(
                        ttsStatusMessage = msg,
                        isTtsSpeaking = false,
                        isPlayingMelody = false,
                        isTestingSpeaker = false
                    )
                }
            }
        )

        // Seed Room translations and scriptures if needed
        viewModelScope.launch {
            repository.seedDatabaseIfNeeded()
        }

        // Load initial chapter: John 3
        loadChapter("John", 3, targetVerse = 16)
    }

    val translationsList: StateFlow<List<com.example.data.model.TranslationEntity>> = repository.allTranslations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarks: StateFlow<List<BookmarkEntity>> = repository.allBookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val highlights: StateFlow<List<HighlightEntity>> = repository.allHighlights
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notes: StateFlow<List<VerseNoteEntity>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val readingProgress: StateFlow<List<ReadingPlanProgressEntity>> = repository.allReadingProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun loadChapter(book: String, chapter: Int, targetVerse: Int? = null) {
        val currentBookObj = BibleCatalog.findBook(book)
        val testament = currentBookObj?.testament ?: _uiState.value.currentTestament

        _uiState.update {
            it.copy(
                currentBook = book,
                currentChapter = chapter,
                currentTestament = testament,
                targetVerseHighlight = targetVerse,
                isLoading = true,
                errorMessage = null,
                activeTab = MainNavTab.READER
            )
        }

        viewModelScope.launch {
            try {
                val content = repository.loadChapter(book, chapter, _uiState.value.currentTranslation)
                _uiState.update {
                    it.copy(
                        chapterContent = content,
                        isLoading = false,
                        targetVerseHighlight = targetVerse
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Could not load chapter: ${e.message}"
                    )
                }
            }
        }
    }

    fun stepChapter(direction: Int) {
        val currentBookName = _uiState.value.currentBook
        val currentCh = _uiState.value.currentChapter
        val bookIndex = BibleCatalog.ALL_BOOKS.indexOfFirst { it.name.equals(currentBookName, ignoreCase = true) }

        if (bookIndex == -1) return
        val currentBook = BibleCatalog.ALL_BOOKS[bookIndex]

        val targetCh = currentCh + direction
        when {
            targetCh < 1 -> {
                if (bookIndex > 0) {
                    val prevBook = BibleCatalog.ALL_BOOKS[bookIndex - 1]
                    loadChapter(prevBook.name, prevBook.chapters)
                }
            }
            targetCh > currentBook.chapters -> {
                if (bookIndex < BibleCatalog.ALL_BOOKS.size - 1) {
                    val nextBook = BibleCatalog.ALL_BOOKS[bookIndex + 1]
                    loadChapter(nextBook.name, 1)
                }
            }
            else -> {
                loadChapter(currentBookName, targetCh)
            }
        }
    }

    fun setTranslation(translation: String) {
        if (_uiState.value.currentTranslation == translation) return
        _uiState.update { it.copy(currentTranslation = translation) }
        loadChapter(_uiState.value.currentBook, _uiState.value.currentChapter, _uiState.value.targetVerseHighlight)
    }

    fun setTestament(testament: String) {
        _uiState.update { it.copy(currentTestament = testament) }
    }

    fun setTab(tab: MainNavTab) {
        _uiState.update { it.copy(activeTab = tab) }
    }

    fun setThemeMode(themeMode: BibleThemeMode) {
        _uiState.update { it.copy(themeMode = themeMode) }
    }

    fun setFontSize(fontSizeSp: Float) {
        _uiState.update { it.copy(fontSizeSp = fontSizeSp) }
    }

    fun setUseSerifFont(serif: Boolean) {
        _uiState.update { it.copy(useSerifFont = serif) }
    }

    fun openVerseAction(verse: Verse) {
        _uiState.update { it.copy(selectedVerseForAction = verse) }
    }

    fun dismissVerseAction() {
        _uiState.update { it.copy(selectedVerseForAction = null) }
    }

    fun explainVerse(verse: Verse) {
        _uiState.update {
            it.copy(
                selectedVerseForAction = null,
                selectedVerseForExplanation = verse
            )
        }
    }

    fun dismissExplainDialog() {
        _uiState.update { it.copy(selectedVerseForExplanation = null) }
    }

    fun toggleBookmark(verse: Verse) {
        viewModelScope.launch {
            val isAlreadyBookmarked = bookmarks.value.any {
                it.book == verse.book && it.chapter == verse.chapter && it.verse == verse.verseNumber
            }
            if (isAlreadyBookmarked) {
                repository.removeBookmark(verse.book, verse.chapter, verse.verseNumber, verse.translation)
            } else {
                repository.toggleBookmark(verse)
            }
        }
    }

    fun deleteBookmark(id: Long) {
        viewModelScope.launch {
            repository.deleteBookmarkById(id)
        }
    }

    fun setHighlightForSelected(colorHex: String) {
        val verse = _uiState.value.selectedVerseForAction ?: return
        viewModelScope.launch {
            repository.setHighlight(verse.book, verse.chapter, verse.verseNumber, colorHex)
            dismissVerseAction()
        }
    }

    fun removeHighlightForSelected() {
        val verse = _uiState.value.selectedVerseForAction ?: return
        viewModelScope.launch {
            repository.removeHighlight(verse.book, verse.chapter, verse.verseNumber)
            dismissVerseAction()
        }
    }

    fun saveNoteForSelected(noteText: String) {
        val verse = _uiState.value.selectedVerseForAction ?: return
        if (noteText.isBlank()) return
        viewModelScope.launch {
            repository.saveNote(
                book = verse.book,
                chapter = verse.chapter,
                verse = verse.verseNumber,
                note = noteText.trim(),
                verseText = verse.text,
                translation = verse.translation
            )
            dismissVerseAction()
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNoteById(id)
        }
    }

    fun toggleReadingPlanDay(planId: String, dayIndex: Int, completed: Boolean) {
        viewModelScope.launch {
            repository.toggleReadingPlanDay(planId, dayIndex, completed)
        }
    }

    fun openVerseOfDay() {
        val list = SampleScriptures.VERSE_OF_THE_DAY_LIST
        val dayIndex = (System.currentTimeMillis() / (1000 * 60 * 60 * 24)).toInt() % list.size
        val vod = list[dayIndex]
        loadChapter(vod.book, vod.chapter, targetVerse = vod.verseNumber)
    }

    fun setBooksFilter(filter: String) {
        _uiState.update { it.copy(booksFilterText = filter) }
    }

    fun toggleSettingsDialog(show: Boolean) {
        _uiState.update { it.copy(showSettingsDialog = show) }
    }

    fun toggleWallpaperAndSpeakerDialog(show: Boolean) {
        if (show) {
            refreshSpeakerDeviceInfo()
        }
        _uiState.update { it.copy(showWallpaperAndSpeakerDialog = show) }
    }

    fun setCustomWallpaper(uri: String?) {
        _uiState.update {
            it.copy(
                customWallpaperUri = uri,
                presetWallpaperResId = null
            )
        }
    }

    fun setPresetWallpaper(resId: Int?) {
        _uiState.update {
            it.copy(
                presetWallpaperResId = resId,
                customWallpaperUri = null
            )
        }
    }

    fun setWallpaperDimAlpha(alpha: Float) {
        _uiState.update { it.copy(wallpaperDimAlpha = alpha.coerceIn(0.1f, 0.95f)) }
    }

    fun setWallpaperBlur(enabled: Boolean) {
        _uiState.update { it.copy(wallpaperBlur = enabled) }
    }

    fun clearWallpaper() {
        _uiState.update {
            it.copy(
                customWallpaperUri = null,
                presetWallpaperResId = null
            )
        }
    }

    fun setSpeakerVolume(percent: Float) {
        speakerHelper.setStreamVolumePercent(percent)
        _uiState.update { it.copy(speakerVolumeLevel = percent.coerceIn(0.0f, 1.0f)) }
    }

    fun refreshSpeakerDeviceInfo() {
        val currentVol = speakerHelper.getStreamVolumePercent()
        val deviceName = speakerHelper.getActiveAudioDeviceName()
        _uiState.update {
            it.copy(
                speakerVolumeLevel = currentVol,
                speakerDeviceName = deviceName
            )
        }
    }

    fun toggleSpeakerBoost(enabled: Boolean) {
        _uiState.update { it.copy(speakerBoostEnabled = enabled) }
        if (enabled) {
            // Set speaker stream to high volume when boost is enabled
            speakerHelper.setStreamVolumePercent(1.0f)
            _uiState.update { it.copy(speakerVolumeLevel = 1.0f) }
        }
    }

    fun testPhoneSpeaker() {
        _uiState.update { it.copy(isTestingSpeaker = true) }
        audioPlayer.playSpeakerTestChime(
            onDone = {
                _uiState.update { it.copy(isTestingSpeaker = false) }
            }
        )
    }

    fun dismissVerseOfDayBanner() {
        _uiState.update { it.copy(showVerseOfDayBanner = false) }
    }

    fun performSearch(query: String) {
        val q = query.trim()
        _uiState.update { it.copy(searchQuery = q, isSearching = true) }
        if (q.isEmpty()) {
            _uiState.update { it.copy(searchResults = emptyList(), isSearching = false) }
            return
        }

        // Check if query is a reference e.g., "John 3:16" or "Gen 1:1" or "Romans 8"
        val refPattern = Regex("""^([1-3]?\s?[A-Za-z]+)\s+(\d+)(?::(\d+))?$""")
        val match = refPattern.matchEntire(q)
        if (match != null) {
            val bookNameRaw = match.groupValues[1]
            val chapterNum = match.groupValues[2].toIntOrNull() ?: 1
            val verseNum = match.groupValues[3].toIntOrNull()

            val book = BibleCatalog.findBook(bookNameRaw)
            if (book != null && chapterNum in 1..book.chapters) {
                loadChapter(book.name, chapterNum, targetVerse = verseNum)
                _uiState.update { it.copy(isSearching = false) }
                return
            }
        }

        // Keyword search across loaded content, offline texts, and current chapter
        viewModelScope.launch(Dispatchers.Default) {
            val matches = mutableListOf<Verse>()
            val lowerQ = q.lowercase()

            // Search in offline prominent scriptures
            for (bookObj in BibleCatalog.ALL_BOOKS) {
                val sampleVerses = SampleScriptures.getOfflineVerses(bookObj.name, 1, _uiState.value.currentTranslation) ?: emptyList()
                for (v in sampleVerses) {
                    if (v.text.lowercase().contains(lowerQ)) {
                        matches.add(v)
                        if (matches.size >= 40) break
                    }
                }
                if (matches.size >= 40) break
            }

            // Also check current loaded chapter
            _uiState.value.chapterContent?.verses?.forEach { v ->
                if (v.text.lowercase().contains(lowerQ) && matches.none { it.reference == v.reference }) {
                    matches.add(0, v)
                }
            }

            _uiState.update {
                it.copy(
                    searchResults = matches.take(50),
                    isSearching = false
                )
            }
        }
    }

    fun speakVerse(verse: Verse) {
        val isTwi = verse.translation.uppercase() in listOf("TWI", "ASANTE TWI") ||
                _uiState.value.currentTranslation.uppercase() in listOf("TWI", "ASANTE TWI")
        val speech = if (isTwi) {
            "${verse.book} ti ${verse.chapter}, asɛm ${verse.verseNumber}. ${verse.text}"
        } else {
            "${verse.book} chapter ${verse.chapter}, verse ${verse.verseNumber}. ${verse.text}"
        }
        val utteranceId = "single_${verse.book}_${verse.chapter}_v_${verse.verseNumber}_last_true"

        _uiState.update {
            it.copy(
                isTtsSpeaking = true,
                isPlayingMelody = false,
                currentSongPlayingId = null,
                ttsSpeakingTitle = verse.reference + if (isTwi) " (Asante Twi)" else "",
                ttsSpeakingVerseNumber = verse.verseNumber,
                isAsanteTwiAudio = isTwi
            )
        }
        audioPlayer.speak(speech, utteranceId, TextToSpeech.QUEUE_FLUSH, isTwi = isTwi)
    }

    fun speakCurrentChapter() {
        val content = _uiState.value.chapterContent ?: return
        if (content.verses.isEmpty()) return

        val isTwi = _uiState.value.currentTranslation.uppercase() in listOf("TWI", "ASANTE TWI")
        val totalVerses = content.verses.size
        _uiState.update {
            it.copy(
                isTtsSpeaking = true,
                isPlayingMelody = false,
                currentSongPlayingId = null,
                ttsSpeakingTitle = "${content.book} ${content.chapter}" + if (isTwi) " (Asante Twi)" else "",
                ttsSpeakingVerseNumber = 1,
                isAsanteTwiAudio = isTwi
            )
        }

        // Introduction
        val introId = "intro_${content.book}_${content.chapter}_v_1"
        val introSpeech = if (isTwi) "${content.book} Ti ${content.chapter}." else "${content.book} Chapter ${content.chapter}."
        audioPlayer.speak(introSpeech, introId, TextToSpeech.QUEUE_FLUSH, isTwi = isTwi)

        // Queue all verses sequentially with clean individual utterance tracking
        content.verses.forEachIndexed { index, verse ->
            val isLast = index == totalVerses - 1
            val utteranceId = "ch_${content.book}_${content.chapter}_v_${verse.verseNumber}_last_$isLast"
            val textToSpeak = if (isTwi) "Asɛm ${verse.verseNumber}. ${verse.text}" else "Verse ${verse.verseNumber}. ${verse.text}"
            audioPlayer.speak(textToSpeak, utteranceId, TextToSpeech.QUEUE_ADD, isTwi = isTwi)
        }
    }

    /**
     * Dedicated Asante Twi spoken narration with authentic Akan phonetics
     */
    fun speakAsanteTwi(title: String, twiText: String) {
        _uiState.update {
            it.copy(
                isTtsSpeaking = true,
                isPlayingMelody = false,
                currentSongPlayingId = null,
                ttsSpeakingTitle = "$title (🇬🇭 Asante Twi)",
                ttsSpeakingVerseNumber = null,
                isAsanteTwiAudio = true
            )
        }
        val utteranceId = "twi_${title.hashCode()}_last_true"
        audioPlayer.speak(twiText, utteranceId, TextToSpeech.QUEUE_FLUSH, isTwi = true)
    }

    fun listenToText(title: String, text: String, isTwi: Boolean = false) {
        _uiState.update {
            it.copy(
                isTtsSpeaking = true,
                isPlayingMelody = false,
                currentSongPlayingId = null,
                ttsSpeakingTitle = title + if (isTwi) " (🇬🇭 Asante Twi)" else "",
                ttsSpeakingVerseNumber = null,
                isAsanteTwiAudio = isTwi
            )
        }
        val utteranceId = "text_${title.hashCode()}_last_true"
        audioPlayer.speak(text, utteranceId, TextToSpeech.QUEUE_FLUSH, isTwi = isTwi)
    }

    fun playSongMelody(song: BibleSongItem) {
        _uiState.update {
            it.copy(
                isPlayingMelody = true,
                currentSongPlayingId = song.id,
                ttsSpeakingTitle = song.title,
                isTtsSpeaking = true,
                ttsSpeakingVerseNumber = null,
                isAsanteTwiAudio = false
            )
        }
        audioPlayer.playSongMelody(song.melody)
    }

    fun setSpeechRate(rate: Float) {
        audioPlayer.setSpeechRate(rate)
        _uiState.update { it.copy(speechRate = rate) }
    }

    /**
     * Halts all audio immediately. Used whenever the user leaves the audio section,
     * switches screens, closes dialogs, or taps stop.
     */
    fun stopSpeaking() {
        audioPlayer.stop()
        _uiState.update {
            it.copy(
                isTtsSpeaking = false,
                isPlayingMelody = false,
                currentSongPlayingId = null,
                ttsSpeakingVerseNumber = null,
                ttsSpeakingTitle = null,
                isAsanteTwiAudio = false,
                isTestingSpeaker = false
            )
        }
    }

    fun quitAudioSection() {
        stopSpeaking()
    }

    fun clearTtsStatusMessage() {
        _uiState.update { it.copy(ttsStatusMessage = null) }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }
}
