package com.eeszen.alumnidirectoryapp.ui.components.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eeszen.alumnidirectoryapp.data.model.Role
import com.eeszen.alumnidirectoryapp.data.model.User
import com.eeszen.alumnidirectoryapp.data.repo.AlumniRepo
import com.eeszen.alumnidirectoryapp.service.AuthService
import com.eeszen.alumnidirectoryapp.ui.navigation.Screen


data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: String
)

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    authService: AuthService = AuthService(),
    repo: AlumniRepo = AlumniRepo.getInstance()
) {
    val uid = authService.getCurrentUid() ?: return

    val user by produceState<User?>(initialValue = null, key1 = uid) {
        value = repo.getAlumniById(uid)
    }

    val bottomNavItems = buildList {
        add(BottomNavItem(Screen.Home, Icons.Default.Home, "Home"))

        if (user?.role == Role.ADMIN) {
            add(BottomNavItem(Screen.AdminDashboard, Icons.Default.Dashboard, "Admin"))
        }

        add(BottomNavItem(Screen.Profile(uid), Icons.Default.Person, "Profile"))
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
    ) {
        bottomNavItems.forEach { item ->
            val selected =
                currentDestination
                    ?.route
                    ?.contains(item.screen::class.qualifiedName ?: "") == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.screen) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = Color.Black
                    )
                },
                label = {
                    Text(
                        item.label,
                        color = Color.Black
                    )
                }
            )
        }
    }
}

