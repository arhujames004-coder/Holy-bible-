package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.ui.components.AudioPlayerBar
import com.example.ui.components.ExplainVerseDialog
import com.example.ui.components.GoldHeader
import com.example.ui.components.SettingsDialog
import com.example.ui.components.VerseActionSheet
import com.example.ui.components.WallpaperAndSpeakerDialog
import com.example.ui.model.MainNavTab
import com.example.ui.screens.BooksScreen
import com.example.ui.screens.ReaderScreen
import com.example.ui.screens.ReadingPlansScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SongsAndParablesScreen
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.HolyBibleTheme
import com.example.viewmodel.BibleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleApp(
    viewModel: BibleViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()
    val highlights by viewModel.highlights.collectAsStateWithLifecycle()
    val notes by viewModel.notes.collectAsStateWithLifecycle()
    val readingProgress by viewModel.readingProgress.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // BackHandler: Return to Reader if user is on other tabs
    BackHandler(enabled = uiState.activeTab != MainNavTab.READER) {
        if (uiState.activeTab == MainNavTab.SONGS_PARABLES) {
            viewModel.stopSpeaking()
        }
        viewModel.setTab(MainNavTab.READER)
    }

    HolyBibleTheme(themeMode = uiState.themeMode) {
        val hasWallpaper = uiState.customWallpaperUri != null || uiState.presetWallpaperResId != null

        androidx.compose.foundation.layout.BoxWithConstraints(modifier = modifier.fillMaxSize()) {
            val isExpanded = maxWidth >= 720.dp

            val tabs = listOf(
                Triple(MainNavTab.READER, Icons.Default.MenuBook, "Scripture"),
                Triple(MainNavTab.BOOKS, Icons.Default.AutoStories, "Books"),
                Triple(MainNavTab.SONGS_PARABLES, Icons.Default.MusicNote, "Parables & Audio"),
                Triple(MainNavTab.SAVED, Icons.Default.Bookmark, "Saved"),
                Triple(MainNavTab.PLANS, Icons.Default.CalendarToday, "Plans")
            )

            // Background wallpaper layer (custom device photo or sacred artwork)
            if (uiState.customWallpaperUri != null) {
                AsyncImage(
                    model = uiState.customWallpaperUri,
                    contentDescription = "Custom device wallpaper backdrop",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = uiState.wallpaperDimAlpha))
                )
            } else if (uiState.presetWallpaperResId != null) {
                Image(
                    painter = painterResource(id = uiState.presetWallpaperResId!!),
                    contentDescription = "Sacred wallpaper backdrop",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = uiState.wallpaperDimAlpha))
                )
            }

            androidx.compose.foundation.layout.Row(modifier = Modifier.fillMaxSize()) {
                // Adaptive NavigationRail on Expanded displays (tablets, foldables unfolded, landscape)
                if (isExpanded) {
                    androidx.compose.material3.NavigationRail(
                        modifier = Modifier
                            .testTag("tablet_nav_rail"),
                        containerColor = if (hasWallpaper) MaterialTheme.colorScheme.surface.copy(alpha = 0.92f) else MaterialTheme.colorScheme.surface,
                        header = {
                            androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
                            androidx.compose.foundation.layout.Column(
                                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                                modifier = Modifier.padding(bottom = 12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(GoldPrimary, androidx.compose.foundation.shape.CircleShape),
                                    contentAlignment = androidx.compose.ui.Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MenuBook,
                                        contentDescription = "Holy Bible",
                                        tint = Color(0xFF1E1700),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(6.dp))
                                Text(
                                    text = "HOLY BIBLE",
                                    fontSize = 10.sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    color = GoldPrimary,
                                    letterSpacing = 1.sp
                                )
                            }
                        }
                    ) {
                        tabs.forEach { (tab, icon, label) ->
                            val isSelected = uiState.activeTab == tab
                            androidx.compose.material3.NavigationRailItem(
                                selected = isSelected,
                                onClick = {
                                    if (uiState.activeTab == MainNavTab.SONGS_PARABLES && tab != MainNavTab.SONGS_PARABLES) {
                                        viewModel.stopSpeaking()
                                    }
                                    viewModel.setTab(tab)
                                },
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = label,
                                        modifier = Modifier.size(22.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
                                    )
                                },
                                colors = androidx.compose.material3.NavigationRailItemDefaults.colors(
                                    selectedIconColor = Color(0xFF1E1700),
                                    selectedTextColor = GoldPrimary,
                                    indicatorColor = GoldPrimary,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .testTag("rail_tab_${tab.name.lowercase()}")
                            )
                        }
                    }
                }

                // Main Content Scaffold
                Scaffold(
                    containerColor = if (hasWallpaper) Color.Transparent else MaterialTheme.colorScheme.background,
                    topBar = {
                        GoldHeader(
                            currentTranslation = uiState.currentTranslation,
                            savedCount = bookmarks.size,
                            onTranslationSelected = { viewModel.setTranslation(it) },
                            onVerseOfDayClick = { viewModel.openVerseOfDay() },
                            onSavedClick = { viewModel.setTab(MainNavTab.SAVED) },
                            onSearchClick = { viewModel.setTab(MainNavTab.SEARCH) },
                            onSettingsClick = { viewModel.toggleSettingsDialog(true) },
                            onWallpaperClick = { viewModel.toggleWallpaperAndSpeakerDialog(true) }
                        )
                    },
                    bottomBar = {
                        if (!isExpanded) {
                            NavigationBar(
                                modifier = Modifier
                                    .navigationBarsPadding()
                                    .testTag("bottom_nav_bar"),
                                containerColor = if (hasWallpaper) MaterialTheme.colorScheme.surface.copy(alpha = 0.94f) else MaterialTheme.colorScheme.surface,
                                tonalElevation = 6.dp
                            ) {
                                tabs.forEach { (tab, icon, label) ->
                                    val isSelected = uiState.activeTab == tab
                                    NavigationBarItem(
                                        selected = isSelected,
                                        onClick = {
                                            // If quitting the audio section, stop audio immediately
                                            if (uiState.activeTab == MainNavTab.SONGS_PARABLES && tab != MainNavTab.SONGS_PARABLES) {
                                                viewModel.stopSpeaking()
                                            }
                                            viewModel.setTab(tab)
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = label,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = label,
                                                fontSize = 11.sp
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color(0xFF1E1700),
                                            selectedTextColor = GoldPrimary,
                                            indicatorColor = GoldPrimary,
                                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                        modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        AudioPlayerBar(
                            isPlaying = uiState.isTtsSpeaking,
                            title = uiState.ttsSpeakingTitle,
                            currentVerseNumber = uiState.ttsSpeakingVerseNumber,
                            speechRate = uiState.speechRate,
                            isAsanteTwi = uiState.isAsanteTwiAudio,
                            onStopClick = { viewModel.stopSpeaking() },
                            onSpeedChange = { viewModel.setSpeechRate(it) }
                        )

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            when (uiState.activeTab) {
                                MainNavTab.READER -> ReaderScreen(
                                    uiState = uiState,
                                    bookmarks = bookmarks,
                                    highlights = highlights,
                                    notes = notes,
                                    onVerseClick = { viewModel.openVerseAction(it) },
                                    onChapterSelect = { viewModel.loadChapter(uiState.currentBook, it) },
                                    onStepChapter = { viewModel.stepChapter(it) },
                                    onSpeakChapter = { viewModel.speakCurrentChapter() },
                                    onStopSpeaking = { viewModel.stopSpeaking() },
                                    onDismissVerseOfDay = { viewModel.dismissVerseOfDayBanner() },
                                    onToggleBookmark = { viewModel.toggleBookmark(it) },
                                    onChangeFontSize = { viewModel.setFontSize(it) },
                                    onInspirationClick = { viewModel.openVerseOfDay() },
                                    onSpeakText = { title, text -> viewModel.listenToText(title, text) },
                                    onOpenParables = { viewModel.setTab(MainNavTab.SONGS_PARABLES) },
                                    onNavigateToPassage = { book, ch, verse ->
                                        viewModel.loadChapter(book, ch, targetVerse = verse)
                                    },
                                    onSpeakVerse = { viewModel.speakVerse(it) }
                                )
                                MainNavTab.BOOKS -> BooksScreen(
                                    currentBook = uiState.currentBook,
                                    filterText = uiState.booksFilterText,
                                    onFilterChange = { viewModel.setBooksFilter(it) },
                                    onBookAndChapterSelected = { book, ch ->
                                        viewModel.loadChapter(book, ch)
                                    }
                                )
                                MainNavTab.SONGS_PARABLES -> SongsAndParablesScreen(
                                    onListenToText = { title, text ->
                                        viewModel.listenToText(title, text)
                                    },
                                    onPlaySongMelody = { song -> viewModel.playSongMelody(song) },
                                    onStopAudio = { viewModel.stopSpeaking() },
                                    isAudioPlaying = uiState.isTtsSpeaking,
                                    currentPlayingTitle = uiState.ttsSpeakingTitle,
                                    onNavigateToScripture = { book, ch ->
                                        viewModel.stopSpeaking()
                                        viewModel.loadChapter(book, ch)
                                    }
                                )
                                MainNavTab.SAVED -> SavedScreen(
                                    bookmarks = bookmarks,
                                    highlights = highlights,
                                    notes = notes,
                                    onNavigateToVerse = { book, ch, verse ->
                                        viewModel.loadChapter(book, ch, targetVerse = verse)
                                    },
                                    onDeleteBookmark = { viewModel.deleteBookmark(it) },
                                    onDeleteNote = { viewModel.deleteNote(it) }
                                )
                                MainNavTab.PLANS -> ReadingPlansScreen(
                                    progressList = readingProgress,
                                    onToggleDayCompleted = { planId, day, done ->
                                        viewModel.toggleReadingPlanDay(planId, day, done)
                                    },
                                    onNavigateToPassage = { book, ch ->
                                        viewModel.loadChapter(book, ch)
                                    }
                                )
                                MainNavTab.SEARCH -> SearchScreen(
                                    query = uiState.searchQuery,
                                    results = uiState.searchResults,
                                    isSearching = uiState.isSearching,
                                    onQueryChange = { viewModel.performSearch(it) },
                                    onSearchSubmit = { viewModel.performSearch(it) },
                                    onVerseSelected = { book, ch, verse ->
                                        viewModel.loadChapter(book, ch, targetVerse = verse)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Verse Action Bottom Sheet
            uiState.selectedVerseForAction?.let { verse ->
                val isBookmarked = bookmarks.any {
                    it.book == verse.book && it.chapter == verse.chapter && it.verse == verse.verseNumber
                }
                val currentHighlight = highlights.firstOrNull {
                    it.book == verse.book && it.chapter == verse.chapter && it.verse == verse.verseNumber
                }?.colorHex
                val existingNote = notes.firstOrNull {
                    it.book == verse.book && it.chapter == verse.chapter && it.verse == verse.verseNumber
                }?.note

                VerseActionSheet(
                    verse = verse,
                    isBookmarked = isBookmarked,
                    currentHighlightColorHex = currentHighlight,
                    existingNote = existingNote,
                    sheetState = sheetState,
                    onDismiss = { viewModel.dismissVerseAction() },
                    onToggleBookmark = { viewModel.toggleBookmark(verse) },
                    onSetHighlight = { viewModel.setHighlightForSelected(it) },
                    onRemoveHighlight = { viewModel.removeHighlightForSelected() },
                    onSaveNote = { viewModel.saveNoteForSelected(it) },
                    onExplainVerse = { viewModel.explainVerse(verse) },
                    onListenAloud = { viewModel.speakVerse(verse) }
                )
            }

            // Verse Explanation / Study Dialogue
            uiState.selectedVerseForExplanation?.let { verse ->
                ExplainVerseDialog(
                    verse = verse,
                    onDismiss = { viewModel.dismissExplainDialog() }
                )
            }

            // Settings Dialog
            if (uiState.showSettingsDialog) {
                SettingsDialog(
                    themeMode = uiState.themeMode,
                    fontSizeSp = uiState.fontSizeSp,
                    useSerifFont = uiState.useSerifFont,
                    onThemeModeChange = { viewModel.setThemeMode(it) },
                    onFontSizeChange = { viewModel.setFontSize(it) },
                    onUseSerifFontChange = { viewModel.setUseSerifFont(it) },
                    onOpenWallpaperAndSpeakers = { viewModel.toggleWallpaperAndSpeakerDialog(true) },
                    onDismiss = { viewModel.toggleSettingsDialog(false) }
                )
            }

            // Wallpaper & Phone Speakers Dialog
            if (uiState.showWallpaperAndSpeakerDialog) {
                WallpaperAndSpeakerDialog(
                    customWallpaperUri = uiState.customWallpaperUri,
                    presetWallpaperResId = uiState.presetWallpaperResId,
                    wallpaperDimAlpha = uiState.wallpaperDimAlpha,
                    speakerVolumeLevel = uiState.speakerVolumeLevel,
                    speakerDeviceName = uiState.speakerDeviceName,
                    speakerBoostEnabled = uiState.speakerBoostEnabled,
                    isTestingSpeaker = uiState.isTestingSpeaker,
                    onCustomWallpaperSelected = { viewModel.setCustomWallpaper(it) },
                    onPresetWallpaperSelected = { viewModel.setPresetWallpaper(it) },
                    onWallpaperDimChange = { viewModel.setWallpaperDimAlpha(it) },
                    onClearWallpaper = { viewModel.clearWallpaper() },
                    onSpeakerVolumeChange = { viewModel.setSpeakerVolume(it) },
                    onToggleSpeakerBoost = { viewModel.toggleSpeakerBoost(it) },
                    onTestSpeaker = { viewModel.testPhoneSpeaker() },
                    onDismiss = { viewModel.toggleWallpaperAndSpeakerDialog(false) }
                )
            }
        }
    }
}
