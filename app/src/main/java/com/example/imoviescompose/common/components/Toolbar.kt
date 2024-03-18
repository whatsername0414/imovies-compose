package com.example.imoviescompose.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imoviescompose.ui.theme.Primary
import com.example.imoviescompose.ui.theme.White0F0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String? = "",
    searchPlaceholder: String? = "",
    backButtonIcon: ImageVector? = Icons.Rounded.ArrowBack,
    actionIcon: ImageVector? = null,
    onBackButtonClicked: (() -> Unit)? = null,
    onActionClicked: (() -> Unit)? = null,
    onSearchSubmitted: ((String) -> Unit)? = null,
) {
    var text by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(horizontal = 16.dp)
            .background(color = Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (backButtonIcon != null) {
            Icon(
                modifier = Modifier
                    .clickable { onBackButtonClicked?.invoke() },
                imageVector = backButtonIcon,
                contentDescription = "back"
            )
        }

        if (!title.isNullOrBlank()) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Primary
            )
        }

        if (!searchPlaceholder.isNullOrBlank()) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                value = text,
                onValueChange = {
                    text = it
                                },
                placeholder = { Text(searchPlaceholder) },
                shape = RoundedCornerShape(16.dp),
                leadingIcon  = {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                onSearchSubmitted?.invoke(text)
                            },
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "search"
                    )
                },
                trailingIcon = {
                    if (text.isNotBlank()) Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                text = ""
                                       },
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "clear"
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchSubmitted?.invoke(text)
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = White0F0,
                    focusedIndicatorColor =  Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        if (actionIcon != null) {
            Icon(
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onActionClicked?.invoke() },
                imageVector = actionIcon,
                tint = Primary,
                contentDescription = "action"
            )
        } else {
            Box(
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}