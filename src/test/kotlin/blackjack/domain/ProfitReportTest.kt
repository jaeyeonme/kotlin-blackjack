package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProfitReportTest {
    @Test
    fun `플레이어별 수익을 계산하고 합을 반대로 집계해 딜러 수익을 만든다`() {
        val pobi = playerWith("pobi", "10000", Rank.ACE, Rank.KING)
        val jason = playerWith("jason", "20000", Rank.TEN, Rank.SEVEN)
        val dealer = Dealer().apply { listOf(Rank.TEN, Rank.NINE).map(::card).forEach(::receive) }

        val report = ProfitReport.from(listOf(pobi, jason), dealer)

        assertThat(report.playerProfits()).containsExactlyEntriesOf(
            mapOf(pobi to Amount.from("15000"), jason to Amount.from("-20000")),
        )
        assertThat(report.dealerProfit()).isEqualTo(Amount.from("5000"))
    }

    private fun playerWith(
        name: String,
        bettingAmount: String,
        vararg ranks: Rank,
    ): Player = Player(name, Amount.from(bettingAmount), Hand(ranks.map(::card)))

    private fun card(rank: Rank): Card = Card(rank, Suit.SPADES)
}
