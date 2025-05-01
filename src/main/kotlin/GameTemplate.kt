data class GameTemplate(
    val name: String,
    val description: String,
    val pattern: List<Pair<Int, Int>>
)

object GameTemplates {
    val GLIDER = GameTemplate(
        name = "Glider",
        description = "A simple spaceship that moves diagonally",
        pattern = listOf(
            Pair(1, 0),
            Pair(2, 1),
            Pair(0, 2),
            Pair(1, 2),
            Pair(2, 2)
        )
    )

    val BLINKER = GameTemplate(
        name = "Blinker",
        description = "A simple oscillator that blinks between two states",
        pattern = listOf(
            Pair(1, 0),
            Pair(1, 1),
            Pair(1, 2)
        )
    )

    val BEACON = GameTemplate(
        name = "Beacon",
        description = "A simple oscillator that blinks between two states",
        pattern = listOf(
            Pair(0, 0),
            Pair(0, 1),
            Pair(1, 0),
            Pair(1, 1),
            Pair(2, 2),
            Pair(2, 3),
            Pair(3, 2),
            Pair(3, 3)
        )
    )

    val PULSAR = GameTemplate(
        name = "Pulsar",
        description = "A complex oscillator with a period of 3",
        pattern = listOf(
            Pair(2, 4), Pair(2, 5), Pair(2, 6),
            Pair(2, 10), Pair(2, 11), Pair(2, 12),
            Pair(7, 4), Pair(7, 5), Pair(7, 6),
            Pair(7, 10), Pair(7, 11), Pair(7, 12),
            Pair(9, 4), Pair(9, 5), Pair(9, 6),
            Pair(9, 10), Pair(9, 11), Pair(9, 12),
            Pair(14, 4), Pair(14, 5), Pair(14, 6),
            Pair(14, 10), Pair(14, 11), Pair(14, 12),
            Pair(4, 2), Pair(5, 2), Pair(6, 2),
            Pair(4, 7), Pair(5, 7), Pair(6, 7),
            Pair(4, 9), Pair(5, 9), Pair(6, 9),
            Pair(4, 14), Pair(5, 14), Pair(6, 14),
            Pair(10, 2), Pair(11, 2), Pair(12, 2),
            Pair(10, 7), Pair(11, 7), Pair(12, 7),
            Pair(10, 9), Pair(11, 9), Pair(12, 9),
            Pair(10, 14), Pair(11, 14), Pair(12, 14)
        )
    )

    val ALL_TEMPLATES = listOf(GLIDER, BLINKER, BEACON, PULSAR)
} 