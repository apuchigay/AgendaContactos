package com.example.agendacontactos.Screen.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.agendacontactos.MainScreen

@Composable
fun NavigationExample() {
    val navController = rememberNavController()
    val contactList = remember { mutableStateListOf<Contact>() }  // Lista mutable para almacenar contactos

    NavHost(navController = navController, startDestination = "add_contact") {
        composable("add_contact") {
            AddContact(
                onSave = { newContact ->
                    contactList.add(newContact)
                },
                onNavigateToList = {
                    navController.navigate("info_screen")
                }
            )
        }
        composable("info_screen") {
            InfoScreen(
                contactList = contactList,
                onBackToForm = {
                    navController.popBackStack() // Regresa al formulario
                }
            )
        }
    }
}