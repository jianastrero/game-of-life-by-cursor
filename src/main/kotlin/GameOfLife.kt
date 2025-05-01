class GameOfLife(private val width: Int, private val height: Int) {
    private var grid = Array(width) { BooleanArray(height) }
    private var previousGrid = Array(width) { BooleanArray(height) }
    private var isStable = false

    fun initialize(template: GameTemplate) {
        grid = Array(width) { BooleanArray(height) }
        val centerX = width / 2
        val centerY = height / 2
        
        template.pattern.forEach { (x, y) ->
            val actualX = centerX + x
            val actualY = centerY + y
            if (actualX in 0 until width && actualY in 0 until height) {
                grid[actualX][actualY] = true
            }
        }
    }

    fun nextGeneration() {
        previousGrid = grid.map { it.clone() }.toTypedArray()
        val newGrid = Array(width) { BooleanArray(height) }

        for (x in 0 until width) {
            for (y in 0 until height) {
                val neighbors = countNeighbors(x, y)
                newGrid[x][y] = when {
                    grid[x][y] && (neighbors < 2 || neighbors > 3) -> false
                    !grid[x][y] && neighbors == 3 -> true
                    else -> grid[x][y]
                }
            }
        }

        grid = newGrid
        checkStability()
    }

    private fun countNeighbors(x: Int, y: Int): Int {
        var count = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val newX = (x + i + width) % width
                val newY = (y + j + height) % height
                if (grid[newX][newY]) count++
            }
        }
        return count
    }

    private fun checkStability() {
        isStable = grid.contentDeepEquals(previousGrid)
    }

    fun isCellAlive(x: Int, y: Int): Boolean = grid[x][y]
    fun isStable(): Boolean = isStable
    fun getGrid(): Array<BooleanArray> = grid
} 