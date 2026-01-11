package com.eeszen.alumnidirectoryapp.ui.screens.admin.rejected

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.eeszen.alumnidirectoryapp.ui.screens.admin.composables.UserCard

@Composable
fun RejectedAlumniScreen(
    navController: NavController,
    viewModel: RejectedAlumniViewModel = hiltViewModel()
) {
    val users = viewModel.users.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.getRejectedUsers()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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