package com.eeszen.alumnidirectoryapp.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eeszen.alumnidirectoryapp.data.model.Status
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val user = viewModel.user.collectAsStateWithLifecycle().value
    val isAdmin = viewModel.isAdmin.collectAsStateWithLifecycle().value

    var fullName by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var techStack by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var contactPref by remember { mutableStateOf("") }
    var shortBio by remember { mutableStateOf("") }

    var gradYear by remember { mutableStateOf("") }
    var country by remember { mutableStateOf(user.currentCountry) }
    var currentStatus by remember { mutableStateOf(user.status) }

    LaunchedEffect(user) {
        fullName = user.fullName
        department = user.department
        jobTitle = user.currentJob
        company = user.currentCompany
        techStack = user.primaryTechStack
        city = user.currentCity
        contactPref = user.contactPreference
        shortBio = user.shortBio

        gradYear = user.graduationYear.toString()
        country = user.currentCountry
        currentStatus = user.status
    }

    LaunchedEffect(Unit) {
        viewModel.success.collect {
            navController.popBackStack()
        }
    }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val startYear = currentYear - 100
    val endYear = currentYear

    // Country dropdown
    val countries = listOf("United States", "Canada", "United Kingdom", "India", "Germany", "Australia", "Singapore", "Japan")
    var countryExpanded by remember { mutableStateOf(false) }
    // Status dropdown
    val statusOptions = listOf(Status.PENDING, Status.APPROVED, Status.REJECTED, Status.INACTIVE)
    var statusExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.size(100.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    // Avatar
                    Icon(
                        modifier = Modifier
                            .size(100.dp)
                            .background(color = Color.White, shape = CircleShape),
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "",
                        tint = Color.Black,
                    )
                    // Camera button
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(x = 30.dp, y = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.White, CircleShape)
                                .border(1.dp, Color.DarkGray, CircleShape)
                                .padding(6.dp)
                        ) {
                            Icon(
                                Icons.Outlined.CameraAlt,
                                "",
                                tint = Color.Cyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

            }
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = department,
                onValueChange = { department = it },
                label = { Text("Department / Major") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Graduation year
            val years = mutableListOf<Int>()
            for (i in startYear..endYear) {
                years.add(i)
            }
            var yearExpanded by remember { mutableStateOf(false) }


            ExposedDropdownMenuBox(
                expanded = yearExpanded,
                onExpandedChange = { yearExpanded = !yearExpanded }
            ) {
                OutlinedTextField(
                    value = gradYear,
                    onValueChange = { gradYear = it },
                    readOnly = true,
                    label = { Text("Batch (year)") },
                    trailingIcon = {
                        Icon(Icons.Filled.ArrowDropDown, null)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = yearExpanded,
                    onDismissRequest = { yearExpanded = false }
                ) {
                    years.forEach {
                        DropdownMenuItem(
                            text = { Text(it.toString()) },
                            onClick = {
                                gradYear = it.toString()
                                yearExpanded = false
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = jobTitle,
                    onValueChange = { jobTitle = it },
                    label = { Text("Current job") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = company,
                    onValueChange = { company = it },
                    label = { Text("Current company") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Country dropdown
                ExposedDropdownMenuBox(
                    expanded = countryExpanded,
                    onExpandedChange = { countryExpanded = !countryExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = country,
                        onValueChange = { country = it },
                        readOnly = true,
                        label = { Text("Country") },
                        trailingIcon = {
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Expand")
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .weight(1f),
                    )
                    ExposedDropdownMenu(
                        expanded = countryExpanded,
                        onDismissRequest = { countryExpanded = false }
                    ) {
                        countries.forEach { c ->
                            DropdownMenuItem(
                                text = { Text(c) },
                                onClick = {
                                    country = c
                                    countryExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Current city") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = techStack,
                    onValueChange = { techStack = it },
                    label = { Text("Primary tech stack") },
                    placeholder = { Text("e.g., Android, Backend Go, Data") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                )

                OutlinedTextField(
                    value = contactPref,
                    onValueChange = { contactPref = it },
                    label = { Text("Contact preference") },
                    placeholder = { Text("e.g, email, phone, LinkedIn") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                )
            }

            OutlinedTextField(
                value = shortBio,
                onValueChange = {
                    if (it.length <= 100) {
                        shortBio = it
                    }
                },
                label = { Text("Short Bio") },
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            // Change status - only admin
            if (isAdmin) {
                ExposedDropdownMenuBox(
                    expanded = statusExpanded,
                    onExpandedChange = { statusExpanded = !statusExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = currentStatus.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Current Status") },
                        trailingIcon = {
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Expand")
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                    )
                    ExposedDropdownMenu(
                        expanded = statusExpanded,
                        onDismissRequest = { statusExpanded = false }
                    ) {
                        statusOptions.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status.toString()) },
                                onClick = {
                                    currentStatus = status
                                    statusExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            Button(
                onClick = {
                    viewModel.updateUser(
                        fullName = fullName,
                        department = department,
                        gradYear = gradYear.toInt(),
                        jobTitle = jobTitle,
                        company = company,
                        techStack = techStack,
                        country = country,
                        city = city,
                        contactPref = contactPref,
                        shortBio = shortBio,
                        status = currentStatus
                    )
                },
                modifier = Modifier.padding(12.dp).fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Update Profile")
            }
        }
        }
    }
}