package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.BibleSongCatalog
import com.example.data.model.BibleSongItem
import com.example.data.model.ParableCatalog
import com.example.data.model.ParableItem
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BibleSerif
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary

data class AsanteTwiAudioPassage(
    val id: String,
    val titleTwi: String,
    val titleEnglish: String,
    val reference: String,
    val category: String,
    val scriptureTwi: String,
    val book: String,
    val chapter: Int
)

val ASANTE_TWI_AUDIO_LIST = listOf(
    AsanteTwiAudioPassage(
        id = "twi_john_3_16",
        titleTwi = "Onyankopɔn Dɔ Wiase (Nkwa a Enni Awiei)",
        titleEnglish = "God So Loved The World",
        reference = "Yohane 3:16-17",
        category = "Twerɛ Kronkron",
        scriptureTwi = "Na sɛnea Onyankopɔn dɔɔ wiase ni, sɛ ɔde ne Ba a ɔwo no koro no mae, na obiara a ɔgye no di no anyera, na mmom wanya nkwa a enni awiei. Efisɛ Onyankopɔn ansoma ne Ba no wiase sɛ ɔmmɛbu wiase atɛn, na mmom sɛ ɔnam ne so nnye wiase.",
        book = "John",
        chapter = 3
    ),
    AsanteTwiAudioPassage(
        id = "twi_psalm_23",
        titleTwi = "Awurade Ne Me Hwɛfo (Dwom 23)",
        titleEnglish = "The Lord is My Shepherd",
        reference = "Dwom 23:1-6",
        category = "Nnwom Kronkron",
        scriptureTwi = "Awurade ne me hwɛfo, hwee renhia me. Ɔma meda wura mono so dwoodwoo; ɔde me kɔ nsuo a ɛho dwo nkyɛn. Ɔdwudwo me kra; ɔgyina ne din nti kyerɛ me atreneekwan. Ampa, sɛ menam owuo sum kabii bɔn mu po a, merensuro bɔne biara, efisɛ wo ne me na ɛwɔ hɔ; wo poma ne w'abaa na ɛkyekyere me werɛ. Woto me pono m'atamfo anim; wode ngo sra me ti, me kuruwa yiri boroo so. Akyinnyeɛ biara nni ho sɛ papayɛ ne adɔeɛ bedi m'akyi me nkwa nna nyinaa, na mɛtena Awurade fie daa daa daa.",
        book = "Psalms",
        chapter = 23
    ),
    AsanteTwiAudioPassage(
        id = "twi_lords_prayer",
        titleTwi = "Mpaebɔ Kronkron (Yɛn Agya a Wowɔ Soro)",
        titleEnglish = "The Lord's Prayer in Asante Twi",
        reference = "Matefo 6:9-13",
        category = "Mpaebɔ",
        scriptureTwi = "Yɛn Agya a wowɔ soro, Wo din ho nte. Wo man mmra. W'apɛdeɛ nyɛ hɔ wɔ asase so, sɛnea ɛyɛ wɔ soro. Ma yɛn yɛn daadaa aduane ɛnnɛ. Na firi yɛn yɛn mfomsoɔ, sɛnea yɛn nso yɛde firi wɔn a wɔfom yɛn no. Na mfa yɛn nkɔ sɔhwɛ mu, na mmom yi yɛn firi bɔne mu. Efisɛ ahemman, tumi ne animuonyam yɛ wo deɛ afebɔɔ. Amen.",
        book = "Matthew",
        chapter = 6
    ),
    AsanteTwiAudioPassage(
        id = "twi_genesis_1",
        titleTwi = "Mfitiaseɛ (Ɔbɔadeɛ Hann Ne Asase)",
        titleEnglish = "The Creation in Asante Twi",
        reference = "Mfitiaseɛ 1:1-5",
        category = "Twerɛ Kronkron",
        scriptureTwi = "Mfitiase no Onyankopɔn bɔɔ ɔsoro ne asase. Na asase no yɛ sesaw ne nwunu, na sum duruu bun ani, na Onyankopɔn Honhom wosow nsuo ani. Na Onyankopɔn see sɛ: Hann nnya nhyɛn! Na hann hyɛnee. Na Onyankopɔn hunuu sɛ hann no ye, na Onyankopɔn tetee hann no ne sum no mu. Na Onyankopɔn frɛɛ hann no Awia, na sum no nso ɔfrɛɛ no Anadwo.",
        book = "Genesis",
        chapter = 1
    ),
    AsanteTwiAudioPassage(
        id = "twi_romans_8",
        titleTwi = "Nkonimdi Wɔ Kristo Yesu Mu",
        titleEnglish = "More Than Conquerors in Christ",
        reference = "Romafo 8:31-39",
        category = "Twerɛ Kronkron",
        scriptureTwi = "Sɛ Onyankopɔn gyina yɛn akyi a, hwan na ɔbɛtumi atia yɛn? Deɛ wamma ne Ba mpo ansisi fam, na mmom ɔde no maeɛ maa yɛn nyinaa no, ɔbɛyɛ dɛn na ɔremfa nnoɔma nyinaa mma yɛn kwa? Hwan na ɔbɛtumi atete yɛn afiri Kristo dɔ no mu? Amaneɛ anaa ahohiahia anaa ɔtaa anaa kɔm? Dabi, yeinom nyinaa mu yɛsen nkonimdifoɔ koraa ɛnam deɛ ɔdɔɔ yɛn no so.",
        book = "Romans",
        chapter = 8
    ),
    AsanteTwiAudioPassage(
        id = "twi_good_samaritan",
        titleTwi = "Samariani Pa No (Ɔdɔ A Enni Ntetemu)",
        titleEnglish = "The Good Samaritan Story",
        reference = "Luka 10:30-37",
        category = "Yesu Mmɛ",
        scriptureTwi = "Onipa bi firi Yerusalem resian akɔ Yeriko, na ɔkɔtɔɔ awudifoɔ nsam. Wɔworɔɔ ne ntoma, bɔɔ no pirapiraeɛ, gyaw no sɛ wawu rekɔ. Ɔsɔfoɔ bi bae, ɔhunuu no na ɔfaa nkyɛn kɔe. Levíni nso bae, na ɔhunuu no na ɔfaa nkyɛn kɔe. Nanso Samariani bi a ɔretu kwan baa ne nkyɛn; bere a ɔhunuu no no, ne yam hyehyei maa no. Ɔbɛn no, de ngo ne nsã guu ne pirapiraeɛ so, na ɔkyekyeree. Ɔde no kɔɔ asoɛeɛ bi kɔhwɛɛ no yie.",
        book = "Luke",
        chapter = 10
    ),
    AsanteTwiAudioPassage(
        id = "twi_prodigal_son",
        titleTwi = "Ɔba Ɔsɛeefoɔ No (Agya Dɔ Ne Ahummɔbɔ)",
        titleEnglish = "The Prodigal Son in Asante Twi",
        reference = "Luka 15:11-24",
        category = "Yesu Mmɛ",
        scriptureTwi = "Onipa bi woo mmabarima baanu. Ne ba kumaa see n'agya sɛ: Agya, ma me m'agyapadeɛ fã. Ɔkɔɔ akyirikyiri asase so kɔsɛee ne sika nyinaa. Bere a kɔm baeɛ no, ɔkɔyɛɛ prako hwɛfoɔ. N'adwene baa ne ho so na ɔsee sɛ: Mɛsɔre akɔ m'agya nkyɛn aka sɛ: Agya, mayɛ bɔne atia ɔsoro ne wo anim. Nanso n'agya hunuu no akyirikyiri no, ɔtuu mmirika bɛbam no fefeee n'ano, na wɔyɛɛ apontoɔ efisɛ: Me ba yi na wawu na wanya nkwa bio!",
        book = "Luke",
        chapter = 15
    ),
    AsanteTwiAudioPassage(
        id = "twi_adom_nwanwaso",
        titleTwi = "Adom Nwanwaso (Amazing Grace Twi)",
        titleEnglish = "Amazing Grace Akan Hymn",
        reference = "Asɔre Dwom",
        category = "Asɔre Nnwom",
        scriptureTwi = "Adom nwanwaso dɛ nne bɛn, Ɛgyee ɔbɔnefo sɛ me! Meyerae, nanso mahu me; Menhu ade, nanso seesei mehu. Adom no kyerɛɛ m'akoma suro, Na ɛno ara yii me suro firii hɔ; Hwɛ sɛnea adom no som bo ma me, Bere a edi kan a megigye dii no!",
        book = "Psalms",
        chapter = 23
    ),
    AsanteTwiAudioPassage(
        id = "twi_aseda_nka_nyame",
        titleTwi = "Aseda Nka Nyame (Yɛda W'ase)",
        titleEnglish = "Ghanaian Praise & Thanksgiving",
        reference = "Ghana Asɔre Dwom",
        category = "Asɔre Nnwom",
        scriptureTwi = "Aseda nka Nyame, Yɛhyira wo din daa nyinaa; Efisɛ wo nneyɛe yɛ nwanwa ma yɛn, Yɛda w'ase daa nyinaa. Wo na wobɔɔ soro ne asase, Wo na wokora yɛn so; Yɛde animuonyam ma wo, Kristo Yesu yɛn Gyefo!",
        book = "Psalms",
        chapter = 100
    )
)

