fun main() {
    val seedToSoilMap = mutableMapOf<LongRange, Long>()
    val soilToFertilizerMap = mutableMapOf<LongRange, Long>()
    val fertilizerToWaterMap = mutableMapOf<LongRange, Long>()
    val waterToLightMap = mutableMapOf<LongRange, Long>()
    val lightToTemperatureMap = mutableMapOf<LongRange, Long>()
    val temperatureToHumidityMap = mutableMapOf<LongRange, Long>()
    val humidityToLocationMap = mutableMapOf<LongRange, Long>()
    
    fun translate(value: Long, map: Map<LongRange, Long>): Long {
        val initial: Pair<LongRange, Long>? = null

        val (srcRange, diff) = map.entries
            .fold(initial) { acc, (srcRange, diff) ->
                if (acc == null && value in srcRange)
                    srcRange to diff
                else
                    acc
            } ?: (null to null)

        return if (srcRange == null || diff == null)
            value
        else
            value + diff
    }

    fun lowestLocation(seeds: List<Long>): Long =
        seeds
            .asSequence()
            .map { translate(it, seedToSoilMap) }
            .map { translate(it, soilToFertilizerMap) }
            .map { translate(it, fertilizerToWaterMap) }
            .map { translate(it, waterToLightMap) }
            .map { translate(it, lightToTemperatureMap) }
            .map { translate(it, temperatureToHumidityMap) }
            .map { translate(it, humidityToLocationMap) }
            .minOf { it }
    
    fun initMaps(input: List<String>) {
        seedToSoilMap.clear()
        soilToFertilizerMap.clear()
        fertilizerToWaterMap.clear()
        waterToLightMap.clear()
        lightToTemperatureMap.clear()
        temperatureToHumidityMap.clear()
        humidityToLocationMap.clear()
        
        var curMap: MutableMap<LongRange, Long>? = null
        for (i in 1..<input.size) {
            when {
                input[i].isEmpty() -> curMap = null
                input[i] == "seed-to-soil map:" -> curMap = seedToSoilMap
                input[i] == "soil-to-fertilizer map:" -> curMap = soilToFertilizerMap
                input[i] == "fertilizer-to-water map:" -> curMap = fertilizerToWaterMap
                input[i] == "water-to-light map:" -> curMap = waterToLightMap
                input[i] == "light-to-temperature map:" -> curMap = lightToTemperatureMap
                input[i] == "temperature-to-humidity map:" -> curMap = temperatureToHumidityMap
                input[i] == "humidity-to-location map:" -> curMap = humidityToLocationMap
                curMap != null -> {
                    val (destStart, srcStart, length) = input[i].split(' ').map(String::toLong)

                    val srcEnd = srcStart + length - 1
                    curMap[srcStart..srcEnd] = destStart - srcStart
                }
            }
        }
    }

    fun part1(input: List<String>): Long {
        val seedRanges = input[0].substring(7).split(' ').map { it.toLong() }

        initMaps(input)
        
        return lowestLocation(seedRanges)
    }

    fun part2(input: List<String>): Long {
        return 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 1L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
