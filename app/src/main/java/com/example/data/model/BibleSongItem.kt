package com.example.data.model

import androidx.annotation.DrawableRes
import com.example.R

data class MusicNote(
    val frequencyHz: Float,
    val durationMs: Int
)

data class BibleSongItem(
    val id: String,
    val title: String,
    val titleTwi: String,
    val author: String,
    val scriptureRef: String,
    val lyricsEnglish: List<String>,
    val lyricsTwi: List<String>,
    val description: String,
    @DrawableRes val imageResId: Int,
    val melody: List<MusicNote> = emptyList()
)

object BibleSongCatalog {
    // Standard Musical Frequencies in Hz
    private const val C4 = 261.63f
    private const val D4 = 293.66f
    private const val E4 = 329.63f
    private const val F4 = 349.23f
    private const val G4 = 392.00f
    private const val A4 = 440.00f
    private const val B4 = 493.88f
    private const val C5 = 523.25f
    private const val D5 = 587.33f
    private const val E5 = 659.25f
    private const val REST = 0.0f

    val ALL_SONGS = listOf(
        BibleSongItem(
            id = "amazing_grace",
            title = "Amazing Grace",
            titleTwi = "Adom Nwanwaso",
            author = "John Newton (1779)",
            scriptureRef = "1 Chronicles 17:16-17",
            lyricsEnglish = listOf(
                "Amazing grace! How sweet the sound that saved a wretch like me!",
                "I once was lost, but now am found; was blind, but now I see.",
                "'Twas grace that taught my heart to fear, and grace my fears relieved;",
                "How precious did that grace appear the hour I first believed!",
                "Through many dangers, toils and snares, I have already come;",
                "'Tis grace hath brought me safe thus far, and grace will lead me home.",
                "When we've been there ten thousand years, bright shining as the sun,",
                "We've no less days to sing God's praise than when we'd first begun."
            ),
            lyricsTwi = listOf(
                "Adom nwanwaso kɛse a egyee me ɔbɔnefo kra!",
                "Mayera kane, nanso seesei mahunu me; m'ani afurae, nanso mewɔ hann.",
                "Saa adom yi na ɛma m'akoma suroo Nyame, na ɛyii me suro nyinaa.",
                "Hwɛ sɛnea saa adom yi som bo dɔɔso bere a medi kan gyee no di!",
                "Afiri asiane ne amanehunu pii mu na maba;",
                "Adom na ɛde me abɛduru ha dwoodwoo, na adom bɛkɔ me fie nso."
            ),
            description = "Written by former slave trader John Newton after his radical conversion. One of the most cherished sacred hymns in Christian history.",
            imageResId = R.drawable.img_worship_hymns,
            melody = listOf(
                // A-ma-zing grace, how sweet the sound
                MusicNote(G4, 400), MusicNote(C5, 800), MusicNote(E5, 400), MusicNote(C5, 400),
                MusicNote(E5, 800), MusicNote(D5, 400), MusicNote(C5, 800), MusicNote(A4, 400),
                MusicNote(G4, 800), MusicNote(REST, 200),
                // That saved a wretch like me
                MusicNote(G4, 400), MusicNote(C5, 800), MusicNote(E5, 400), MusicNote(C5, 400),
                MusicNote(E5, 800), MusicNote(D5, 400), MusicNote(G4, 1000)
            )
        ),
        BibleSongItem(
            id = "how_great_thou_art",
            title = "How Great Thou Art",
            titleTwi = "O Nyame Me Nyankopɔn (Wo Yɛ Kɛse)",
            author = "Carl Boberg (1885)",
            scriptureRef = "Psalm 8:1, Psalm 104",
            lyricsEnglish = listOf(
                "O Lord my God, when I in awesome wonder, consider all the worlds Thy hands have made,",
                "I see the stars, I hear the rolling thunder, Thy power throughout the universe displayed:",
                "Then sings my soul, my Savior God, to Thee: How great Thou art, how great Thou art!",
                "Then sings my soul, my Savior God, to Thee: How great Thou art, how great Thou art!",
                "And when I think, that God, His Son not sparing, sent Him to die, I scarce can take it in;",
                "That on the Cross, my burden gladly bearing, He bled and died to take away my sin."
            ),
            lyricsTwi = listOf(
                "Awurade me Nyankopɔn, sɛ me dwene wo nneyɛe nwanwaso ho a,",
                "Mahunu nsoromma, mate aprannaa nnyigyei, wo tumi kɛse a ɛwɔ wiase nyinaa:",
                "Ɛnde me kra to dwom kyerɛ wo, m'Agyenkwa Nyame: Wo yɛ kɛse! Wo yɛ kɛse kɛse!",
                "Ɛnde me kra to dwom kyerɛ wo, m'Agyenkwa Nyame: Wo yɛ kɛse! Wo yɛ kɛse kɛse!"
            ),
            description = "A stirring hymn celebrating the breathtaking majesty of creation and the redemption of Calvary.",
            imageResId = R.drawable.img_worship_hymns,
            melody = listOf(
                MusicNote(C4, 400), MusicNote(F4, 400), MusicNote(A4, 600), MusicNote(A4, 400),
                MusicNote(G4, 400), MusicNote(F4, 600), MusicNote(G4, 400), MusicNote(A4, 800),
                MusicNote(F4, 400), MusicNote(A4, 400), MusicNote(C5, 800), MusicNote(B4, 400),
                MusicNote(A4, 800), MusicNote(G4, 1000)
            )
        ),
        BibleSongItem(
            id = "blessed_assurance",
            title = "Blessed Assurance",
            titleTwi = "Ahotɔ Nwanwaso, Yesu Yɛ Me Deɛ",
            author = "Fanny Crosby (1873)",
            scriptureRef = "Hebrews 10:22",
            lyricsEnglish = listOf(
                "Blessed assurance, Jesus is mine! Oh, what a foretaste of glory divine!",
                "Heir of salvation, purchase of God, born of His Spirit, washed in His blood.",
                "This is my story, this is my song, praising my Savior all the day long;",
                "This is my story, this is my song, praising my Savior all the day long.",
                "Perfect submission, perfect delight, visions of rapture now burst on my sight;",
                "Angels, descending, bring from above echoes of mercy, whispers of love."
            ),
            lyricsTwi = listOf(
                "Ahotɔ pa, Yesu yɛ me deɛ koraa! Hwɛ animuonyam nwanwaso a ɛwɔ soro!",
                "Nkwagyeɛ adedifoɔ, Nyankopɔn tɔfoɔ, Ɔhonhom na awo me, bɔgye mogya mu.",
                "Yei ne m'abakɔsɛm, yei ne me dwom: Mereyi m'Agyenkwa ayɛ daa nyinaa!",
                "Yei ne m'abakɔsɛm, yei ne me dwom: Mereyi m'Agyenkwa ayɛ daa nyinaa!"
            ),
            description = "Written by blind hymnwriter Fanny Crosby, reflecting unbreakable assurance and joy in the Lord Jesus Christ.",
            imageResId = R.drawable.img_worship_hymns,
            melody = listOf(
                MusicNote(E4, 400), MusicNote(F4, 400), MusicNote(G4, 800), MusicNote(G4, 400),
                MusicNote(A4, 400), MusicNote(G4, 800), MusicNote(E4, 400), MusicNote(C4, 800),
                MusicNote(D4, 400), MusicNote(E4, 800), MusicNote(D4, 400), MusicNote(C4, 1000)
            )
        ),
        BibleSongItem(
            id = "aseda_nka_nyame",
            title = "Aseda Nka Nyame (Ghana Praise Hymn)",
            titleTwi = "Aseda Nka Nyame / Yɛhyira Wo Din",
            author = "Ghanaian Traditional Church Hymnal",
            scriptureRef = "Psalm 100:4-5, Psalm 103",
            lyricsEnglish = listOf(
                "All thanksgiving belong to God! He has done great wonders in our midst.",
                "We praise Your Holy Name, Almighty Father, for Your loving kindness endures forever.",
                "Come let us sing with joyful hearts to the Rock of our salvation!",
                "For the Lord is good, and His truth abides to all generations."
            ),
            lyricsTwi = listOf(
                "Aseda nka Nyame a ɔyɛɛ ɔsoro ne asase! N'adɔeɛ tim hɔ daa nyinaa.",
                "Yɛhyira wo din kronkron, O yɛn Agya a wowɔ soro, Wo tumi nni awiei.",
                "Mommra na yɛnto dwom mma Awurade, yɛn nkwagyeɛ Botan kɛse no!",
                "Efisɛ Awurade ye, na ne nokorɛ kɔ so firi awoɔntoatoasoɔ so kɔsi awoɔntoatoasoɔ."
            ),
            description = "A jubilant Ghanaian Christian praise hymn commonly sung across churches in Kumasi, Accra, and all Akan communities in Ghana.",
            imageResId = R.drawable.img_worship_hymns,
            melody = listOf(
                MusicNote(C4, 300), MusicNote(E4, 300), MusicNote(G4, 600), MusicNote(G4, 300),
                MusicNote(A4, 300), MusicNote(G4, 600), MusicNote(E4, 300), MusicNote(C4, 600),
                MusicNote(D4, 300), MusicNote(E4, 300), MusicNote(C4, 800)
            )
        ),
        BibleSongItem(
            id = "what_a_friend",
            title = "What a Friend We Have in Jesus",
            titleTwi = "Adamfo Pa Bɛn Na Yɛwɔ Wɔ Yesu Mu",
            author = "Joseph M. Scriven (1855)",
            scriptureRef = "John 15:13-15, Philippians 4:6",
            lyricsEnglish = listOf(
                "What a friend we have in Jesus, all our sins and griefs to bear!",
                "What a privilege to carry everything to God in prayer!",
                "Oh, what peace we often forfeit, oh, what needless pain we bear,",
                "All because we do not carry everything to God in prayer!",
                "Have we trials and temptations? Is there trouble anywhere?",
                "We should never be discouraged; take it to the Lord in prayer."
            ),
            lyricsTwi = listOf(
                "Adamfo pa bɛn na yɛwɔ wɔ Yesu mu, a ɔsoa yɛn bɔne ne yɛn awerɛhoɔ nyinaa!",
                "Hwɛ hokwan kɛse a yɛwɔ sɛ yɛde biribiara kɔ Nyame anim wɔ mpaebɔ mu!",
                "O, asomdwoeɛ pii na yɛhwere, ɛyaw pii na yɛfa mu kwa,",
                "Efisɛ yɛmfa biribiara nkɔ Nyankopɔ anim mpaebɔ mu!",
                "So yɛwɔ sɔhwɛ ne amanehunu? Haw bi wɔ baabiara?",
                "Ɛnsɛ sɛ yɛn abam bu da; fa kɔ Awurade anim mpaebɔ mu."
            ),
            description = "Written as a poem of comfort to Scriven's mother across the ocean during grief. A beloved reassurance of Christ's tender friendship.",
            imageResId = R.drawable.img_worship_hymns,
            melody = listOf(
                MusicNote(G4, 400), MusicNote(G4, 400), MusicNote(A4, 400), MusicNote(G4, 400),
                MusicNote(E4, 600), MusicNote(C4, 400), MusicNote(C4, 600), MusicNote(D4, 400),
                MusicNote(E4, 400), MusicNote(E4, 400), MusicNote(D4, 800)
            )
        )
    )
}
