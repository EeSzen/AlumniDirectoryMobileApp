package com.eeszen.alumnidirectoryapp.ui.screens.home

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CancelPresentation
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eeszen.alumnidirectoryapp.data.model.Status
import com.eeszen.alumnidirectoryapp.ui.navigation.Screen
import com.eeszen.alumnidirectoryapp.ui.screens.dialog.BottomSheetDialog
import com.eeszen.alumnidirectoryapp.ui.theme.customOutlinedTextFieldColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
){
    val userStatus = viewModel.userStatus.collectAsStateWithLifecycle().value
    val alumni by viewModel.filteredAlumni.collectAsStateWithLifecycle()
    val searchName by viewModel.searchName.collectAsStateWithLifecycle()
    val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()
    // SortBy dropdown
    val options = SortOption.entries.toList()
    var optionsExpanded by remember { mutableStateOf(false) }
    val label = when (sortOption) {
        SortOption.NAME_AZ -> "Name (A–Z)"
        SortOption.NAME_ZA -> "Name (Z–A)"
        SortOption.GRAD_YEAR_NEWEST -> "Graduation year (Newest)"
        SortOption.GRAD_YEAR_OLDEST -> "Graduation year (Oldest)"
        SortOption.RECENTLY_UPDATED -> "Recently updated profiles"
    }

    var showFilter by remember { mutableStateOf(false) }

    if(showFilter) {
        BottomSheetDialog(
            onDismiss = { showFilter = false },
            onApply = {
                viewModel.onFiltersChanged(it)
            }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.getAllAlumni()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box (
            modifier = Modifier.fillMaxSize().background(
                color = MaterialTheme.colorScheme.surface
            ),
            contentAlignment = Alignment.Center
        ){
            when(userStatus) {
                Status.PENDING -> PendingStatusView()
                Status.REJECTED -> RejectedStatusView()
                Status.INACTIVE -> InactiveStatusView()
                Status.APPROVED -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(bottomStart = 40.dp)
                            ).padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            // Search by name
                            OutlinedTextField(
                                modifier = Modifier.weight(0.6f),
                                colors = customOutlinedTextFieldColors(),
                                shape = RoundedCornerShape(8.dp),
                                value = searchName,
                                onValueChange = {viewModel.onSearchName(it)},
                                placeholder = { Text("Search Name") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        ""
                                    )
                                },
                                singleLine = true
                            )
                            // Sort
                            Row(
                                modifier = Modifier.weight(0.35f)
                            ) {
                                ExposedDropdownMenuBox(
                                    expanded = optionsExpanded,
                                    onExpandedChange = { optionsExpanded = !optionsExpanded },
                                ) {
                                    OutlinedTextField(
                                        shape = RoundedCornerShape(8.dp),
                                        value = label,
                                        onValueChange = {},
                                        readOnly = true,
                                        trailingIcon = {
                                            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Expand")
                                        },
                                        modifier = Modifier
                                            .menuAnchor()
                                            .fillMaxWidth(),
                                        singleLine = true,
                                        colors = customOutlinedTextFieldColors()
                                    )
                                    ExposedDropdownMenu(
                                        expanded = optionsExpanded,
                                        onDismissRequest = { optionsExpanded = false },
                                        containerColor = MaterialTheme.colorScheme.surface
                                    ) {
                                        options.forEach { option ->
                                            DropdownMenuItem(
                                                text = { Text(
                                                    when (option) {
                                                        SortOption.NAME_AZ -> "Name (A–Z)"
                                                        SortOption.NAME_ZA -> "Name (Z–A)"
                                                        SortOption.GRAD_YEAR_NEWEST -> "Graduation year (Newest)"
                                                        SortOption.GRAD_YEAR_OLDEST -> "Graduation year (Oldest)"
                                                        SortOption.RECENTLY_UPDATED -> "Recently updated profiles"
                                                    },
                                                    fontSize = 12.sp
                                                ) },
                                                onClick = {
                                                    viewModel.onSortOptionSelected(option)
                                                    optionsExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            IconButton(
                                onClick = { showFilter = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterAlt, ""
                                )
                            }
                        }
                        LazyColumn (
                            contentPadding = PaddingValues(8.dp),
                            modifier = Modifier.fillMaxSize().padding(top = 10.dp)
                        ) {
                            items(alumni) { user ->
                                Card(
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                                        .clickable {
                                            navController.navigate(Screen.Profile(user.id))
                                        },
                                    border = BorderStroke(width = 1.dp, color = Color.Black.copy(0.6f))
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                // Profile avatar
                                                Icon(
                                                    modifier = Modifier
                                                        .size(64.dp)
                                                        .background(color = Color.White, shape = CircleShape),
                                                    imageVector = Icons.Default.AccountCircle,
                                                    contentDescription = "",
                                                    tint = Color.Black,
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .border(
                                                            2.dp,
                                                            color = MaterialTheme.colorScheme.onPrimary,
                                                            shape = CutCornerShape(8.dp)
                                                        )
                                                        .background(
                                                            color = MaterialTheme.colorScheme.primary,
                                                            shape = CutCornerShape(8.dp)
                                                        )
                                                ) {
                                                    Text(
                                                        "Class of ${user.graduationYear}",
                                                        color = MaterialTheme.colorScheme.onPrimary,
                                                        modifier = Modifier.padding(
                                                            start = 6.dp,
                                                            end = 6.dp,
                                                            top = 4.dp,
                                                            bottom = 4.dp),
                                                        textAlign = TextAlign.Center,
                                                        style = MaterialTheme.typography.titleMedium,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(Modifier.height(2.dp))
                                        Text(
                                            user.fullName,
                                            textAlign = TextAlign.Start,
                                            style = MaterialTheme.typography.titleLarge,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            "${user.currentJob} at ${user.currentCompany}",
                                            textAlign = TextAlign.Start,
                                            style = MaterialTheme.typography.titleSmall,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            "Primary Stack: ${user.primaryTechStack}",
                                            textAlign = TextAlign.Start,
                                            style = MaterialTheme.typography.titleSmall,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.LocationOn,
                                                ""
                                            )
                                            Text(
                                                "${user.currentCity}, ${user.currentCountry}",
                                                style = MaterialTheme.typography.titleSmall
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
    }
}
@Composable
fun PendingStatusView() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Icon(
            imageVector = Icons.Default.TagFaces,
            "",
            modifier = Modifier
                .size(100.dp)
        )
        Text(
            "Thanks for signing up!"
        )
        Text(
            "We’re reviewing your account."
        )
    }
}
@Composable
fun RejectedStatusView() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Icon(
            imageVector = Icons.Default.CancelPresentation,
            "",
            modifier = Modifier
                .size(100.dp)
        )
        Text(
            "Sorry! Sign Up rejected"
        )
        Text(
            "Contact admin for further action"
        )
    }
}
@Composable
fun InactiveStatusView() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Icon(
            imageVector = Icons.Default.WarningAmber,
            "",
            modifier = Modifier
                .size(100.dp)
        )
        Text(
            "Your account is inactive!"
        )
        Text(
            "Contact admin for further action"
        )
    }
}