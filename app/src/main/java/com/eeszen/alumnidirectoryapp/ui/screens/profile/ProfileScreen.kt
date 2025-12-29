package com.eeszen.alumnidirectoryapp.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.eeszen.alumnidirectoryapp.R
import com.eeszen.alumnidirectoryapp.ui.screens.profile.components.SkillsTagItem

@Composable
fun ProfileScreen(
    navController: NavController
) {
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
                    // Edit button
                    IconButton(
                        onClick = {},
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Default.Edit,
                            contentDescription = "", tint = Color.Black
                        )
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
                                .padding(top = 50.dp, bottom = 16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                "John Doe",
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Text(
                                "UI/UX Designer",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(Modifier.height(4.dp))
                            ContactLinks()
                            Spacer(Modifier.height(12.dp))
                            ProfileMetaRow()
                        }
                    }
                }
                // About me section
                ShortBio()
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray
                )
                Spacer(Modifier.height(16.dp))
                BasicInfo()
                Spacer(Modifier.height(16.dp))
                ProfessionalInfo()
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray
                )
                Spacer(Modifier.height(16.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachRow = 3
                ) {
                    SkillsTagItem(
                        tag = "UI/UX"
                    )
                    SkillsTagItem(
                        tag = "Android"
                    )
                    SkillsTagItem(
                        tag = "Product"
                    )
                    SkillsTagItem(
                        tag = "Data"
                    )
                    SkillsTagItem(
                        tag = "Backend Go"
                    )
                }
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
fun ProfileMetaRow() {
    Row(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileMetaItem(
            icon = Icons.Default.LocationOn,
            text = "Penang, MY",
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight(),
            thickness = 2.dp,
            color = Color.DarkGray
        )
        ProfileMetaItem(
            icon = Icons.Default.MailOutline,
            text = "Email",
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight(),
            thickness = 2.dp,
            color = Color.DarkGray
        )
        ProfileMetaItem(
            icon = Icons.Default.Phone,
            text = "••• •••",
            modifier = Modifier.weight(1f)
        )
    }
}
@Composable
fun ProfileMetaItem(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
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
fun ShortBio() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "About me (Short bio)",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Hi! My name is ...",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun BasicInfo() {
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
            Text(
                text = "Batch of 2026",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Software Engineering",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ProfessionalInfo() {
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
            Text(
                text = "Current company",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Primary tech stack",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}