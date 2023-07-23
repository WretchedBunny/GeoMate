package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PermContactCalendar
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geomate.ui.theme.GeoMateTheme

data class SupportingButton(
    val text: String,
    val onClick: () -> Unit
)

data class LeadingIcon(
    val icon: ImageVector,
    val onClick: (() -> Unit)? = null
)

data class TrailingIcon(
    val icon: ImageVector,
    val onClick: (() -> Unit)? = null
)

data class InputValidator(
    val rule: (String) -> Boolean,
    val errorMessage: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoMateTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: LeadingIcon? = null,
    trailingIcon: TrailingIcon? = null,
    placeholder: String,
    supportingText: String? = null,
    supportingButton: SupportingButton? = null,
    inputValidator: InputValidator? = null,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Next
) {
    var isValid by remember { mutableStateOf(true) }
    var wasFocusedOnce by remember { mutableStateOf(false) }
    var isInFocus by remember { mutableStateOf(false) }

    LaunchedEffect(isInFocus) {
        if (wasFocusedOnce && !isInFocus) {
            inputValidator?.let { validator ->
                isValid = validator.rule(value)
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(
            if (supportingText != null || !isValid) (-16).dp else 0.dp
        ),
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                if (!isValid) {
                    isValid = inputValidator?.rule?.invoke(it) ?: true
                }
            },
            shape = CircleShape,
            leadingIcon = {
                leadingIcon?.let {
                    val leadingIconModifier = it.onClick?.let {
                        Modifier.clickable { it() }
                    } ?: run { Modifier }
                    Icon(
                        imageVector = it.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = leadingIconModifier
                    )
                }
            },
            trailingIcon = {
                trailingIcon?.let {
                    val trailingIconModifier = it.onClick?.let {
                        Modifier.clickable { it() }
                    } ?: run { Modifier }
                    Icon(
                        imageVector = it.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = trailingIconModifier
                    )
                }
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = .5f)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                errorCursorColor = Color.Transparent,
                errorSupportingTextColor = MaterialTheme.colorScheme.error
            ),
            singleLine = singleLine,
            supportingText = {
                if (!isValid && inputValidator != null) {
                    Text(
                        text = inputValidator.errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                } else if (supportingText != null) {
                    Text(
                        text = supportingText,
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontStyle = FontStyle.Italic
                    )
                }
            },
            isError = !isValid,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (wasFocusedOnce && !it.isFocused) {
                        isInFocus = false
                    } else if (it.isFocused) {
                        wasFocusedOnce = true
                        isInFocus = true
                    }
                }
        )
        supportingButton?.let {
            Text(
                text = it.text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { it.onClick() }
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFF7F0
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF2A2A2A
)
@Composable
private fun EmailTextFieldPreview() {
    GeoMateTheme {
        GeoMateTextField(
            value = "",
            onValueChange = { },
            leadingIcon = LeadingIcon(Icons.Outlined.Email),
            placeholder = "Enter your email",
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFF7F0
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF2A2A2A
)
@Composable
private fun PasswordTextFieldPreview() {
    GeoMateTheme {
        GeoMateTextField(
            value = "VeryStrongPassword",
            onValueChange = { },
            leadingIcon = LeadingIcon(Icons.Outlined.Lock),
            trailingIcon = TrailingIcon(
                icon = Icons.Outlined.Visibility,
                onClick = { /* Show/hide password */ }
            ),
            placeholder = "Enter your password",
            supportingButton = SupportingButton(
                text = "Forgot password?",
                onClick = { /* Navigation */ }
            ),
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFF7F0
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF2A2A2A
)
@Composable
private fun BioTextFieldPreview() {
    GeoMateTheme {
        GeoMateTextField(
            value = "",
            onValueChange = { },
            leadingIcon = LeadingIcon(Icons.Outlined.PermContactCalendar),
            placeholder = "Describe yourself",
            supportingText = "Optional",
            imeAction = ImeAction.Done
        )
    }
}