@Composable
fun SongsAndParablesScreen(
    onListenToText: (title: String, text: String) -> Unit,
    onPlaySongMelody: (song: BibleSongItem) -> Unit,
    onStopAudio: () -> Unit,
    isAudioPlaying: Boolean,
    currentPlayingTitle: String?,
    onNavigateToScripture: (book: String, chapter: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSectionIndex by remember { mutableIntStateOf(0) } // 0: Parables, 1: Songs, 2: Asante Twi Audio
    var selectedParableForDetail by remember { mutableStateOf<ParableItem?>(null) }
    var selectedSongForLyrics by remember { mutableStateOf<BibleSongItem?>(null) }

    // CRITICAL REQUIREMENT: "when I quit the audio section let it stop"
    // Disposing this screen stops all audio instantly.
    DisposableEffect(Unit) {
        onDispose {
            onStopAudio()
        }
    }

    // Intercept back navigation: stop audio and close modals
    BackHandler {
        if (selectedParableForDetail != null) {
            onStopAudio()
            selectedParableForDetail = null
        } else if (selectedSongForLyrics != null) {
            onStopAudio()
            selectedSongForLyrics = null
        } else {
            onStopAudio()
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 920.dp)
        ) {
            // Top Active Audio Banner with instant Stop & Exit Section
            AnimatedVisibility(visible = isAudioPlaying) {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.5.dp, GoldPrimary),
                tonalElevation = 6.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 6.dp)
                    .testTag("audio_section_playing_banner")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(GoldPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = null,
                                tint = Color(0xFF1E1700),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "NOW PLAYING IN AUDIO SECTION",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = AmberGlow
                            )
                            Text(
                                text = currentPlayingTitle ?: "Sacred Word",
                                fontFamily = BibleSerif,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1
                            )
                        }
                    }

                    Button(
                        onClick = onStopAudio,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE53935),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.testTag("stop_audio_exit_section")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stop,
                            contentDescription = "Stop",
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Stop Audio", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Top Section Tab Bar: Parables, Songs, Asante Twi Audio
        TabRow(
            selectedTabIndex = selectedSectionIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = GoldPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedSectionIndex]),
                    color = GoldPrimary,
                    height = 3.dp
                )
            }
        ) {
            Tab(
                selected = selectedSectionIndex == 0,
                onClick = { selectedSectionIndex = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = "Parables",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Parables",
                            fontWeight = if (selectedSectionIndex == 0) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }
                },
                selectedContentColor = GoldPrimary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.testTag("tab_parables")
            )

            Tab(
                selected = selectedSectionIndex == 1,
                onClick = { selectedSectionIndex = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = "Songs",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Songs",
                            fontWeight = if (selectedSectionIndex == 1) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }
                },
                selectedContentColor = GoldPrimary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.testTag("tab_songs")
            )

            Tab(
                selected = selectedSectionIndex == 2,
                onClick = { selectedSectionIndex = 2 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.RecordVoiceOver,
                            contentDescription = "Asante Twi Audio",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "🇬🇭 Twi Audio",
                            fontWeight = if (selectedSectionIndex == 2) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }
                },
                selectedContentColor = GoldPrimary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.testTag("tab_twi_audio")
            )
        }

        // Content Area based on selected tab
        when (selectedSectionIndex) {
            0 -> {
                // Parables List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("parables_list"),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "THE SACRED PARABLES (MMƐ NE NSƐMBISA)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp,
                            color = AmberGlow
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Teachings of Christ with Divine Wisdom",
                            fontFamily = BibleSerif,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    items(ParableCatalog.ALL_PARABLES, key = { it.id }) { parable ->
                        ParableCard(
                            parable = parable,
                            isPlayingAudio = isAudioPlaying && currentPlayingTitle?.contains(parable.title) == true,
                            onReadStory = { selectedParableForDetail = parable },
                            onListen = {
                                if (isAudioPlaying && currentPlayingTitle?.contains(parable.title) == true) {
                                    onStopAudio()
                                } else {
                                    onListenToText(parable.title, "${parable.title}. ${parable.summary}. Lesson: ${parable.moralLesson}")
                                }
                            }
                        )
                    }
                }
            }
            1 -> {
                // Songs & Hymns List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("songs_list"),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "SACRED SONGS & GHANAIAN HYMNS OF PRAISE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp,
                            color = AmberGlow
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Sing & Listen to Sacred Worship",
                            fontFamily = BibleSerif,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    items(BibleSongCatalog.ALL_SONGS, key = { it.id }) { song ->
                        BibleSongCard(
                            song = song,
                            isPlayingMelody = isAudioPlaying && currentPlayingTitle?.contains(song.title) == true,
                            onPlayMelody = {
                                if (isAudioPlaying && currentPlayingTitle?.contains(song.title) == true) {
                                    onStopAudio()
                                } else {
                                    onPlaySongMelody(song)
                                }
                            },
                            onViewLyrics = { selectedSongForLyrics = song },
                            onReadScripture = {
                                val parts = song.scriptureRef.split(" ", ":", ",")
                                if (parts.isNotEmpty()) {
                                    val book = if (parts[0].startsWith("Psalm")) "Psalms" else parts[0]
                                    val ch = parts.getOrNull(1)?.toIntOrNull() ?: 1
                                    onNavigateToScripture(book, ch)
                                }
                            }
                        )
                    }
                }
            }
            2 -> {
                // Asante Twi Audio Sanctuary
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("asante_twi_audio_list"),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Text(
                            text = "🇬🇭 ASANTE TWI AUDIO SANCTUARY (NNE & TWERƐ KRONKRON)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp,
                            color = AmberGlow
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Authentic Akan Spoken Scriptures & Sacred Hymns",
                            fontFamily = BibleSerif,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Listen aloud in fluent Asante Twi with authentic Ghanaian cadence, tonal phrasing, and reverent acoustics.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    items(ASANTE_TWI_AUDIO_LIST, key = { it.id }) { item ->
                        val isThisPlaying = isAudioPlaying && currentPlayingTitle?.contains(item.titleTwi) == true
                        AsanteTwiAudioCard(
                            item = item,
                            isPlaying = isThisPlaying,
                            onPlayAudio = {
                                if (isThisPlaying) {
                                    onStopAudio()
                                } else {
                                    onListenToText(item.titleTwi, item.scriptureTwi)
                                }
                            },
                            onReadInScripture = {
                                onNavigateToScripture(item.book, item.chapter)
                            }
                        )
                    }
                }
            }
        }
    }
    }

    // Parable Detail Dialog
    selectedParableForDetail?.let { parable ->
        ParableDetailDialog(
            parable = parable,
            onDismiss = {
                onStopAudio()
                selectedParableForDetail = null
            },
            onListenStory = { isTwi ->
                if (isTwi) {
                    onListenToText(parable.titleTwi, parable.storyTwi)
                } else {
                    onListenToText(parable.title, parable.fullStory)
                }
            },
            onStopAudio = onStopAudio,
            isAudioPlaying = isAudioPlaying
        )
    }

    // Song Lyrics Dialog
    selectedSongForLyrics?.let { song ->
        SongLyricsDialog(
            song = song,
            onDismiss = {
                onStopAudio()
                selectedSongForLyrics = null
            },
            onPlayMelody = { onPlaySongMelody(song) },
            onListenLyrics = { isTwi ->
                val lyrics = if (isTwi) song.lyricsTwi.joinToString(". ") else song.lyricsEnglish.joinToString(". ")
                val title = if (isTwi) song.titleTwi else song.title
                onListenToText(title, lyrics)
            },
            onStopAudio = onStopAudio,
            isAudioPlaying = isAudioPlaying
        )
    }
}

