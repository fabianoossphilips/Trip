package com.example.trip.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trip.TripConst
import com.example.trip.entity.Category
import com.example.trip.viewModel.CategoryViewModel


@Composable
fun CategoryScreen(model: CategoryViewModel, appState: TripAppState) {
    model.load()
    CategoryList(model, appState)
}


@Composable
fun CategoryList(model: CategoryViewModel, appState: TripAppState) {
    var openDialogRemove by remember { mutableStateOf(false) }
    val list = model.list.value

    ConfirmDelete(openDialog = openDialogRemove,
        onRemove = {
            openDialogRemove = false
            model.remove()
        },
        onClose = { openDialogRemove = false })

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items(items = list) {
                item -> CategoryItem(
                    item,
                    onEdit = {
                        Log.i(TripConst.TAG, "Edit: ${item.id}")
                        appState.navigate("categoryForm/${item.id}")
                    },
                    onRemove = {
                        Log.i(TripConst.TAG, "Remove: ${item.id}")
                        model.categoryForDelete = item
                        openDialogRemove = true
                    }
            )
        }
    }
}

@Composable
fun ConfirmDelete(openDialog: Boolean, onClose: () -> Unit, onRemove: () -> Unit) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                onClose()
            },
            title = {
                Text(text = "Confirm delete record?")
            },
            text = {
                Column() {
                    Text("Do you want delete this record?")
                }
            },
            confirmButton = {
                            Button(onClick = { onRemove() }) {
                                Text(text = "Yes")
                            }
            },
            dismissButton = {
                            Button(onClick = { onClose() }) {
                                Text(text = "No")
                            }
            },
        )
    }
}


@Composable
fun CategoryItem(category: Category, onEdit: () -> Unit, onRemove: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(elevation = 16.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .clickable { expanded = true }
    ) {
        Row(modifier = Modifier.padding(start = 8.dp)) {
            Column(modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f) ) {
                Text(text = "${category.description} - ${category.id}")
            }
            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                DropDownMenuCategory(
                    expanded = expanded,
                    onEdit = { onEdit() },
                    onRemove = {
                        expanded = false
                        onRemove()
                    },
                    onClose = {expanded = false})
            }

        }

    }
}

@Composable
fun DropDownMenuCategory(expanded: Boolean, onEdit: () -> Unit, onRemove: () -> Unit, onClose: () -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onClose() }
    ) {
        DropdownMenuItem(onClick = {
            onEdit()
        }) {
            Text("Edit")
        }
        DropdownMenuItem(onClick = { onRemove() }) {
            Text("Remove")
        }
    }
}




