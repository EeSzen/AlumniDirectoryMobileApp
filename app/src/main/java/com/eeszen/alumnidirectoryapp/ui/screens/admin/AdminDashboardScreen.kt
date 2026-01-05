package com.eeszen.alumnidirectoryapp.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Groups3
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eeszen.alumnidirectoryapp.ui.navigation.Screen

@Composable
fun AdminDashboardScreen(
    navController: NavController,
    viewModel: AdminDashboardViewModel = hiltViewModel()
) {
    val pendingUserCount = viewModel.pendingUserCount.collectAsStateWithLifecycle().value
    val approvedUserCount = viewModel.approvedUserCount.collectAsStateWithLifecycle().value
    val rejectedUserCount = viewModel.rejectedUserCount.collectAsStateWithLifecycle().value
    val allUsersCount = viewModel.allUsersCount.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.getPendingUsersCount()
        viewModel.getApprovedUsersCount()
        viewModel.getRejectedUsersCount()
        viewModel.getAllUsersCount()
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
                .background(MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(8.dp))
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp))
        ) {
            Column (
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Key Metrics",
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Approved Alumni
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.weight(1f)
                            .clickable { navController.navigate(Screen.ApprovedAlumni) }
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(color = Color.White, shape = CircleShape),
                                imageVector = Icons.Default.VerifiedUser,
                                contentDescription = "",
                                tint = Color.Black,
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    "Approved Alumni",
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    "$approvedUserCount Users",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    // Pending Registrations
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.weight(1f)
                            .clickable { navController.navigate(Screen.PendingRegistrations) }
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(color = Color.White, shape = CircleShape),
                                imageVector = Icons.Default.PersonAddAlt1,
                                contentDescription = "",
                                tint = Color.Black,
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    "Pending Registrations",
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    "${pendingUserCount} Users",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rejected Alumni
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.weight(1f)
                            .clickable { navController.navigate(Screen.RejectedAlumni) }
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(color = Color.White, shape = CircleShape),
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "",
                                tint = Color.Black,
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    "Rejected Alumni",
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    "$rejectedUserCount Users",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    // Recent Approvals
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.weight(1f)
                            .clickable { }
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(color = Color.White, shape = CircleShape),
                                imageVector = Icons.Default.CheckCircleOutline,
                                contentDescription = "",
                                tint = Color.Black,
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    "Recent Approvals",
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    " Users",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
                .background(MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(8.dp))
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp))
        ) {
            Column (
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Manage Users",
                    style = MaterialTheme.typography.titleLarge
                )
                // Manage Users
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                        .clickable { navController.navigate(Screen.ManageUsers) }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(48.dp)
                                .background(color = Color.White, shape = CircleShape),
                            imageVector = Icons.Default.Groups3,
                            contentDescription = "",
                            tint = Color.Black,
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "All Users",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                "$allUsersCount Users",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
            }
        }
    }
}


