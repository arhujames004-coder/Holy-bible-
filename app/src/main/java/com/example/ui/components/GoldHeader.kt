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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BibleSerif
import com.example.ui.theme.GoldPrimary

@Composable
fun GoldHeader(
    currentTranslation: String,
    savedCount: Int,
    onTranslationSelected: (String) -> Unit,
    onVerseOfDayClick: () -> Unit,
    onSavedClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onWallpaperClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var translationMenuExpanded by remember { mutableStateOf(false) }
    val translations = listOf("KJV", "NKJV", "TWI", "NIV", "WEB", "BBE", "ASV", "OEB")

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Emblem & Title with Jesus Sanctuary shortcut
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onVerseOfDayClick() }
                    .padding(4.dp)
                    .testTag("app_logo_title")
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(GoldPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✝",
                        color = Color(0xFF1E1700),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        text = "THE HOLY BIBLE",
                        fontFamily = BibleSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        letterSpacing = 1.sp,
                        color = GoldPrimary
                    )
                    Text(
                        text = "DIGITAL SANCTUARY",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.5.sp,
                        color = AmberGlow
                    )
                }
            }

            // Actions: Translation Selector, Search, Saved, Settings
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Translation Dropdown Chip
                Box {
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { translationMenuExpanded = true }
                            .testTag("translation_selector"),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = currentTranslation,
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = translationMenuExpanded,
                        onDismissRequest = { translationMenuExpanded = false }
                    ) {
                        translations.forEach { tr ->
                            DropdownMenuItem(
                                text = {
                                    val desc = when (tr) {
                                        "KJV" -> "King James Version (Offline)"
                                        "NKJV" -> "New King James Version"
                                        "TWI" -> "Asante Twi (Ghana - Twerɛ Kronkron)"
                                        "NIV" -> "New International Version"
                                        "WEB" -> "World English Bible"
                                        "BBE" -> "Bible in Basic English"
                                        "ASV" -> "American Standard Version"
                                        "OEB" -> "Open English Bible"
                                        else -> tr
                                    }
                                    Column {
                                        Text(
                                            text = tr,
                                            fontWeight = if (tr == currentTranslation) FontWeight.Bold else FontWeight.Normal,
                                            color = if (tr == currentTranslation) GoldPrimary else MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = desc,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                },
                                onClick = {
                                    onTranslationSelected(tr)
                                    translationMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                // Search Icon
                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("search_icon_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Scripture",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Saved Verses Badge
                IconButton(
                    onClick = onSavedClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("saved_bookmarks_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (savedCount > 0) {
                                Badge(
                                    containerColor = GoldPrimary,
                                    contentColor = Color(0xFF1E1700)
                                ) {
                                    Text(
                                        text = savedCount.toString(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Saved Verses",
                            tint = if (savedCount > 0) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Wallpaper & Speakers Shortcut
                IconButton(
                    onClick = onWallpaperClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("header_wallpaper_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Wallpaper & Speakers",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Settings (Font size & theme)
                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("settings_button")
                ) {
                    Text(
                        text = "⚙️",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
