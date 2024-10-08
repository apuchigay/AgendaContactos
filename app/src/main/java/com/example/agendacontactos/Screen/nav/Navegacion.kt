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
    val contactList = remember { mutableStateListOf<Contact>() }

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
                    navController.popBackStack()
                },
                onEditContact = { contact, index ->
                    navController.navigate("edit_contact/$index/${contact.nombre}/${contact.apellido}/${contact.alias}/${contact.telefono}/${contact.hobbie}")
                },
                onDeleteContact = { contact ->
                    contactList.remove(contact)
                }
            )
        }
        composable("edit_contact/{index}/{nombre}/{apellido}/{alias}/{telefono}/{hobbie}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toInt() ?: -1
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val apellido = backStackEntry.arguments?.getString("apellido") ?: ""
            val alias = backStackEntry.arguments?.getString("alias") ?: ""
            val telefono = backStackEntry.arguments?.getString("telefono") ?: ""
            val hobbie = backStackEntry.arguments?.getString("hobbie") ?: ""

            EditContact(
                contact = Contact(nombre, apellido, alias, telefono, hobbie),
                onSave = { updatedContact ->
                    if (index != -1) {
                        contactList[index] = updatedContact
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}