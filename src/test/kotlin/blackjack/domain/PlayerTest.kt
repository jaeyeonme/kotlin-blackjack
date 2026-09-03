package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerTest {
    @Test
    fun `플레이어는 받은 카드와 손패 점수를 제공한다`() {
        val player = Player("pobi", Amount.from("10000"))

        player.receive(card(Rank.ACE))
        player.receive(card(Rank.KING))

        assertThat(player.cards()).containsExactly(card(Rank.ACE), card(Rank.KING))
        assertThat(player.score()).isEqualTo(21)
        assertThat(player.bettingAmount).isEqualTo(Amount.from("10000"))
    }

    @Test
    fun `점수가 21을 초과하면 카드를 더 받을 수 없다`() {
        val player =
            Player(
                "pobi",
                Amount.from("10000"),
                Hand(listOf(card(Rank.KING), card(Rank.QUEEN), card(Rank.TWO))),
            )

        assertThat(player.canHit()).isFalse()
    }

    @Test
    fun `최초 두 장의 합이 21이면 블랙잭이다`() {
        val player = playerWith(Rank.ACE, Rank.KING)

        assertThat(player.isBlackjack()).isTrue()
    }

    @Test
    fun `세 장 이상의 카드 합이 21이면 블랙잭이 아니다`() {
        val player = playerWith(Rank.ACE, Rank.FIVE, Rank.FIVE)

        assertThat(player.isBlackjack()).isFalse()
    }

    @Test
    fun `카드 합이 21을 초과하면 Bust다`() {
        val player = playerWith(Rank.KING, Rank.QUEEN, Rank.TWO)

        assertThat(player.isBust()).isTrue()
    }

    private fun playerWith(vararg ranks: Rank): Player = Player("pobi", Amount.from("10000"), Hand(ranks.map(::card)))

    private fun card(rank: Rank): Card = Card(rank, Suit.SPADES)
}
