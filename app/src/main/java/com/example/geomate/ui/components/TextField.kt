package com.example.geomate.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// TODO: Figure out the best way to display an error
// TODO: Decide whether different border color on focus is a good idea
// TODO: Change "Color(0xFFF4E8DA)" to "MaterialTheme.colorScheme.secondary" after PR will be accepted and merged
// TODO: Change "Color(0xFF363636)" to "MaterialTheme.colorScheme.onSecondary" after PR will be accepted and merged

data class SupportingButton(
    val text: String,
    val action: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoMateTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    placeholder: String,
    supportingText: String? = null,
    supportingButton: SupportingButton? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Next
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            if (supportingText != null) (-16).dp else 0.dp
        ),
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            shape = CircleShape,
            leadingIcon = {
                leadingIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = Color(0xFF363636)
                    )
                }
            },
            trailingIcon = {
                trailingIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = Color(0xFF363636)
                    )
                }
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFF363636).copy(alpha = .5f)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFF4E8DA),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            singleLine = true,
            supportingText = {
                supportingText?.let {
                    Text(
                        text = it,
                        color = Color(0xFF363636),
                        fontStyle = FontStyle.Italic
                    )
                }
            },
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        )
        supportingButton?.let {
            Text(
                text = it.text,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF363636),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { it.action() }
            )
        }
    }
}

@Preview(name = "Email TextField")
@Composable
private fun EmailTextFieldPreview() {
    GeoMateTextField(
        value = "",
        onValueChange = { },
        leadingIcon = Icons.Outlined.Email,
        placeholder = "Enter your email"
    )
}

@Preview(name = "Password TextField")
@Composable
private fun PasswordTextFieldPreview() {
    GeoMateTextField(
        value = "VeryStrongPassword",
        onValueChange = { },
        leadingIcon = Icons.Outlined.Lock,
        trailingIcon = Icons.Outlined.Visibility,
        placeholder = "Enter your password",
        supportingButton = SupportingButton(
            text = "Forgot password?",
            action = { /* Navigation */ }
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Preview(name = "Bio TextField")
@Composable
private fun BioTextFieldPreview() {
    GeoMateTextField(
        value = "",
        onValueChange = { },
        leadingIcon = Icons.Outlined.PermContactCalendar,
        placeholder = "Describe yourself",
        supportingText = "Optional",
        imeAction = ImeAction.Done
    )
}