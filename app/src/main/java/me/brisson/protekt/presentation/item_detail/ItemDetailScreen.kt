package me.brisson.protekt.presentation.item_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.R
import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.ui.theme.montserrat

@Composable
fun ItemDetailScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onEdit: (itemId: String) -> Unit,
    viewModel: ItemDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, end = 24.dp, start = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIos,
                    contentDescription = stringResource(id = R.string.icon_arrow_back_content_description)
                )
            }
            TextButton(
                onClick = { uiState.item?.let { onEdit(it.id) } },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onBackground)
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = stringResource(id = R.string.icon_edit_content_description)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(id = R.string.edit),
                    style = TextStyle(
                        fontFamily = montserrat,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        uiState.item?.let { item ->

            Text(
                modifier = Modifier.padding(24.dp),
                text = stringResource(id = item.itemType.stringResId),
                style = TextStyle(
                    fontFamily = montserrat,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            when (item) {
                is Credential -> {
                    CredentialDetails(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        credential = item
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemDetailScreen() {
    ProteKTTheme {
        ItemDetailScreen(onBack = { }, onEdit = { })
    }
}