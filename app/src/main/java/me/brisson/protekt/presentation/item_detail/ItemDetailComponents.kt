package me.brisson.protekt.presentation.item_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.R
import me.brisson.protekt.ui.theme.DarkGray
import me.brisson.protekt.ui.theme.montserrat

@Composable
fun CredentialDetails(
    modifier: Modifier = Modifier,
    credential: Credential
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            AsyncImage(
                modifier = Modifier
                    .height(60.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp)),
                model = credential.image,
                contentDescription = stringResource(
                    id = R.string.credential_image_content_description,
                    credential.name
                ),
                placeholder = painterResource(id = R.drawable.image_placeholder),
                error = painterResource(id = R.drawable.image_placeholder)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = credential.name,
                    style = TextStyle(
                        fontFamily = montserrat,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape)
                        .clickable { }
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = credential.url,
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontSize = 13.sp,
                            color = DarkGray,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Icon(
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .size(14.dp),
                        painter = painterResource(id = R.drawable.external_link_icon),
                        tint = DarkGray,
                        contentDescription = null
                    )
                }

            }
        }

    }
}

@Preview
@Composable
fun PreviewCredentialDetails() {
    ProteKTTheme {
        CredentialDetails(
            credential = Credential(
                image = "https://logo.clearbit.com/https://twitter.com",
                name = "Twitter",
                username = "@JonDoe",
                url = "http://twitter.com"
            )
        )
    }
}