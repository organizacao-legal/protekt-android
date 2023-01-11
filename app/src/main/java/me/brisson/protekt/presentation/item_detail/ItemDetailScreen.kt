package me.brisson.protekt.presentation.item_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.brisson.protekt.R
import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat

@Composable
fun ItemDetailScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onEdit: (itemId: String) -> Unit,
    viewModel: ItemDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var menuExtended by remember { mutableStateOf(false) }

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
            IconButton(
                onClick = { menuExtended = true },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dot_menu),
                    contentDescription = stringResource(id = R.string.icon_menu_content_description)
                )

                ItemOptionsDropdown(
                    modifier = Modifier.width(150.dp),
                    expanded = menuExtended,
                    onDismissRequest = { menuExtended = false },
                    onEdit = {
                        uiState.item?.let { onEdit(it.id) }
                        menuExtended = false
                    },
                    onDelete =  {
                        //todo: show delete dialog
                        menuExtended = false
                    }
                )
            }

        }

        uiState.item?.let { item ->

            Text(
                modifier = Modifier.padding(24.dp),
                text = stringResource(id = item.type.stringResId),
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