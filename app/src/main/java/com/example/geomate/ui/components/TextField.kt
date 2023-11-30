package com.example.geomate.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.geomate.ui.theme.spacing

data class SupportingButton(
    val text: String,
    val onClick: () -> Unit,
)

data class TextFieldIcon(
    val onClick: (() -> Unit)? = null,
    val icon: @Composable (Modifier) -> Unit,
)

data class InputValidator(
    val isValid: Boolean,
    val updateIsValid: (Boolean) -> Unit,
    val rule: (String) -> Boolean,
    val errorMessage: String,
)

@Composable
fun GeoMateTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcons: List<TextFieldIcon> = listOf(),
    trailingIcons: List<TextFieldIcon> = listOf(),
    placeholder: String,
    supportingText: String? = null,
    supportingButton: SupportingButton? = null,
    inputValidator: InputValidator? = null,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.onSecondary,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
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
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 13.dp)
                ) {
                    leadingIcons.forEach { leadingIcon ->
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
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 13.dp)
                ) {
                    trailingIcons.forEach { trailingIcon ->
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = contentColor,
                unfocusedTextColor = contentColor,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                errorContainerColor = containerColor,
                cursorColor = contentColor,
                errorCursorColor = contentColor,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
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
            keyboardActions = keyboardActions,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
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
