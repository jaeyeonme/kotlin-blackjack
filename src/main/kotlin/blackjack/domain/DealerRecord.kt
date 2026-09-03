package blackjack.domain

class DealerRecord(
    results: List<PlayerResult>,
) {
    private val results = results.toList()

    fun wins(): Int = results.count { it == PlayerResult.LOSE }

    fun draws(): Int = results.count { it == PlayerResult.DRAW }

    fun losses(): Int = results.count { it == PlayerResult.WIN || it == PlayerResult.BLACKJACK }
}
