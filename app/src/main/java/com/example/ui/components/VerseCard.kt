package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Verse
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BibleSerif
import com.example.ui.theme.GoldPrimary

@Composable
fun VerseCard(
    verse: Verse,
    isBookmarked: Boolean,
    highlightColorHex: String?,
    hasNote: Boolean,
    isTargetHighlighted: Boolean,
    isCurrentlySpeaking: Boolean,
    fontSizeSp: Float,
    useSerif: Boolean,
    onClick: () -> Unit,
    onToggleBookmark: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val highlightColor = when (highlightColorHex?.uppercase()) {
        "#F59E0B" -> Color(0x33F59E0B)
        "#10B981" -> Color(0x3310B981)
        "#0EA5E9" -> Color(0x330EA5E9)
        "#F43F5E" -> Color(0x33F43F5E)
        "#8B5CF6" -> Color(0x338B5CF6)
        else -> if (isTargetHighlighted) Color(0x28F59E0B) else Color.Transparent
    }

    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(highlightColor)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 7.dp)
            .testTag("verse_item_${verse.verseNumber}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Verse content
                val annotatedString = buildAnnotatedString {
                    // Verse number superscript
                    withStyle(
                        SpanStyle(
                            color = AmberGlow,
                            fontSize = (fontSizeSp * 0.72f).sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Default
                        )
                    ) {
                        append("${verse.verseNumber}  ")
                    }

                    // Scripture text
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = fontSizeSp.sp,
                            fontFamily = if (useSerif) BibleSerif else FontFamily.Default,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(verse.text)
                    }
                }

                Text(
                    text = annotatedString,
                    modifier = Modifier.weight(1f),
                    lineHeight = (fontSizeSp * 1.55f).sp
                )

                // Indicators row: Bookmark, Note, TTS Speaking
                if (isBookmarked || hasNote || isCurrentlySpeaking) {
                    Row(
                        modifier = Modifier.padding(start = 6.dp, top = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isCurrentlySpeaking) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Speaking",
                                tint = AmberGlow,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        if (isBookmarked) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "Bookmarked",
                                tint = GoldPrimary,
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { onToggleBookmark?.invoke() }
                            )
                        }
                        if (hasNote) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = "Has Study Note",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
