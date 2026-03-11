package com.example.kbbifinder

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kbbifinder.ui.theme.KBBIFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KBBIFinderTheme(darkTheme = true) {
                DictionaryScreen()
            }
        }
    }
}

fun loadWords(context: Context): List<String> {
    return context.assets
        .open("kbbi.txt")
        .bufferedReader()
        .use { it.readLines() }
        .map { it.trim().lowercase() }
        .filter { it.matches(Regex("^[a-zA-Z]+$")) }
        .filter { it.length > 2 }
}

enum class ThemeColor(val color: Color, val displayName: String) {
    GREEN(Color(0xFF4CAF50), "Green"),
    BLUE(Color(0xFF2196F3), "Blue"),
    PURPLE(Color(0xFF9C27B0), "Purple"),
    ORANGE(Color(0xFFFF9800), "Orange"),
    TEAL(Color(0xFF009688), "Teal")
}

enum class SortOrder {
    ASCENDING, DESCENDING
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DictionaryScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val darkBg = Color(0xFF121212)
    val cardBg = Color(0xFF1E1E1E)
    val controlBg = Color(0xFF2A2A2A)
    val textColor = Color.White
    val secondaryTextColor = Color(0xFF9E9E9E)
    val subtleTextColor = Color(0xFF757575)

    var selectedTheme by remember { mutableStateOf(ThemeColor.GREEN) }
    val accentColor by animateColorAsState(
        targetValue = selectedTheme.color,
        label = "accent"
    )

