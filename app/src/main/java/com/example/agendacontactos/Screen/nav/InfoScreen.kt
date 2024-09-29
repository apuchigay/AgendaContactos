package com.example.agendacontactos.Screen.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun InfoScreen(
    contactList: List<Contact>,
    onBackToForm: () -> Unit,
    onEditContact: (Contact) -> Unit,
    onDeleteContact: (Contact) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contactList) { contact ->
                ContactCard(contact, onEditContact, onDeleteContact)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver al formulario
        Button(onClick = onBackToForm) {
            Text("Volver al Formulario")
        }
    }
}

@Composable
fun ContactCard(
    contact: Contact,
    onEditContact: (Contact) -> Unit,
    onDeleteContact: (Contact) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${contact.nombre}")
            Text(text = "Apellido: ${contact.apellido}")
            Text(text = "Alias: ${contact.alias}")
            Text(text = "Teléfono: ${contact.telefono}")
            Text(text = "Hobbie: ${contact.hobbie}")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botón para editar el contacto
                Button(onClick = { onEditContact(contact) }) {
                    Text("Editar")
                }

                // Botón para eliminar el contacto
                Button(onClick = { onDeleteContact(contact) }) {
                    Text("Eliminar")
                }
            }
        }
    }
}

@Composable
fun EditContact(
    contact: Contact,
    onSave: (Contact) -> Unit,
    onNavigateBack: () -> Unit
) {
    // Estado mutable para cada campo del contacto
    var nombre by remember { mutableStateOf(contact.nombre) }
    var apellido by remember { mutableStateOf(contact.apellido) }
    var alias by remember { mutableStateOf(contact.alias) }
    var telefono by remember { mutableStateOf(contact.telefono) }
    var hobbie by remember { mutableStateOf(contact.hobbie) }

    // Estado para mostrar mensajes de Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Diseño de la interfaz de usuario
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.85f)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Editar Contacto", style = MaterialTheme.typography.displaySmall)

            // Campos de texto para editar los atributos del contacto
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") }
            )
            TextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") }
            )
            TextField(
                value = alias,
                onValueChange = { alias = it },
                label = { Text("Alias") }
            )
            TextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = hobbie,
                onValueChange = { hobbie = it },
                label = { Text("Hobbie") }
            )

            // Botón para guardar los cambios
            Button(onClick = {
                val updatedContact = contact.copy(
                    nombre = nombre,
                    apellido = apellido,
                    alias = alias,
                    telefono = telefono,
                    hobbie = hobbie
                )
                onSave(updatedContact)

                // Muestra un mensaje de confirmación
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Contacto guardado con éxito.")
                }

                onNavigateBack()  // Navegar de regreso después de guardar
            }) {
                Text("Guardar Cambios")
            }

            // Snackbar para mostrar mensajes temporales
            SnackbarHost(hostState = snackbarHostState)
        }
    }
}
