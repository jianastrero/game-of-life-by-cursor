import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.Instant

@Composable
fun GameScreen(
    template: GameTemplate?,
    onBack: () -> Unit
) {
    val gridSize = 50
    val game = remember { GameOfLife(gridSize, gridSize) }
    var isPlaying by remember { mutableStateOf(false) }
    var generation by remember { mutableStateOf(0) }
    var startTime by remember { mutableStateOf<Instant?>(null) }
    var endTime by remember { mutableStateOf<Instant?>(null) }
    var isStable by remember { mutableStateOf(false) }
    var grid by remember { mutableStateOf(game.getGrid()) }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            startTime = Instant.now()
            endTime = null
            isStable = false
            while (isPlaying && !isStable) {
                game.nextGeneration()
                grid = game.getGrid()
                generation++
                isStable = game.isStable()
                if (isStable) {
                    endTime = Instant.now()
                    isPlaying = false
                }
                delay(100)
            }
        }
    }

    LaunchedEffect(template) {
        template?.let {
            game.initialize(it)
            grid = game.getGrid()
            generation = 0
            isPlaying = false
            startTime = null
            endTime = null
            isStable = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Generation: $generation",
                style = MaterialTheme.typography.h6
            )
            
            if (isStable && endTime != null && startTime != null) {
                val duration = Duration.between(startTime, endTime)
                Text(
                    text = "Time to stability: ${formatDuration(duration)}",
                    style = MaterialTheme.typography.h6
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            val cellWidth = size.width / gridSize
            val cellHeight = size.height / gridSize

            // Draw grid lines
            for (i in 0..gridSize) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(i * cellWidth, 0f),
                    end = Offset(i * cellWidth, size.height),
                    strokeWidth = 1f
                )
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, i * cellHeight),
                    end = Offset(size.width, i * cellHeight),
                    strokeWidth = 1f
                )
            }

            // Draw cells
            for (x in 0 until gridSize) {
                for (y in 0 until gridSize) {
                    if (grid[x][y]) {
                        drawRect(
                            color = Color.Black,
                            topLeft = Offset(x * cellWidth, y * cellHeight),
                            size = androidx.compose.ui.geometry.Size(cellWidth, cellHeight)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { isPlaying = !isPlaying },
                enabled = !isStable
            ) {
                Text(if (isPlaying) "Pause" else "Play")
            }

            Button(
                onClick = {
                    template?.let {
                        game.initialize(it)
                        grid = game.getGrid()
                        generation = 0
                        isPlaying = false
                        startTime = null
                        endTime = null
                        isStable = false
                    }
                }
            ) {
                Text("Reset")
            }

            Button(onClick = onBack) {
                Text("Back to Menu")
            }
        }
    }
}

private fun formatDuration(duration: Duration): String {
    val seconds = duration.seconds
    return when {
        seconds < 60 -> "$seconds seconds"
        seconds < 3600 -> "${seconds / 60} minutes ${seconds % 60} seconds"
        else -> "${seconds / 3600} hours ${(seconds % 3600) / 60} minutes"
    }
} 