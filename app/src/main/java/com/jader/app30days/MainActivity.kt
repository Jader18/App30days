package com.jader.app30days

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jader.app30days.ui.theme.App30daysTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App30daysTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App30days()
                }
            }
        }
    }
}

// Modelo de datos para una foto
data class Photo(
    val dayId: Int,
    val imageResId: Int,
    val titleResID: Int,
    val descriptionResID: Int
)

// Lista estática de fotos
val photos = listOf(
    Photo(
        dayId = 1,
        imageResId = R.drawable.day1,
        titleResID = R.string.t_day1,
        descriptionResID = R.string.d_day1
    ),
    Photo(
        dayId = 2,
        imageResId = R.drawable.day2,
        titleResID = R.string.t_day2,
        descriptionResID = R.string.d_day2
    ),
    Photo(
        dayId = 3,
        imageResId = R.drawable.day3,
        titleResID = R.string.t_day3,
        descriptionResID = R.string.d_day3
    )
)

@Composable
fun App30days() {
    // Estado para rastrear la foto actual
    var currentPhotoIndex by remember { mutableIntStateOf(0) }
    val currentPhoto = photos[currentPhotoIndex]

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Título "Día"
        Text(
            text = "Day ${currentPhoto.dayId}",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Imagen con animación y estilo mejorado
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp)
                .shadow(8.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp)
        ) {
            AnimatedContent(
                targetState = currentPhoto.imageResId,
                transitionSpec = {
                    fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
                },
                modifier = Modifier.fillMaxSize()
            ) { targetImageResId ->
                Image(
                    painter = painterResource(id = targetImageResId),
                    contentDescription = stringResource(id = currentPhoto.titleResID),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Sección para título completo, descripción e indicador de posición
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = currentPhoto.titleResID,
                transitionSpec = {
                    fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
                }
            ) { targetTitleResId ->
                Text(
                    text = stringResource(id = targetTitleResId),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            AnimatedContent(
                targetState = currentPhoto.descriptionResID,
                transitionSpec = {
                    fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
                }
            ) { targetDescriptionResId ->
                Text(
                    text = stringResource(id = targetDescriptionResId),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Text(
                text = "Day ${currentPhotoIndex + 1} / ${photos.size}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Botones Anterior y Siguiente
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    currentPhotoIndex = if (currentPhotoIndex > 0) {
                        currentPhotoIndex - 1
                    } else {
                        photos.size - 1
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Previous")
            }
            Button(
                onClick = {
                    currentPhotoIndex = if (currentPhotoIndex < photos.size - 1) {
                        currentPhotoIndex + 1
                    } else {
                        0
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App30daysTheme {
        App30days()
    }
}