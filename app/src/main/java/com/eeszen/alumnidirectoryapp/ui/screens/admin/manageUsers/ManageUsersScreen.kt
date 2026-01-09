package com.eeszen.alumnidirectoryapp.ui.screens.admin.manageUsers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eeszen.alumnidirectoryapp.ui.navigation.Screen
import com.eeszen.alumnidirectoryapp.ui.screens.admin.composables.UserCard
import com.eeszen.alumnidirectoryapp.ui.screens.dialog.BottomSheetDialog

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
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                // Search
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {viewModel.onSearchChange(it)},
                    modifier = Modifier.weight(0.7f),
                    placeholder = { Text("Search") },
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
                    modifier = Modifier.weight(0.3f)
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterAlt, ""
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(users) { user ->
                    UserCard(
                        modifier = Modifier.fillMaxWidth(),
                        user = user
                    ) {
                        Button(
                            onClick = { navController.navigate(Screen.Profile(user.id)) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("View Full Profile")
                        }
                    }
                }
            }
        }
    }
}