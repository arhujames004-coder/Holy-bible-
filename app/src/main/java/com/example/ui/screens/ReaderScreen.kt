package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.SampleScriptures
import com.example.data.model.BibleCatalog
import com.example.data.model.BookmarkEntity
import com.example.data.model.HighlightEntity
import com.example.data.model.Verse
import com.example.data.model.VerseNoteEntity
import com.example.ui.components.DailyVerseCard
import com.example.ui.components.VerseCard
import com.example.ui.model.BibleUiState
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BibleSerif
import com.example.ui.theme.GoldPrimary

@Composable
fun ReaderScreen(
    uiState: BibleUiState,
    bookmarks: List<BookmarkEntity>,
    highlights: List<HighlightEntity>,
    notes: List<VerseNoteEntity>,
    onVerseClick: (Verse) -> Unit,
    onChapterSelect: (Int) -> Unit,
    onStepChapter: (Int) -> Unit,
    onSpeakChapter: () -> Unit,
    onStopSpeaking: () -> Unit,
    onDismissVerseOfDay: () -> Unit,
    onToggleBookmark: (Verse) -> Unit,
    onChangeFontSize: (Float) -> Unit,
    onInspirationClick: () -> Unit,
    onSpeakText: (String, String) -> Unit = { _, _ -> },
    onOpenParables: () -> Unit = {},
    onNavigateToPassage: (String, Int, Int?) -> Unit = { _, _, _ -> },
    onSpeakVerse: (Verse) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val currentBookObj = remember(uiState.currentBook) {
        BibleCatalog.findBook(uiState.currentBook)
    }
    val totalChapters = currentBookObj?.chapters ?: 50

    // Auto-scroll to target verse if set
    LaunchedEffect(uiState.targetVerseHighlight, uiState.chapterContent) {
        val target = uiState.targetVerseHighlight
        val verses = uiState.chapterContent?.verses
        if (target != null && verses != null && verses.isNotEmpty()) {
            val index = verses.indexOfFirst { it.verseNumber == target }
            if (index >= 0) {
                // index + 1 accounts for header item
                listState.animateScrollToItem(index + 1)
            }
        }
    }

    // Verse of the day for top card
    val dailyVerse = remember {
        val list = SampleScriptures.VERSE_OF_THE_DAY_LIST
        val dayIndex = (System.currentTimeMillis() / (1000 * 60 * 60 * 24)).toInt() % list.size
        list[dayIndex]
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (uiState.isLoading && uiState.chapterContent == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = GoldPrimary)
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Opening sacred texts...",
                    fontFamily = BibleSerif,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 860.dp)
                        .testTag("reader_verses_list"),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 32.dp)
                ) {
                // Header section item
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        // Jesus Christ Divine Sanctuary Hero Card
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                                .testTag("christ_sanctuary_card")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Jesus Portrait
                                Box(
                                    modifier = Modifier
                                        .size(78.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(1.5.dp, GoldPrimary, RoundedCornerShape(12.dp))
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.img_jesus_christ),
                                        contentDescription = "Lord Jesus Christ",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "WORDS OF JESUS CHRIST • YESU",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp,
                                        color = AmberGlow
                                    )

                                    Spacer(modifier = Modifier.height(2.dp))

                                    Text(
                                        text = "\"I am the way, the truth, and the life: no man cometh unto the Father, but by me.\"",
                                        fontFamily = BibleSerif,
                                        fontSize = 12.sp,
                                        lineHeight = 17.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = "— John 14:6",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = GoldPrimary
                                        )

                                        // Listen to Jesus Quote button
                                        Surface(
                                            color = MaterialTheme.colorScheme.surface,
                                            shape = RoundedCornerShape(6.dp),
                                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f)),
                                            modifier = Modifier.clickable {
                                                onSpeakText(
                                                    "Words of Jesus",
                                                    "Jesus said: I am the way, the truth, and the life: no man cometh unto the Father, but by me."
                                                )
                                            }
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.VolumeUp,
                                                    contentDescription = "Listen to Jesus",
                                                    tint = GoldPrimary,
                                                    modifier = Modifier.size(12.dp)
                                                )
                                                Spacer(modifier = Modifier.width(3.dp))
                                                Text(
                                                    text = "Hear Christ",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = GoldPrimary
                                                )
                                            }
                                        }

                                        // Parables shortcut
                                        Surface(
                                            color = MaterialTheme.colorScheme.surface,
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.clickable { onOpenParables() }
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.MenuBook,
                                                    contentDescription = "Parables",
                                                    tint = AmberGlow,
                                                    modifier = Modifier.size(12.dp)
                                                )
                                                Spacer(modifier = Modifier.width(3.dp))
                                                Text(
                                                    text = "Parables",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = AmberGlow
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Daily Verse component (randomly selected, tap to view full chapter)
                        AnimatedVisibility(visible = uiState.showVerseOfDayBanner) {
                            DailyVerseCard(
                                onNavigateToChapter = onNavigateToPassage,
                                onSpeakVerse = onSpeakVerse,
                                onDismiss = onDismissVerseOfDay,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 14.dp)
                            )
                        }

                        // Category & Testament Kicker + Passage Title & Quick Tools Bar
                        val testamentLabel = if (uiState.currentTestament == "OT") "Old Testament" else "New Testament"
                        val categoryLabel = currentBookObj?.category ?: "Sacred Scripture"

                        Text(
                            text = "$testamentLabel • $categoryLabel • ${uiState.currentBook}".uppercase(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp,
                            color = AmberGlow
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${uiState.currentBook} ${uiState.currentChapter}",
                                fontFamily = BibleSerif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = GoldPrimary,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("chapter_title")
                            )

                            // Quick Reading Tools (Listen, A-, A+, Inspiration)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                // Listen Aloud button
                                Surface(
                                    color = if (uiState.isTtsSpeaking) GoldPrimary else MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(
                                        width = 1.dp,
                                        color = if (uiState.isTtsSpeaking) GoldPrimary else GoldPrimary.copy(alpha = 0.5f)
                                    ),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            if (uiState.isTtsSpeaking) onStopSpeaking()
                                            else onSpeakChapter()
                                        }
                                        .testTag("listen_chapter_button")
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (uiState.isTtsSpeaking) Icons.Default.Stop else Icons.Default.VolumeUp,
                                            contentDescription = "Listen Aloud",
                                            tint = if (uiState.isTtsSpeaking) Color(0xFF1E1700) else AmberGlow,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (uiState.isTtsSpeaking) "Stop Audio" else "Listen",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (uiState.isTtsSpeaking) Color(0xFF1E1700) else AmberGlow
                                        )
                                    }
                                }

                                // A- Button
                                IconButton(
                                    onClick = { onChangeFontSize(maxOf(14f, uiState.fontSizeSp - 2f)) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Text(
                                        "A-",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                // A+ Button
                                IconButton(
                                    onClick = { onChangeFontSize(minOf(28f, uiState.fontSizeSp + 2f)) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Text(
                                        "A+",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                // Inspiration Button
                                IconButton(
                                    onClick = onInspirationClick,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Text("🎲", fontSize = 14.sp)
                                }
                            }
                        }

                        // Offline notice if applicable
                        if (uiState.chapterContent?.isOffline == true && uiState.chapterContent.note != null) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AmberGlow.copy(alpha = 0.12f),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                            ) {
                                Text(
                                    text = uiState.chapterContent.note,
                                    fontSize = 11.sp,
                                    color = AmberGlow,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Chapter Chips row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            for (c in 1..totalChapters) {
                                val isSelected = c == uiState.currentChapter
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (isSelected) GoldPrimary
                                            else MaterialTheme.colorScheme.surfaceVariant
                                        )
                                        .clickable { onChapterSelect(c) }
                                        .testTag("chapter_chip_$c"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$c",
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) Color(0xFF1E1700) else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                // Verses list items
                val verses = uiState.chapterContent?.verses ?: emptyList()
                items(verses, key = { "${it.book}_${it.chapter}_${it.verseNumber}" }) { verse ->
                    val isBookmarked = bookmarks.any {
                        it.book == verse.book && it.chapter == verse.chapter && it.verse == verse.verseNumber
                    }
                    val highlight = highlights.firstOrNull {
                        it.book == verse.book && it.chapter == verse.chapter && it.verse == verse.verseNumber
                    }
                    val hasNote = notes.any {
                        it.book == verse.book && it.chapter == verse.chapter && it.verse == verse.verseNumber
                    }
                    val isTarget = uiState.targetVerseHighlight == verse.verseNumber
                    val isSpeaking = uiState.ttsSpeakingVerseNumber == verse.verseNumber

                    VerseCard(
                        verse = verse,
                        isBookmarked = isBookmarked,
                        highlightColorHex = highlight?.colorHex,
                        hasNote = hasNote,
                        isTargetHighlighted = isTarget,
                        isCurrentlySpeaking = isSpeaking,
                        fontSizeSp = uiState.fontSizeSp,
                        useSerif = uiState.useSerifFont,
                        onClick = { onVerseClick(verse) },
                        onToggleBookmark = { onToggleBookmark(verse) },
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp)
                    )
                }

                // Bottom Navigation Stepper Item
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { onStepChapter(-1) },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("step_prev_chapter")
                        ) {
                            Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Previous Chapter", fontSize = 12.sp)
                        }

                        Text(
                            text = "${uiState.currentBook.uppercase()} ${uiState.currentChapter}",
                            fontFamily = BibleSerif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = AmberGlow,
                            letterSpacing = 1.sp
                        )

                        Button(
                            onClick = { onStepChapter(1) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldPrimary,
                                contentColor = Color(0xFF1E1700)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("step_next_chapter")
                        ) {
                            Text("Next Chapter", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            }
        }
    }
}
