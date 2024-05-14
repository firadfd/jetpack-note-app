package fd.firad.wishwrite.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fd.firad.wishwrite.ui.theme.ContentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    search: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    val keyboardState = LocalSoftwareKeyboardController.current
    TextField(
        value = search,
        onValueChange = {
            onValueChange.invoke(it)
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardState?.hide()
        }),
        modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = Color.Black,
            containerColor = ContentColor,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Black)
        },
        trailingIcon = {
            if (search.isNotEmpty()) {
                IconButton(onClick = {
                    onValueChange("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.Black
                    )

                }
            }
        },
        placeholder = {
            Text(
                text = "Search Note", style = TextStyle(
                    color = Color.Black.copy(0.5f), fontSize = 14.sp
                )
            )
        },
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CustomTitleTextField(
    text: String, hintText: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            onValueChange.invoke(it)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboard?.hide()
        }),
        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(text = hintText, style = TextStyle(color = Color.Black.copy(alpha = .7f)))
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(color = Color.Black),
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDesTextField(
    text: String, hintText: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.size(400.dp),
        value = text,
        onValueChange = {
            onValueChange.invoke(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(text = hintText, style = TextStyle(color = Color.Black.copy(alpha = .7f)))
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(color = Color.Black),
    )

}

@Composable
fun ShowDialogueBox(
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onClose: () -> Unit,
    onConfirmClicked: () -> Unit
) {
    val focusRequest = FocusRequester()
    LaunchedEffect(key1 = true) {
        focusRequest.requestFocus()
    }
    AlertDialog(onDismissRequest = {

    }, confirmButton = {
        Button(onClick = {
            onConfirmClicked.invoke()
        }) {
            Text(text = "Add")
        }
    }, dismissButton = {
        Button(onClick = {
            onClose.invoke()
        }) {
            Text(text = "Dismiss")
        }

    },
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                Icon(imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.clickable {
                        onClose.invoke()
                    })
            }
        }, shape = RoundedCornerShape(10.dp), containerColor = ContentColor, text = {
            Column(
                modifier = Modifier
                    .size(450.dp)
            ) {
                CustomTitleTextField(
                    text = title,
                    hintText = "Enter Note Title ...",
                    onValueChange = onTitleChange,
                    modifier = Modifier.focusRequester(focusRequest)
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomDesTextField(
                    text = description,
                    hintText = "Enter Your Note ...",
                    onValueChange = onDescriptionChange
                )
            }
        })

}