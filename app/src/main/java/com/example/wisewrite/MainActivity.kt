package com.example.wisewrite

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wisewrite.model.Notes
import com.example.wisewrite.screen.AppSearchBar
import com.example.wisewrite.screen.ShowDialogueBox
import com.example.wisewrite.state.NoteResponseState
import com.example.wisewrite.ui.theme.Background
import com.example.wisewrite.ui.theme.ContentColor
import com.example.wisewrite.ui.theme.WiseWriteTheme
import com.example.wisewrite.viewmodel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: NotesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        setContent {
            WiseWriteTheme {
                MainAppScreen(viewModel)
            }
        }
    }
}

/**
 * implement search note
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainAppScreen(viewModel: NotesViewModel) {
    val noteState = viewModel.note.collectAsState()

    val successState = noteState.value as? NoteResponseState.Success

    val search = remember {
        mutableStateOf("")
    }

    val id = remember {
        mutableStateOf(0)
    }
    val title = remember {
        mutableStateOf("")
    }
    val des = remember {
        mutableStateOf("")
    }
    val uid = remember {
        mutableStateOf(0)
    }
    val utitle = remember {
        mutableStateOf("")
    }
    val udes = remember {
        mutableStateOf("")
    }
    val showDialog = remember {
        mutableStateOf(false)
    }
    val showUpdateDialog = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog.value = true
            }) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        }, floatingActionButtonPosition = FabPosition.End) { paddingValues ->

            if (showDialog.value) {
                ShowDialogueBox(title.value, des.value, {
                    title.value = it
                }, {
                    des.value = it
                }, {
                    showDialog.value = false
                }, {
                    viewModel.addNotes(notes = Notes(title = title.value, descriptions = des.value))
                    showDialog.value = false
                })
            }
            if (showUpdateDialog.value) {
                ShowDialogueBox(utitle.value, udes.value, {
                    utitle.value = it
                }, {
                    udes.value = it
                }, {
                    showUpdateDialog.value = false
                }, {
                    viewModel.updateNotes(
                        notes = Notes(
                            id = uid.value,
                            title = utitle.value,
                            descriptions = udes.value
                        )
                    )
                    showUpdateDialog.value = false
                })
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Background)
            ) {
                AppSearchBar(search = search.value, onValueChange = {
                    search.value = it
                }, modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 15.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    if (successState != null) {
                        val notes = successState.data
                        items(notes) { note ->
                            NoteItem(
                                notes = note,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .width(250.dp),
                                onDelete = {
                                    viewModel.deleteNotes(note)
                                }, {
                                    showUpdateDialog.value = true
                                    uid.value = note.id!!
                                    utitle.value = note.title
                                    udes.value = note.descriptions
                                }
                            )
                        }
                    } else {

                    }
                }
            }
        }
    }
}

@Composable
private fun NoteItem(
    notes: Notes,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onUpdateClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
            .clickable {
                onUpdateClicked.invoke()
            }
            .background(color = ContentColor, shape = RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = notes.title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600
                    ),
                    modifier = Modifier
                        .weight(0.7f)
                )
                IconButton(onClick = {
                    onDelete.invoke()
                }, modifier = Modifier.weight(.3f)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = notes.descriptions,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W200
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
