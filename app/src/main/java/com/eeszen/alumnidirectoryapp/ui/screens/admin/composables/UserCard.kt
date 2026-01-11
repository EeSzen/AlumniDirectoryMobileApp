package com.eeszen.alumnidirectoryapp.ui.screens.admin.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.eeszen.alumnidirectoryapp.data.model.Status
import com.eeszen.alumnidirectoryapp.data.model.User

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    user: User,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(64.dp)
                        .background(color = Color.White, shape = CircleShape)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "",
                    tint = Color.Black,
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = user.fullName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = user.email,
                    )
                }
            }
            Spacer(Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth().background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Batch",
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
                Text(
                    text = user.graduationYear.toString(),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Position",
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
                Text(
                    text = user.currentJob,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Company",
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
                Text(
                    text = user.currentCompany,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)

                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Location",
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
                Text(
                    text = user.currentCountry,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Primary Tech Stack",
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
                Text(
                    text = user.primaryTechStack,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Status",
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
                Text(
                    text = user.status.name,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                    color = when(user.status) {
                        Status.APPROVED -> Color.Green
                        Status.PENDING -> Color.Yellow
                        Status.REJECTED -> Color.Red
                        Status.INACTIVE -> Color.Gray
                    }
                )
            }
            content()
        }
    }
}