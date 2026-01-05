package com.eeszen.alumnidirectoryapp.ui.screens.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
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
import com.eeszen.alumnidirectoryapp.ui.navigation.Screen
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterFormScreen(
    navController: NavController,
    viewModel: RegisterFormViewModel = hiltViewModel()
){
    val user = viewModel.userData.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.getCurrentUser()
    }
    // If no current authUser, navigate to login screen
    LaunchedEffect(Unit) {
        if (viewModel.getAuthUser() == null) {
            navController.navigate(Screen.Login) {
                popUpTo(Screen.Login) {
                    inclusive = true
                }
            }
        }
    }
    var fullName by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var techStack by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var contactPref by remember { mutableStateOf("") }
    var shortBio by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        fullName = user.fullName
        department = user.department
        jobTitle = user.currentJob
        company = user.currentCompany
        techStack = user.primaryTechStack
        city = user.currentCity
        contactPref = user.contactPreference
        shortBio = user.shortBio
    }
    LaunchedEffect(Unit) {
        viewModel.success.collect {
            navController.navigate(Screen.Home) {
                popUpTo(Screen.Register) { inclusive = true }
            }
        }
    }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val startYear = currentYear - 100
    val endYear = currentYear

    // Country dropdown
    val countries = listOf("United States", "Canada", "United Kingdom", "India", "Germany", "Australia", "Singapore", "Japan")
    var countryExpanded by remember { mutableStateOf(false) }
    var country by remember { mutableStateOf(user.currentCountry) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text("Name")
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Text("Major")
            OutlinedTextField(
                value = department,
                onValueChange = { department = it },
                label = { Text("Department / Major") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Graduation year
            val years = mutableListOf<Int>()
            for (i in startYear..endYear){
                years.add(i)
            }
            var yearExpanded by remember { mutableStateOf(false) }
            var gradYear by remember { mutableStateOf("") }

            Spacer(Modifier.height(12.dp))

            Text("Batch(year)")
            ExposedDropdownMenuBox(
                expanded = yearExpanded,
                onExpandedChange = { yearExpanded = !yearExpanded }
            ) {
                OutlinedTextField(
                    value = gradYear,
                    onValueChange = {},
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

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier.weight(1f)
                ){
                    Text("Job")
                    OutlinedTextField(
                        value = jobTitle,
                        onValueChange = { jobTitle = it },
                        label = { Text("Current job") },
//                        modifier = Modifier.weight(1f),
                        singleLine = true,
                    )
                }

                Column (
                    modifier = Modifier.weight(1f)
                ){
                    Text("Company")
                    OutlinedTextField(
                        value = company,
                        onValueChange = { company = it },
                        label = { Text("Current company") },
//                        modifier = Modifier.weight(1f),
                        singleLine = true,
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier.weight(1f)
                ){
                    Text("Country")
                    // Country dropdown
                    ExposedDropdownMenuBox(
                        expanded = countryExpanded,
                        onExpandedChange = { countryExpanded = !countryExpanded },
//                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = country,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Country") },
                            trailingIcon = {
                                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Expand")
                            },
                            modifier = Modifier
                                .menuAnchor()
//                                .weight(1f),
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
                }

                Column (
                    modifier = Modifier.weight(1f)
                ){
                    Text("City")
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("Current city") },
                        singleLine = true,
//                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Text("Tech Stack")
            OutlinedTextField(
                value = techStack,
                onValueChange = { techStack = it },
                label = { Text("Primary tech stack") },
                placeholder = { Text("e.g., Android, Backend Go, Data") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(12.dp))

            Text("Contact Preference")
            OutlinedTextField(
                value = contactPref,
                onValueChange = { contactPref = it },
                label = { Text("Contact preference") },
                placeholder = { Text("e.g, email, phone, LinkedIn") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(12.dp))

            Text("Bio")
            OutlinedTextField(
                value = shortBio,
                onValueChange = {
                    if (it.length <= 100) { shortBio = it }
                                },
                label = { Text("Short Bio (Optional)") },
                minLines = 3,
                maxLines = 4,
                modifier = Modifier.fillMaxWidth()
            )

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
                        shortBio = shortBio
                    )
                },
                modifier = Modifier.padding(12.dp).fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text("Submit")
            }
        }
    }
}