    var words by remember { mutableStateOf<List<String>>(emptyList()) }
    var query by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf("prefix") }
    var noVocal by remember { mutableStateOf(false) }
    var sortOrder by remember { mutableStateOf(SortOrder.ASCENDING) }
    var showSortMenu by remember { mutableStateOf(false) }
    var showThemeMenu by remember { mutableStateOf(false) }

    val recommendations = listOf("ax", "ex", "y", "v", "pr", "st", "in", "co")

    LaunchedEffect(Unit) {
        words = loadWords(context)
    }

    val filtered = remember(query, words, mode, noVocal, sortOrder) {
        if (query.isEmpty()) return@remember emptyList()

        var res = when (mode) {
            "prefix" -> words.filter { f -> f.startsWith(query.lowercase()) }
            "suffix" -> words.filter { f -> f.endsWith(query.lowercase()) }
            else -> emptyList()
        }

        res = if (sortOrder == SortOrder.ASCENDING) res.sorted() else res.sortedDescending()

        if (noVocal) {
            val gold = res.filter { isNoVowel(it) }
            val normal = res.filter { !isNoVowel(it) }
            res = gold + normal
        }

        res
    }

    val groupedByLastLetter = remember(filtered, mode) {
        if (mode == "suffix" && filtered.isNotEmpty()) {
            filtered.groupBy { it.last().toString() }.toSortedMap()
        } else {
            emptyMap()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header with gradient accent
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                accentColor.copy(alpha = 0.15f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(vertical = 12.dp, horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = modifier.padding(end = 8.dp)) {
                        Text(
                            text = "KBBI Finder",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Text(
                            text = "${words.size} kata tersedia",
                            fontSize = 11.sp,
                            color = secondaryTextColor
                        )
                    }

                    // Theme selector dropdown
                    Box {
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { showThemeMenu = true },
                            color = controlBg,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .clip(RoundedCornerShape(50))
                                        .background(accentColor)
                                )
                                Icon(
                                    imageVector = Icons.Default.Palette,
                                    contentDescription = "Theme",
                                    tint = secondaryTextColor,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = showThemeMenu,
                            onDismissRequest = { showThemeMenu = false },
                            modifier = Modifier.background(cardBg)
                        ) {
                            ThemeColor.entries.forEach { theme ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .clip(RoundedCornerShape(50))
                                                    .background(theme.color)
                                            )
                                            Text(
                                                text = theme.displayName,
                                                color = if (selectedTheme == theme) accentColor else textColor,
                                                fontWeight = if (selectedTheme == theme) FontWeight.Bold else FontWeight.Normal
                                            )
                                            if (selectedTheme == theme) {
                                                Icon(
                                                    imageVector = Icons.Default.Clear,
                                                    contentDescription = null,
                                                    tint = accentColor,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        selectedTheme = theme
                                        showThemeMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Search Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Cari kata...", color = subtleTextColor) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = controlBg,
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            cursorColor = accentColor,
                            focusedContainerColor = controlBg,
                            unfocusedContainerColor = controlBg
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { query = "" }) {
                                    Icon(
                                        Icons.Default.Clear,
                                        contentDescription = "Clear",
                                        tint = secondaryTextColor
                                    )
                                }
                            } else {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = subtleTextColor
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Rekomendasi",
                        color = secondaryTextColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        recommendations.forEach { rec ->
                            FilterChip(
                                selected = query == rec,
                                onClick = { query = rec },
                                label = {
                                    Text(
                                        rec,
                                        fontSize = 12.sp,
                                        fontWeight = if (query == rec) FontWeight.SemiBold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = accentColor,
                                    containerColor = controlBg,
                                    selectedLabelColor = Color.Black,
                                    labelColor = secondaryTextColor
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = query == rec,
                                    borderColor = controlBg,
                                    selectedBorderColor = accentColor,
                                    borderWidth = 1.dp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Controls - improved layout
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Prefix/Suffix Toggle - compact
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(controlBg)
                                .padding(3.dp)
                        ) {
                            Button(
                                onClick = { mode = "prefix" },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (mode == "prefix") accentColor else Color.Transparent,
                                    contentColor = if (mode == "prefix") Color.Black else secondaryTextColor
                                ),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 8.dp)
                            ) {
                                Text("Awal", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }

                            Button(
                                onClick = { mode = "suffix" },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (mode == "suffix") accentColor else Color.Transparent,
                                    contentColor = if (mode == "suffix") Color.Black else secondaryTextColor
                                ),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 8.dp)
                            ) {
                                Text("Akhir", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                        }

                        // Sort Button
                        Box {
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { showSortMenu = true },
                                color = controlBg,
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = if (sortOrder == SortOrder.ASCENDING)
                                            Icons.Default.ArrowUpward
                                        else
                                            Icons.Default.ArrowDownward,
                                        contentDescription = "Sort",
                                        tint = accentColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = if (sortOrder == SortOrder.ASCENDING) "A-Z" else "Z-A",
                                        color = textColor,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            DropdownMenu(
                                expanded = showSortMenu,
                                onDismissRequest = { showSortMenu = false },
                                modifier = Modifier.background(cardBg)
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "A-Z (Ascending)",
                                            color = if (sortOrder == SortOrder.ASCENDING) accentColor else textColor
                                        )
                                    },
                                    onClick = {
                                        sortOrder = SortOrder.ASCENDING
                                        showSortMenu = false
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.ArrowUpward,
                                            contentDescription = null,
                                            tint = if (sortOrder == SortOrder.ASCENDING) accentColor else subtleTextColor
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "Z-A (Descending)",
                                            color = if (sortOrder == SortOrder.DESCENDING) accentColor else textColor
                                        )
                                    },
                                    onClick = {
                                        sortOrder = SortOrder.DESCENDING
                                        showSortMenu = false
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.ArrowDownward,
                                            contentDescription = null,
                                            tint = if (sortOrder == SortOrder.DESCENDING) accentColor else subtleTextColor
                                        )
                                    }
                                )
                            }
                        }

                        // No Vocal Toggle - compact
                        Surface(
                            modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                            color = controlBg,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "No V",
                                    color = if (noVocal) accentColor else subtleTextColor,
                                    fontSize = 11.sp,
                                    fontWeight = if (noVocal) FontWeight.SemiBold else FontWeight.Normal
                                )
                                Switch(
                                    checked = noVocal,
                                    onCheckedChange = { noVocal = it },
                                    modifier = Modifier.height(24.dp),
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = accentColor,
                                        checkedTrackColor = accentColor.copy(alpha = 0.4f),
                                        uncheckedThumbColor = subtleTextColor,
                                        uncheckedTrackColor = controlBg
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Results header
            if (query.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${filtered.size} hasil",
                        color = secondaryTextColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    if (noVocal) {
                        Text(
                            text = " Tanpa vokal didahulukan",
                            color = Color(0xFFCCCC88),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Results List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (mode == "suffix" && groupedByLastLetter.isNotEmpty()) {
                    groupedByLastLetter.forEach { (letter, words) ->
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(1.dp)
                                        .background(accentColor.copy(alpha = 0.2f))
                                )
                                Text(
                                    text = letter.uppercase(),
                                    color = accentColor,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(1.dp)
                                        .background(accentColor.copy(alpha = 0.2f))
                                )
                            }
                        }
                        items(words.take(20)) { word ->
                            WordCard(
                                word = word,
                                isGold = noVocal && isNoVowel(word),
                                cardBg = cardBg,
                                textColor = textColor
                            )
                        }
                    }
                } else {
                    items(filtered.take(200)) { word ->
                        WordCard(
                            word = word,
                            isGold = noVocal && isNoVowel(word),
                            cardBg = cardBg,
                            textColor = textColor
                        )
                    }
                }

                if (query.isNotEmpty() && filtered.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Kata tidak ditemukan",
                                color = secondaryTextColor,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WordCard(
    word: String,
    isGold: Boolean,
    cardBg: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isGold) Color(0xFF252520) else cardBg
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = word,
            fontSize = 14.sp,
            color = if (isGold) Color(0xFFDDDD88) else textColor,
            fontWeight = if (isGold) FontWeight.Medium else FontWeight.Normal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp)
        )
    }
}

fun isNoVowel(word: String): Boolean {
    return !word.contains(Regex("[aiueo]"))
}

@Preview(showBackground = true)
@Composable
fun DictionaryScreenPreview() {
    KBBIFinderTheme(darkTheme = true) {
        DictionaryScreen()
    }
}
