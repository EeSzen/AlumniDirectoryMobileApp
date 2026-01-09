package com.eeszen.alumnidirectoryapp.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eeszen.alumnidirectoryapp.R
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.ui.navigation.Screen
import com.eeszen.alumnidirectoryapp.ui.screens.profile.components.SkillsTagItem

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user = viewModel.user.collectAsStateWithLifecycle().value
    val isAdmin = viewModel.isAdmin.collectAsStateWithLifecycle().value
    val loggedInUser = viewModel.getAuthUser()

    // Reload the profile screen to the latest data
    val backStackEntry = navController.currentBackStackEntry
    LaunchedEffect(backStackEntry) {
        viewModel.refresh()
    }

    LaunchedEffect(Unit) {
        viewModel.signOutSuccess.collect {
            navController.navigate(Screen.Login) {
                popUpTo(0) // clears back stack
            }
        }
    }

    // Background color
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.primaryContainer)
    )
    // Profile section
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    // Back button
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        // Edit button
                        if(user.id == loggedInUser?.uid || isAdmin) {
                            IconButton(
                                onClick = { navController.navigate(Screen.EditProfile(user.id)) }
                            ) {
                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "", tint = Color.Black
                                )
                            }
                        }
                        // Sign Out button
                        if (user.id == loggedInUser?.uid) {
                            IconButton(
                                onClick = { viewModel.signOut() }
                            ) {
                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    imageVector = Icons.Default.ExitToApp,
                                    contentDescription = "", tint = Color.Black
                                )
                            }
                        }
                    }
                    // Profile avatar
                    Icon(
                        modifier = Modifier
                                .size(100.dp)
                                .zIndex(1f)
                                .align(Alignment.TopCenter)
                            .background(color = Color.White, shape = CircleShape),
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "",
                            tint = Color.Black,
                        )
                    // Profile header section
                    Card(
                        modifier = Modifier
                            .padding(top = 66.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
                        elevation = CardDefaults.cardElevation(4.dp),
                        border = BorderStroke(1.dp, color = Color.Black)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 48.dp, bottom = 16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Full Name
                            Text(
                                user.fullName,
                                style = MaterialTheme.typography.headlineLarge
                            )
                            // Graduation Year
                            Box(
                                modifier = Modifier
                                    .border(
                                        1.dp,
                                        color = MaterialTheme.colorScheme.outline,
                                        shape = CutCornerShape(8.dp)
                                    )
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        shape = CutCornerShape(8.dp)
                                    )
                            ) {
                                Text(
                                    "Class of ${user.graduationYear}",
                                    color = MaterialTheme.colorScheme.primary,
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
                            Spacer(Modifier.height(4.dp))
                            ContactLinks()
                            ShortBio(user)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray
                )
                Spacer(Modifier.height(16.dp))
                BasicInfo(user)
                Spacer(Modifier.height(16.dp))
                ProfessionalInfo(user)
                Spacer(Modifier.height(16.dp))
                TechStack(user)
            }
        }
    }
}
@Composable
fun ContactLinks() {
    Box(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            Icon(
                modifier = Modifier
                    .size(28.dp),
                imageVector = Icons.Default.Email,
                contentDescription = "",
                tint = Color.Black,
            )
            Icon(
                modifier = Modifier
                    .size(28.dp),
                imageVector = Icons.Default.Phone,
                contentDescription = "",
                tint = Color.Black,
            )
            Icon(
                painter = painterResource(R.drawable.ic_github),
                modifier = Modifier
                    .size(28.dp),
                contentDescription = "",
                tint = Color.Black,
            )
            Icon(
                painter = painterResource(R.drawable.ic_linkedin),
                modifier = Modifier
                    .size(28.dp),
                contentDescription = "",
                tint = Color.Black,
            )
        }
    }
}
@Composable
fun ShortBio(user: User) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = user.shortBio,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun BasicInfo(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        border = BorderStroke(1.dp, color = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Basic info",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(2.dp))
            ProfileMetaItem(
                icon = Icons.Default.LocationOn,
                text = "${user.currentCity}, ${user.currentCountry}"
            )
            ProfileMetaItem(
                    icon = Icons.Default.MailOutline,
            text = user.email,
            )
            ProfileMetaItem(
                icon = Icons.Default.Phone,
                text = "••• •••",
            )
            ProfileMetaItem(
                icon = Icons.Default.Book,
                text = user.department,
            )
        }
    }
}
@Composable
fun ProfileMetaItem(
    icon: ImageVector,
    text: String
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp),
            imageVector = icon,
            contentDescription = "",
            tint = Color.Black,
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ProfessionalInfo(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        border = BorderStroke(1.dp, color = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Professional info",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(2.dp))
            Text(
                "Current position: ${user.currentJob}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Current company: ${user.currentCompany}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
@Composable
fun TechStack(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        border = BorderStroke(1.dp, color = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Tech Stack",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "Primary tech stack: ${user.primaryTechStack}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Skills: ",
                style = MaterialTheme.typography.titleMedium
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 3
            ) {
                user.skills.forEach { skill ->
                    SkillsTagItem(
                        tag = skill
                    )
                }
            }
        }
    }
}