package blackjack.domain

data class Card(
    val rank: Rank,
    val suit: Suit,
) {
    val score: Int
        get() = rank.score

    override fun toString(): String = "${rank.label}${suit.label}"
}
