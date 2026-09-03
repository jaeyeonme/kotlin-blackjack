package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerTest {
    @Test
    fun `플레이어는 받은 카드와 손패 점수를 제공한다`() {
        val player = Player("pobi")

        player.receive(card(Rank.ACE))
        player.receive(card(Rank.KING))

        assertThat(player.cards()).containsExactly(card(Rank.ACE), card(Rank.KING))
        assertThat(player.score()).isEqualTo(21)
    }

    @Test
    fun `점수가 21을 초과하면 카드를 더 받을 수 없다`() {
        val player = Player("pobi", Hand(listOf(card(Rank.KING), card(Rank.QUEEN), card(Rank.TWO))))

        assertThat(player.canHit()).isFalse()
    }

    private fun card(rank: Rank): Card = Card(rank, Suit.SPADES)
}
