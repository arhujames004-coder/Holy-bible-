package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.example.data.model.MusicNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.PI
import kotlin.math.sin

class BibleAudioPlayer(
    private val context: Context,
    private val onVerseStart: (verseNum: Int?) -> Unit,
    private val onPlaybackDone: () -> Unit,
    private val onError: (String) -> Unit
) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private var pendingSpeechText: String? = null
    private var pendingUtteranceId: String? = null
    private var pendingIsTwi: Boolean = false
    private var speechRate: Float = 1.0f
    private var isTwiLocaleAvailable = false

    private val audioScope = CoroutineScope(Dispatchers.Default)
    private var songJob: Job? = null
    private var speakerTestJob: Job? = null
    private var isPlayingSong = false

    init {
        initTts()
    }

    private fun initTts() {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            onError("TTS initialization error: ${e.message}")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsReady = true
            tts?.let { engine ->
                // Check if Akan / Twi voice locale is available
                val akanLocales = listOf(
                    Locale("ak", "GH"),
                    Locale("ak"),
                    Locale("tw", "GH"),
                    Locale("tw")
                )
                for (loc in akanLocales) {
                    val availability = engine.isLanguageAvailable(loc)
                    if (availability >= TextToSpeech.LANG_AVAILABLE) {
                        isTwiLocaleAvailable = true
                        break
                    }
                }

                val langResult = engine.setLanguage(Locale.US)
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    engine.setLanguage(Locale.getDefault())
                }
                engine.setPitch(1.0f)
                engine.setSpeechRate(speechRate)

                engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        val verse = parseVerseNumber(utteranceId)
                        onVerseStart(verse)
                    }

                    override fun onDone(utteranceId: String?) {
                        if (isLastUtterance(utteranceId)) {
                            onPlaybackDone()
                        }
                    }

                    override fun onError(utteranceId: String?) {
                        onPlaybackDone()
                    }
                })
            }

            // Flush pending speech if user pressed Listen while initializing
            pendingSpeechText?.let { text ->
                val id = pendingUtteranceId ?: "pending_speech_last_true"
                val isTwi = pendingIsTwi
                pendingSpeechText = null
                pendingUtteranceId = null
                speak(text, id, TextToSpeech.QUEUE_FLUSH, isTwi = isTwi)
            }
        } else {
            isTtsReady = false
            onError("Voice engine initializing. Chimes active.")
        }
    }

    /**
     * Speaks text with support for Asante Twi or English.
     * When isTwi is true or detected, applies Ghanaian Akan phonetic adaptation and voice tuning
     * so standard TTS engines pronounce authentic Asante Twi words reverently and accurately.
     */
    fun speak(
        text: String,
        utteranceId: String,
        queueMode: Int = TextToSpeech.QUEUE_FLUSH,
        isTwi: Boolean = false
    ) {
        stopSong()
        val detectedTwi = isTwi || detectTwiText(text)

        if (isTtsReady && tts != null) {
            val engine = tts!!
            val textToSpeak: String
            if (detectedTwi) {
                // If native Akan voice is present, use it; otherwise adapt phonetically for English TTS
                if (isTwiLocaleAvailable) {
                    engine.setLanguage(Locale("ak", "GH"))
                    engine.setPitch(1.05f)
                    engine.setSpeechRate(speechRate * 0.95f)
                    textToSpeak = text
                } else {
                    engine.setLanguage(Locale.US)
                    engine.setPitch(1.08f)
                    engine.setSpeechRate(speechRate * 0.92f)
                    textToSpeak = convertTwiToPhonetics(text)
                }
            } else {
                engine.setLanguage(Locale.US)
                engine.setPitch(1.0f)
                engine.setSpeechRate(speechRate)
                textToSpeak = text
            }

            val params = Bundle()
            engine.speak(textToSpeak, queueMode, params, utteranceId)
        } else {
            pendingSpeechText = text
            pendingUtteranceId = utteranceId
            pendingIsTwi = detectedTwi
            initTts()
            // Play gentle chime tone immediately so user hears sound right away
            playChimeTone(440f, 350)
        }
    }

    /**
     * Detects if the given text is in Asante Twi based on distinctive Akan letters and vocabulary.
     */
    private fun detectTwiText(text: String): Boolean {
        val lower = text.lowercase()
        return lower.contains('ɛ') || lower.contains('ɔ') ||
                lower.contains("nyame") || lower.contains("awurade") ||
                lower.contains("kristo") || lower.contains("yesu") ||
                lower.contains("adom") || lower.contains("nhyira") ||
                lower.contains("nokorɛ") || lower.contains("ahemman") ||
                lower.contains("twerɛ") || lower.contains("soro") ||
                lower.contains("asase") || lower.contains("yerusalem") ||
                lower.contains("yohane") || lower.contains("dwom")
    }

    /**
     * Adapts Asante Twi text into phonetic phonetic approximations so that standard
     * Android TTS engines without Ghanaian voice packs speak Akan words with accurate
     * vowel qualities, diphthongs, and Ghanaian cadence.
     */
    private fun convertTwiToPhonetics(twi: String): String {
        var processed = twi

        // Key sacred names and frequent biblical words
        val wordReplacements = mapOf(
            "Onyankopɔn" to "Oh-nyahn-koh-pawn",
            "onyankopɔn" to "oh-nyahn-koh-pawn",
            "Awurade" to "Ah-woo-rah-deh",
            "awurade" to "ah-woo-rah-deh",
            "Yesu" to "Yeh-soo",
            "yesu" to "yeh-soo",
            "Kristo" to "Krees-toh",
            "kristo" to "krees-toh",
            "Twerɛ" to "Chwereh",
            "twerɛ" to "chwereh",
            "Kronkron" to "Krawn-krawn",
            "kronkron" to "krawn-krawn",
            "Yohane" to "Yoh-hah-neh",
            "yohane" to "yoh-hah-neh",
            "Dwom" to "Jwohm",
            "dwom" to "jwohm",
            "Mfitiaseɛ" to "Mfee-tee-ah-seh",
            "mfitiaseɛ" to "mfee-tee-ah-seh",
            "Matefo" to "Mah-teh-foh",
            "matefo" to "mah-teh-foh",
            "Adom" to "Ah-dom",
            "adom" to "ah-dom",
            "Nwanwaso" to "Nwaan-wah-soh",
            "nwanwaso" to "nwaan-wah-soh",
            "Aseda" to "Ah-seh-dah",
            "aseda" to "ah-seh-dah",
            "Yɛda W'ase" to "Yeh-dah Wah-seh",
            "yɛda w'ase" to "yeh-dah wah-seh",
            "Yɛhyira" to "Yeh-shee-rah",
            "yɛhyira" to "yeh-shee-rah",
            "Agya" to "Ah-jah",
            "agya" to "ah-jah",
            "ahemman" to "ah-hem-mahn",
            "Samariani" to "Sah-mah-ree-ah-nee",
            "Yerusalem" to "Yeh-roo-sah-lem",
            "Yeriko" to "Yeh-ree-koh",
            "Oguammaa" to "Oh-gwaam-maah",
            "oguammaa" to "oh-gwaam-maah",
            "ɔsɛeefoɔ" to "aw-seh-eh-faw",
            "ɔgufoɔ" to "aw-goo-faw",
            "sinapi" to "see-nah-pee",
            "botantim" to "boh-tahn-teem",
            "badwemma" to "bah-jwem-mah",
            "kwasea" to "kwah-seh-ah",
            "w'akoma" to "wah-koh-mah",
            "nkwa" to "n-kwah",
            "anwummere" to "ahn-woom-meh-reh",
            "anɔpa" to "ah-naw-pah",
            "dwoodwoo" to "jwoo-jwoo",
            "nsuo" to "n-soo-oh",
            "kanea" to "kah-neh-ah",
            "nnwan" to "n-gwan"
        )

        for ((target, replacement) in wordReplacements) {
            processed = processed.replace(target, replacement)
        }

        // Akan phonological rules:
        // 'ɛ' -> 'eh', 'ɔ' -> 'aw'
        // 'ky' -> 'ch', 'gy' -> 'j', 'tw' -> 'chw', 'dw' -> 'jw', 'hy' -> 'sh', 'ny' -> 'ny'
        processed = processed
            .replace("ky", "ch")
            .replace("Ky", "Ch")
            .replace("gy", "j")
            .replace("Gy", "J")
            .replace("tw", "chw")
            .replace("Tw", "Chw")
            .replace("dw", "jw")
            .replace("Dw", "Jw")
            .replace("hy", "sh")
            .replace("Hy", "Sh")
            .replace("ɛ", "eh")
            .replace("Ɛ", "Eh")
            .replace("ɔ", "aw")
            .replace("Ɔ", "Aw")

        return processed
    }

    fun setSpeechRate(rate: Float) {
        speechRate = rate
        tts?.setSpeechRate(rate)
    }

    /**
     * Immediately stops any speech, song melody, or chimes, clearing active audio.
     */
    fun stop() {
        try {
            tts?.stop()
        } catch (ignored: Exception) {}
        stopSong()
        speakerTestJob?.cancel()
        speakerTestJob = null
        pendingSpeechText = null
        pendingUtteranceId = null
        onPlaybackDone()
    }

    fun playSongMelody(melody: List<MusicNote>, onNotePlayed: (Int) -> Unit = {}) {
        stop()
        isPlayingSong = true
        songJob = audioScope.launch {
            try {
                val sampleRate = 22050
                val bufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )

                val audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(bufferSize)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                audioTrack.play()

                melody.forEachIndexed { index, note ->
                    if (!isActive || !isPlayingSong) return@forEachIndexed
                    onNotePlayed(index)

                    val numSamples = (sampleRate * (note.durationMs / 1000.0)).toInt()
                    if (numSamples > 0) {
                        val samples = ShortArray(numSamples)
                        val freq = note.frequencyHz

                        if (freq > 0.0f) {
                            // Synthesize rich warm bell/organ tone with gentle decay
                            for (i in 0 until numSamples) {
                                val t = i.toDouble() / sampleRate
                                val envelope = Math.exp(-2.5 * t)
                                val wave = 0.8 * sin(2.0 * PI * freq * t) + 0.2 * sin(4.0 * PI * freq * t)
                                samples[i] = (wave * envelope * Short.MAX_VALUE * 0.75).toInt().toShort()
                            }
                        } else {
                            // Rest note
                            for (i in 0 until numSamples) samples[i] = 0
                        }

                        audioTrack.write(samples, 0, numSamples)
                    }
                }

                audioTrack.stop()
                audioTrack.release()
            } catch (e: Exception) {
                // Ignore audio track write exceptions on shutdown
            } finally {
                isPlayingSong = false
                onPlaybackDone()
            }
        }
    }

    /**
     * Plays a rich celestial 3-chord harmonic chime directly to the device speaker
     * to test phone speakers and sound clarity for the user.
     */
    fun playSpeakerTestChime(onDone: () -> Unit = {}) {
        stop()
        speakerTestJob = audioScope.launch {
            try {
                // 3 ascending reverent bell tones: A4 (440Hz), C#5 (554Hz), E5 (659Hz)
                val chords = listOf(440.0f, 554.37f, 659.25f, 880.0f)
                val sampleRate = 22050

                for (freq in chords) {
                    if (!isActive) break
                    val durationMs = 380
                    val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
                    val samples = ShortArray(numSamples)

                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / sampleRate
                        val envelope = Math.exp(-3.2 * t)
                        val wave = 0.85 * sin(2.0 * PI * freq * t) + 0.15 * sin(4.0 * PI * freq * t)
                        samples[i] = (wave * envelope * Short.MAX_VALUE * 0.8).toInt().toShort()
                    }

                    val audioTrack = AudioTrack.Builder()
                        .setAudioAttributes(
                            AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .build()
                        )
                        .setAudioFormat(
                            AudioFormat.Builder()
                                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                                .setSampleRate(sampleRate)
                                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                                .build()
                        )
                        .setBufferSizeInBytes(samples.size * 2)
                        .setTransferMode(AudioTrack.MODE_STATIC)
                        .build()

                    audioTrack.write(samples, 0, numSamples)
                    audioTrack.play()
                    delay(300)
                    audioTrack.stop()
                    audioTrack.release()
                }
            } catch (ignored: Exception) {
            } finally {
                onDone()
            }
        }
    }

    private fun playChimeTone(freq: Float, durationMs: Int) {
        audioScope.launch {
            try {
                val sampleRate = 22050
                val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
                val samples = ShortArray(numSamples)
                for (i in 0 until numSamples) {
                    val t = i.toDouble() / sampleRate
                    val envelope = Math.exp(-3.0 * t)
                    val wave = sin(2.0 * PI * freq * t)
                    samples[i] = (wave * envelope * Short.MAX_VALUE * 0.5).toInt().toShort()
                }

                val audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(samples.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                audioTrack.write(samples, 0, numSamples)
                audioTrack.play()
            } catch (ignored: Exception) {}
        }
    }

    private fun stopSong() {
        isPlayingSong = false
        songJob?.cancel()
        songJob = null
    }

    private fun parseVerseNumber(utteranceId: String?): Int? {
        if (utteranceId == null) return null
        val match = Regex("""_v_(\d+)""").find(utteranceId)
        return match?.groupValues?.get(1)?.toIntOrNull()
    }

    private fun isLastUtterance(utteranceId: String?): Boolean {
        if (utteranceId == null) return true
        return utteranceId.contains("_last_true")
    }

    fun release() {
        stop()
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (ignored: Exception) {}
        tts = null
    }
}
