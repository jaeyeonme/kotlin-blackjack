package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerResultTest {
    @Test
    fun `플레이어만 블랙잭이면 베팅 금액의 1점5배를 얻는다`() {
        val player = playerWith(Rank.ACE, Rank.KING)
        val dealer = dealerWith(Rank.TEN, Rank.NINE)

        val result = PlayerResult.from(player, dealer)

        assertThat(result).isEqualTo(PlayerResult.BLACKJACK)
        assertThat(result.profit(player.bettingAmount)).isEqualTo(Amount.from("15000"))
    }

    @Test
    fun `플레이어와 딜러가 모두 블랙잭이면 수익은 없다`() {
        val player = playerWith(Rank.ACE, Rank.KING)
        val dealer = dealerWith(Rank.ACE, Rank.QUEEN)

        val result = PlayerResult.from(player, dealer)

        assertThat(result).isEqualTo(PlayerResult.DRAW)
        assertThat(result.profit(player.bettingAmount)).isEqualTo(Amount.from("0"))
    }

    @Test
    fun `플레이어가 Bust면 딜러 점수와 관계없이 베팅 금액을 잃는다`() {
        val player = playerWith(Rank.KING, Rank.QUEEN, Rank.TWO)
        val dealer = dealerWith(Rank.KING, Rank.QUEEN, Rank.TWO)

        val result = PlayerResult.from(player, dealer)

        assertThat(result).isEqualTo(PlayerResult.LOSE)
        assertThat(result.profit(player.bettingAmount)).isEqualTo(Amount.from("-10000"))
    }

    @Test
    fun `딜러가 Bust면 남아 있는 플레이어가 베팅 금액만큼 얻는다`() {
        val player = playerWith(Rank.TEN, Rank.SEVEN)
        val dealer = dealerWith(Rank.KING, Rank.QUEEN, Rank.TWO)

        val result = PlayerResult.from(player, dealer)

        assertThat(result).isEqualTo(PlayerResult.WIN)
        assertThat(result.profit(player.bettingAmount)).isEqualTo(Amount.from("10000"))
    }

    @Test
    fun `일반 승패와 무승부는 각각 1배 0배 마이너스 1배 수익을 만든다`() {
        assertThat(resultOf(20, 19).profit(Amount.from("10000"))).isEqualTo(Amount.from("10000"))
        assertThat(resultOf(19, 19).profit(Amount.from("10000"))).isEqualTo(Amount.from("0"))
        assertThat(resultOf(18, 19).profit(Amount.from("10000"))).isEqualTo(Amount.from("-10000"))
    }

    private fun resultOf(
        playerScore: Int,
        dealerScore: Int,
    ): PlayerResult = PlayerResult.from(playerWithScore(playerScore), dealerWithScore(dealerScore))

    private fun playerWith(vararg ranks: Rank): Player = Player("pobi", Amount.from("10000"), Hand(ranks.map(::card)))

    private fun dealerWith(vararg ranks: Rank): Dealer = Dealer().apply { ranks.map(::card).forEach(::receive) }

    private fun playerWithScore(score: Int): Player = playerWith(*ranksFor(score))

    private fun dealerWithScore(score: Int): Dealer = dealerWith(*ranksFor(score))

    private fun ranksFor(score: Int): Array<Rank> = arrayOf(Rank.TEN, Rank.entries.first { it.score == score - 10 })

    private fun card(rank: Rank): Card = Card(rank, Suit.SPADES)
}
