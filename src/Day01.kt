fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            val digits = line.filter { c -> c.isDigit() }
            val f = digits.first()
            val l = digits.last()

            val value = "$f$l".toInt()
            sum += value

            println("$value : $line")
        }
        println("$sum")
        
        return sum
    }

    fun part2(input: List<String>): Int {
        val digitStrings = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9)
        
        var sum = 0
        input.forEach { line ->
            val digits: MutableList<Int> = mutableListOf()
            
            line.forEachIndexed { cur, c ->
                when {
                    c.isDigit() -> {
                        digits += c.digitToInt()
                    }
                    cur + 3 <= line.length -> {
                        val end = line.length - cur
                        for (len in 3..end) {
                            val candidate = line.substring(cur, cur + len)
                            if (digitStrings.containsKey(candidate)) {
                                digits += digitStrings[candidate]!!
                            }
                        }
                    }
                }
            }
            
            val f = digits.first().digitToChar()
            val l = digits.last().digitToChar()

            val value = "$f$l".toInt()
            sum += value

            println("$value : $line")
        }
        println("$sum")

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    val test2Input = readInput("Day01_test2")
    check(part2(test2Input) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
