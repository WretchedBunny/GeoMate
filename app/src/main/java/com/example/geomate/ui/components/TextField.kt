package com.example.geomate.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geomate.ui.theme.GeoMateTheme
import com.example.geomate.ui.theme.spacing

data class SupportingButton(
    val text: String,
    val onClick: () -> Unit,
)

data class LeadingIcon(
    val icon: @Composable (Modifier) -> Unit,
    val onClick: (() -> Unit)? = null,
)

data class TrailingIcon(
    val icon: @Composable (Modifier) -> Unit,
    val onClick: (() -> Unit)? = null,
)

data class InputValidator(
    val isValid: Boolean,
    val updateIsValid: (Boolean) -> Unit,
    val rule: (String) -> Boolean,
    val errorMessage: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoMateTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcons: List<LeadingIcon>? = null,
    trailingIcons: List<TrailingIcon>? = null,
    placeholder: String,
    supportingText: String? = null,
    supportingButton: SupportingButton? = null,
    inputValidator: InputValidator? = null,
    containerColor: Color,
    contentColor: Color,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Next,
) {
    val isValid = inputValidator?.isValid ?: true

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
                    inputValidator?.updateIsValid?.let { updateIsValid ->
                        updateIsValid(inputValidator.rule.invoke(it))
                    }
                }
            },
            shape = CircleShape,
            leadingIcon = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                    modifier = Modifier.padding(horizontal = 13.dp)
                ) {
                    leadingIcons?.forEach { leadingIcon ->
                        leadingIcon.icon(
                            if (leadingIcon.onClick != null) {
                                Modifier.clickable { leadingIcon.onClick.invoke() }
                            } else {
                                Modifier
                            }
                        )
                    }
                }
            },
            trailingIcon = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                    modifier = Modifier.padding(horizontal = 13.dp)
                ) {
                    trailingIcons?.forEach { trailingIcon ->
                        trailingIcon.icon(
                            if (trailingIcon.onClick != null) {
                                Modifier.clickable { trailingIcon.onClick.invoke() }
                            } else {
                                Modifier
                            }
                        )
                    }
                }
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = .5f)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = contentColor,
                containerColor = containerColor,
                focusedBorderColor = Color.Transparent,
                cursorColor = contentColor,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                errorCursorColor = contentColor,
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
                        color = contentColor,
                        fontStyle = FontStyle.Italic
                    )
                }
            },
            isError = !isValid,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
            modifier = Modifier.fillMaxWidth()
        )
        supportingButton?.let {
            Text(
                text = it.text,
                style = MaterialTheme.typography.bodySmall,
                color = contentColor,
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
            leadingIcons = listOf(
                LeadingIcon(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null
                        )
                    }
                )
            ),
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
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
            leadingIcons = listOf(
                LeadingIcon(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = null
                        )
                    }
                )
            ),
            trailingIcons = listOf(
                TrailingIcon(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Visibility,
                            contentDescription = null
                        )
                    },
                    onClick = { /* Show/hide password */ }
                ),
                TrailingIcon(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Visibility,
                            contentDescription = null
                        )
                    },
                    onClick = { /* Show/hide password */ }
                )
            ),
            placeholder = "Enter your password",
            supportingButton = SupportingButton(
                text = "Forgot password?",
                onClick = { /* Navigation */ }
            ),
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
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
            leadingIcons = listOf(
                LeadingIcon(
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.PermContactCalendar,
                            contentDescription = null
                        )
                    }
                )
            ),
            placeholder = "Describe yourself",
            supportingText = "Optional",
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            imeAction = ImeAction.Done
        )
    }
}