package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.data.model.ReadingPlan
import com.example.data.model.ReadingPlanDay
import com.example.data.model.ReadingPlanProgressEntity
import com.example.data.model.ReadingPlansCatalog
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BibleSerif
import com.example.ui.theme.GoldPrimary

@Composable
fun ReadingPlansScreen(
    progressList: List<ReadingPlanProgressEntity>,
    onToggleDayCompleted: (String, Int, Boolean) -> Unit,
    onNavigateToPassage: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val plans = ReadingPlansCatalog.PLANS
    var expandedPlanId by remember { mutableStateOf<String?>(plans.firstOrNull()?.id) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 860.dp)
                .testTag("reading_plans_list"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        item {
            Column {
                Text(
                    text = "DAILY SCRIPTURE PLANS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = AmberGlow
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Structured Journeys",
                    fontFamily = BibleSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = GoldPrimary
                )
                Text(
                    text = "Walk through God's Word day by day with targeted devotionals and automatic progress tracking.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(plans, key = { it.id }) { plan ->
            val planProgress = progressList.filter { it.planId == plan.id && it.isCompleted }
            val completedCount = planProgress.size
            val fraction = if (plan.totalDays > 0) completedCount.toFloat() / plan.totalDays else 0f
            val isExpanded = expandedPlanId == plan.id

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("plan_card_${plan.id}")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Plan header row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedPlanId = if (isExpanded) null else plan.id
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(GoldPrimary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = plan.title,
                                    fontFamily = BibleSerif,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${plan.totalDays} Days • ${plan.category}",
                                    fontSize = 11.sp,
                                    color = AmberGlow
                                )
                            }
                        }

                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Progress Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$completedCount of ${plan.totalDays} Days Completed",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${(fraction * 100).toInt()}%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { fraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = GoldPrimary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    // Expandable list of days
                    AnimatedVisibility(visible = isExpanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            plan.days.forEach { day ->
                                val isDayDone = planProgress.any { it.dayIndex == day.dayNumber }
                                ReadingDayItem(
                                    day = day,
                                    isCompleted = isDayDone,
                                    onToggleCheck = { completed ->
                                        onToggleDayCompleted(plan.id, day.dayNumber, completed)
                                    },
                                    onReadPassage = {
                                        onNavigateToPassage(day.book, day.chapter)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    }
}

@Composable
private fun ReadingDayItem(
    day: ReadingPlanDay,
    isCompleted: Boolean,
    onToggleCheck: (Boolean) -> Unit,
    onReadPassage: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Checkbox(
                    checked = isCompleted,
                    onCheckedChange = onToggleCheck,
                    colors = CheckboxDefaults.colors(
                        checkedColor = GoldPrimary,
                        checkmarkColor = Color(0xFF1E1700)
                    ),
                    modifier = Modifier.testTag("plan_day_check_${day.dayNumber}")
                )

                Column(modifier = Modifier.padding(start = 4.dp)) {
                    Text(
                        text = "Day ${day.dayNumber}: ${day.title}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = if (isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${day.book} ${day.chapter} — ${day.description}",
                        fontSize = 11.sp,
                        color = AmberGlow,
                        maxLines = 1
                    )
                }
            }

            Button(
                onClick = onReadPassage,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldPrimary,
                    contentColor = Color(0xFF1E1700)
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                modifier = Modifier
                    .height(32.dp)
                    .testTag("read_day_${day.dayNumber}")
            ) {
                Text("Read", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
