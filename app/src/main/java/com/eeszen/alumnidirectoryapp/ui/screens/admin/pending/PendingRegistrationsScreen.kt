package com.eeszen.alumnidirectoryapp.ui.screens.admin.pending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eeszen.alumnidirectoryapp.ui.screens.admin.composables.UserCard

@Composable
fun PendingRegistrationsScreen(
    navController: NavController,
    viewModel: PendingRegistrationsViewModel = hiltViewModel()
) {
    val pendingUsers = viewModel.pendingUsers.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.getPendingUsers()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(pendingUsers) { user ->
                UserCard(
                    modifier = Modifier.fillMaxWidth(),
                    user = user
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.approveUser(user.id) },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text("Approve")
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.rejectUser(user.id) },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text("Reject")
                        }
                    }
                }
            }
        }
    }
}