package blackjack.domain

class ProfitReport private constructor(
    playerProfits: Map<Player, Amount>,
) {
    private val playerProfits = playerProfits.toMap()

    fun playerProfits(): Map<Player, Amount> = playerProfits.toMap()

    fun dealerProfit(): Amount = -playerProfits.values.fold(Amount.ZERO, Amount::plus)

    companion object {
        fun from(
            players: List<Player>,
            dealer: Dealer,
        ): ProfitReport =
            ProfitReport(
                players.associateWith { PlayerResult.from(it, dealer).profit(it.bettingAmount) },
            )
    }
}
