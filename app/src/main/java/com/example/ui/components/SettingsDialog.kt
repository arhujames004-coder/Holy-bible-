package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.BibleSerif
import com.example.ui.theme.BibleThemeMode
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.SanctuaryBackground
import com.example.ui.theme.SepiaBackground

@Composable
fun SettingsDialog(
    themeMode: BibleThemeMode,
    fontSizeSp: Float,
    useSerifFont: Boolean,
    onThemeModeChange: (BibleThemeMode) -> Unit,
    onFontSizeChange: (Float) -> Unit,
    onUseSerifFontChange: (Boolean) -> Unit,
    onDismiss: () -> Unit,
    onOpenWallpaperAndSpeakers: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .widthIn(max = 520.dp)
                .clip(RoundedCornerShape(20.dp)),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Reader Preferences",
                        fontFamily = BibleSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = GoldPrimary
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Theme selection
                Text(
                    text = "THEME STYLE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Dark Sanctuary
                    ThemeChoiceCard(
                        title = "Sanctuary",
                        previewBg = SanctuaryBackground,
                        previewAccent = GoldPrimary,
                        isSelected = themeMode == BibleThemeMode.SANCTUARY_DARK,
                        onClick = { onThemeModeChange(BibleThemeMode.SANCTUARY_DARK) },
                        modifier = Modifier.weight(1f)
                    )

                    // Sepia Parchment
                    ThemeChoiceCard(
                        title = "Parchment",
                        previewBg = SepiaBackground,
                        previewAccent = Color(0xFF9E6E27),
                        isSelected = themeMode == BibleThemeMode.WARM_SEPIA,
                        onClick = { onThemeModeChange(BibleThemeMode.WARM_SEPIA) },
                        modifier = Modifier.weight(1f)
                    )

                    // Cathedral Light
                    ThemeChoiceCard(
                        title = "Cathedral",
                        previewBg = Color.White,
                        previewAccent = GoldPrimary,
                        isSelected = themeMode == BibleThemeMode.CATHEDRAL_LIGHT,
                        onClick = { onThemeModeChange(BibleThemeMode.CATHEDRAL_LIGHT) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Font size slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SCRIPTURE TEXT SIZE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${fontSizeSp.toInt()} sp",
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary,
                        fontSize = 12.sp
                    )
                }

                Slider(
                    value = fontSizeSp,
                    onValueChange = onFontSizeChange,
                    valueRange = 14f..28f,
                    steps = 6,
                    colors = SliderDefaults.colors(
                        thumbColor = GoldPrimary,
                        activeTrackColor = GoldPrimary
                    ),
                    modifier = Modifier.testTag("font_size_slider")
                )

                // Quick preview
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "The LORD is my shepherd; I shall not want.",
                        fontSize = fontSizeSp.sp,
                        fontFamily = if (useSerifFont) BibleSerif else androidx.compose.ui.text.font.FontFamily.Default,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Serif toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Biblical Serif Font",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Classical liturgical typography",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = useSerifFont,
                        onCheckedChange = onUseSerifFontChange,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = GoldPrimary,
                            checkedTrackColor = GoldPrimary.copy(alpha = 0.4f)
                        ),
                        modifier = Modifier.testTag("serif_font_switch")
                    )
                }

                // Wallpaper & Speakers Shortcut
                OutlinedButton(
                    onClick = {
                        onDismiss()
                        onOpenWallpaperAndSpeakers()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = GoldPrimary
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("settings_open_wallpaper_speakers_btn")
                ) {
                    Text("🖼️ Device Wallpaper & Phone Speakers", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = Color(0xFF1E1700)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("close_settings_button")
                ) {
                    Text("Apply Settings", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ThemeChoiceCard(
    title: String,
    previewBg: Color,
    previewAccent: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .then(
                if (isSelected) Modifier.border(2.dp, GoldPrimary, RoundedCornerShape(12.dp))
                else Modifier.border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            )
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(previewBg)
                    .border(1.5.dp, previewAccent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("✝", color = previewAccent, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