@Composable
private fun AsanteTwiAudioCard(
    item: AsanteTwiAudioPassage,
    isPlaying: Boolean,
    onPlayAudio: () -> Unit,
    onReadInScripture: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = if (isPlaying) 1.5.dp else 1.dp,
            color = if (isPlaying) GoldPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        ),
        modifier = modifier
            .fillMaxWidth()
            .testTag("asante_twi_card_${item.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Category badge & Scripture reference
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = GoldPrimary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "🇬🇭 ${item.category}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }

                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.clickable { onReadInScripture() }
                ) {
                    Text(
                        text = item.reference,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AmberGlow,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title in Twi & English subtitle
            Text(
                text = item.titleTwi,
                fontFamily = BibleSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = if (isPlaying) GoldPrimary else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = item.titleEnglish,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Twi Scripture Quote Box
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "\"${item.scriptureTwi}\"",
                    fontFamily = BibleSerif,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons: Play/Stop Asante Twi Audio & Read Full Chapter
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onPlayAudio,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isPlaying) Color(0xFFE53935) else GoldPrimary,
                        contentColor = if (isPlaying) Color.White else Color(0xFF1E1700)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1.3f)
                        .testTag("play_twi_audio_btn_${item.id}")
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.VolumeUp,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isPlaying) "Stop Twi Audio" else "🔊 Play Asante Twi",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                OutlinedButton(
                    onClick = onReadInScripture,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Read Chapter", fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun ParableCard(
    parable: ParableItem,
    isPlayingAudio: Boolean,
    onReadStory: () -> Unit,
    onListen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(
            width = if (isPlayingAudio) 1.5.dp else 1.dp,
            color = if (isPlayingAudio) GoldPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        ),
        modifier = modifier
            .fillMaxWidth()
            .testTag("parable_card_${parable.id}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = parable.imageResId),
                    contentDescription = parable.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xCC000000))
                            )
                        )
                )

                Surface(
                    color = GoldLight,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                ) {
                    Text(
                        text = parable.reference,
                        color = Color(0xFF1E1700),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp)
                ) {
                    Text(
                        text = parable.title,
                        fontFamily = BibleSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Twi: ${parable.titleTwi}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = AmberGlow
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = parable.summary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text("💡", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "SPIRITUAL LESSON",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = AmberGlow
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = parable.moralLesson,
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onReadStory,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = Color(0xFF1E1700)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("read_story_btn_${parable.id}")
                    ) {
                        Text("Read Story", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick = onListen,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isPlayingAudio) Color(0xFFE53935) else GoldPrimary
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (isPlayingAudio) Color(0xFFE53935) else GoldPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("listen_parable_btn_${parable.id}")
                    ) {
                        Icon(
                            imageVector = if (isPlayingAudio) Icons.Default.Stop else Icons.Default.VolumeUp,
                            contentDescription = if (isPlayingAudio) "Stop" else "Listen",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isPlayingAudio) "Stop Audio" else "Listen Aloud", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun BibleSongCard(
    song: BibleSongItem,
    isPlayingMelody: Boolean,
    onPlayMelody: () -> Unit,
    onViewLyrics: () -> Unit,
    onReadScripture: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(
            width = if (isPlayingMelody) 1.5.dp else 1.dp,
            color = if (isPlayingMelody) GoldPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        ),
        modifier = modifier
            .fillMaxWidth()
            .testTag("song_card_${song.id}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Image(
                    painter = painterResource(id = song.imageResId),
                    contentDescription = song.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xDD000000))
                            )
                        )
                )

                Surface(
                    color = AmberGlow,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                ) {
                    Text(
                        text = song.scriptureRef,
                        color = Color(0xFF1E1700),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp)
                ) {
                    Text(
                        text = song.title,
                        fontFamily = BibleSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Twi: ${song.titleTwi}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = GoldLight
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Author: ${song.author}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.clickable { onReadScripture() }
                    ) {
                        Text(
                            text = song.scriptureRef,
                            color = GoldPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = song.description,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "OPENING STANZA:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AmberGlow
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "\"${song.lyricsEnglish.firstOrNull() ?: ""}\"",
                            fontFamily = BibleSerif,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onPlayMelody,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isPlayingMelody) Color(0xFFE53935) else GoldPrimary,
                            contentColor = if (isPlayingMelody) Color.White else Color(0xFF1E1700)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("play_hymn_btn_${song.id}")
                    ) {
                        Icon(
                            imageVector = if (isPlayingMelody) Icons.Default.Stop else Icons.Default.PlayArrow,
                            contentDescription = if (isPlayingMelody) "Stop" else "Play",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isPlayingMelody) "Stop Tune" else "Play Chime", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = onViewLyrics,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("view_lyrics_btn_${song.id}")
                    ) {
                        Text("Lyrics (Twi & Eng)", color = GoldPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun ParableDetailDialog(
    parable: ParableItem,
    onDismiss: () -> Unit,
    onListenStory: (isTwi: Boolean) -> Unit,
    onStopAudio: () -> Unit,
    isAudioPlaying: Boolean
) {
    var isTwiMode by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 560.dp)
                .clip(RoundedCornerShape(20.dp)),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    Image(
                        painter = painterResource(id = parable.imageResId),
                        contentDescription = parable.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(Color(0x88000000), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isTwiMode) parable.titleTwi else parable.title,
                                fontFamily = BibleSerif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = GoldPrimary
                            )
                            Text(
                                text = parable.reference,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AmberGlow
                            )
                        }

                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.clickable { isTwiMode = !isTwiMode }
                        ) {
                            Text(
                                text = if (isTwiMode) "🇬🇭 Twi" else "🇺🇸 English",
                                color = GoldPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    ) {
                        item {
                            Text(
                                text = if (isTwiMode) parable.storyTwi else parable.fullStory,
                                fontFamily = BibleSerif,
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onListenStory(isTwiMode) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isAudioPlaying) Color(0xFFE53935) else GoldPrimary,
                                contentColor = if (isAudioPlaying) Color.White else Color(0xFF1E1700)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1.3f)
                        ) {
                            Icon(
                                imageVector = if (isAudioPlaying) Icons.Default.Stop else Icons.Default.VolumeUp,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isAudioPlaying) "Stop Audio" else if (isTwiMode) "🔊 Tie Asante Twi" else "🔊 Listen Aloud",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }

                        OutlinedButton(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(0.8f)
                        ) {
                            Text("Close")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SongLyricsDialog(
    song: BibleSongItem,
    onDismiss: () -> Unit,
    onPlayMelody: () -> Unit,
    onListenLyrics: (isTwi: Boolean) -> Unit,
    onStopAudio: () -> Unit,
    isAudioPlaying: Boolean
) {
    var isTwiMode by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 560.dp)
                .clip(RoundedCornerShape(20.dp)),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isTwiMode) song.titleTwi else song.title,
                            fontFamily = BibleSerif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = GoldPrimary
                        )
                        Text(
                            text = song.author,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.clickable { isTwiMode = !isTwiMode }
                    ) {
                        Text(
                            text = if (isTwiMode) "🇬🇭 Twi" else "🇺🇸 English",
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                val lyricsList = if (isTwiMode) song.lyricsTwi else song.lyricsEnglish

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(lyricsList) { stanza ->
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stanza,
                                fontFamily = BibleSerif,
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Button(
                        onClick = onPlayMelody,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = Color(0xFF1E1700)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Tune",
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("Tune", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }

                    Button(
                        onClick = { onListenLyrics(isTwiMode) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AmberGlow,
                            contentColor = Color(0xFF1E1700)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1.3f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Speak",
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(if (isTwiMode) "🇬🇭 Tie Nnwom" else "🔊 Recite", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(0.8f)
                    ) {
                        Text("Done", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
