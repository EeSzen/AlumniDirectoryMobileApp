package com.eeszen.alumnidirectoryapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
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
fun HomeScreen(
    navController: NavController
){
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val fakeUsers = usersPlaceholderData()

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(fakeUsers){
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.padding(8.dp).clickable {}
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Profile avatar
                        Icon(
                            modifier = Modifier
                                .size(100.dp)
                                .background(color = Color.White, shape = CircleShape),
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "",
                            tint = Color.Black,
                        )
                        Text(
                            it.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                             "Software Engineer",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("View")
                        }
                    }
                }
            }
        }
    }
}

fun usersPlaceholderData(): List<FakeUser> {
    return List(
        12
    ) {
        index ->
        FakeUser(
            id = index,
            name = "User $index"
        )
    }
}

data class FakeUser(
    val id: Int,
    val name: String
)