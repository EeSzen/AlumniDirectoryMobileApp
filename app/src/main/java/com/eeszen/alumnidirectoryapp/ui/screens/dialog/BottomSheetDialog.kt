package com.eeszen.alumnidirectoryapp.ui.screens.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(
    onDismiss: () -> Unit,
    onApply: (FilterState) -> Unit
) {
    val viewModel: BottomSheetDialogViewModel = hiltViewModel()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    val techStacks = viewModel.techStacks.collectAsStateWithLifecycle().value
    val countries = viewModel.countries.collectAsStateWithLifecycle().value

    var selectedTechStacks by remember { mutableStateOf(setOf<String>()) }
    var selectedCountries by remember { mutableStateOf(setOf<String>()) }
    var selectedYears by remember { mutableStateOf(setOf<Int>()) }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val startYear = currentYear - 20
    val endYear = currentYear
    val gradYears = mutableListOf<Int>()
    for (i in startYear..endYear){
        gradYears.add(i)
    }

    LaunchedEffect(Unit) {
        viewModel.getTechStacks()
        viewModel.getCountries()
    }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = Color(0xFFF6F4F1)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Filters",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "BY TECH STACK",
                    fontWeight = FontWeight.Bold
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    techStacks.forEach { stack ->
                        FilterByItem(
                            stack,
                            selected = selectedTechStacks.contains(stack),
                            onClick = { selectedTechStacks = toggle(selectedTechStacks, stack)
                            }
                        )
                    }
                }
                Text(
                    text = "BY LOCATION",
                    fontWeight = FontWeight.Bold
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    countries.forEach { country ->
                        FilterByItem(
                            country,
                            selected = selectedCountries.contains(country),
                            onClick = { selectedCountries = toggle(selectedCountries, country)
                            }
                        )
                    }
                }
                Text(
                    text = "BY GRADUATION YEAR",
                    fontWeight = FontWeight.Bold
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    gradYears.forEach { year ->
                        FilterByItem(
                            year.toString(),
                            selected = selectedYears.contains(year),
                            onClick = { selectedYears = toggle(selectedYears, year
                            )
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedTechStacks = emptySet()
                            selectedCountries = emptySet()
                            selectedYears = emptySet()
                            onApply(FilterState())
                            onDismiss()
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFF5F5F5)),
                        border = BorderStroke(width = 1.dp, color = Color(0xFFE74C4C))
                    ) {
                        Text(
                            "Clear all",
                            color = Color(0xFFE74C4C)
                        )
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onApply(
                                FilterState(
                                    techStacks = selectedTechStacks,
                                    countries = selectedCountries,
                                    gradYears = selectedYears
                                )
                            )
                            onDismiss()
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFE74C4C))
                    ) {
                        Text(
                            "Apply filters"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterByItem(
    tag: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        onClick = { onClick() },
        label = {
            Text(text = tag)
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color(0xFFF5F5F5),
            selectedContainerColor = Color.Black,
            selectedLabelColor = Color.White,
            selectedLeadingIconColor = Color(0xFFE74C4C)
        ),
        selected = selected,
        leadingIcon = {
            if(selected) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "",
                    modifier = Modifier.size(FilterChipDefaults.IconSize))
            } else {
                null
            }
        }
    )
}

data class FilterState(
    val techStacks: Set<String> = emptySet(),
    val countries: Set<String> = emptySet(),
    val gradYears: Set<Int> = emptySet()
)

fun <T> toggle(set: Set<T>, value: T): Set<T> {
    return if (set.contains(value)) set - value else set + value
}