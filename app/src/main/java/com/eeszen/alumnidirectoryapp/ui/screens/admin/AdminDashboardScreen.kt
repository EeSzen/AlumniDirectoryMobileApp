package com.eeszen.alumnidirectoryapp.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AdminDashboardScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
                // Approved Alumni
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth().clickable {}
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(100.dp)
                                .background(color = Color.White, shape = CircleShape),
                            imageVector = Icons.Default.Group,
                            contentDescription = "",
                            tint = Color.Black,
                        )
                        Text(
                            "Approved Alumni",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                // Pending Registrations
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth().clickable {}
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(100.dp)
                                .background(color = Color.White, shape = CircleShape),
                            imageVector = Icons.Default.PersonAddAlt1,
                            contentDescription = "",
                            tint = Color.Black,
                        )
                        Text(
                            "Pending Registrations",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                // Recent Approvals
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth().clickable {}
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(100.dp)
                                .background(color = Color.White, shape = CircleShape),
                            imageVector = Icons.Default.CheckCircleOutline,
                            contentDescription = "",
                            tint = Color.Black,
                        )
                        Text(
                            "Recent Approvals",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }

