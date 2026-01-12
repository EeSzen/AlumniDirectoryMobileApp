package com.eeszen.alumnidirectoryapp.ui.screens.admin.manageUsers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eeszen.alumnidirectoryapp.ui.navigation.Screen
import com.eeszen.alumnidirectoryapp.ui.screens.admin.composables.UserCard
import com.eeszen.alumnidirectoryapp.ui.screens.dialog.BottomSheetDialog
import com.eeszen.alumnidirectoryapp.ui.theme.customOutlinedTextFieldColors

@Composable
fun ManageUsersScreen(
    navController: NavController,
    viewModel: ManageUsersViewModel = hiltViewModel()
) {
    val users by viewModel.filteredUsers.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
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
        viewModel.getAllUsers()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
            ) {
                // Search
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {viewModel.onSearchChange(it)},
                    modifier = Modifier.weight(0.8f),
                    colors = customOutlinedTextFieldColors(),
                    placeholder = { Text("Search") },
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            ""
                        )
                    },
                    singleLine = true
                )
                // Filter
                IconButton(
                    onClick = { showFilter = true },
                    modifier = Modifier.weight(0.2f).align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterAlt, ""
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxSize().background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStart = 40.dp)
                )
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    items(users) { user ->
                        UserCard(
                            modifier = Modifier.fillMaxWidth(),
                            user = user
                        ) {
                            Button(
                                onClick = { navController.navigate(Screen.Profile(user.id)) },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.align(Alignment.End),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black
                                )
                            ) {
                                Text(
                                    "View Profile"
                                )
                                Spacer(Modifier.width(16.dp))
                                Icon(
                                    Icons.Default.ArrowForward,
                                    "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}