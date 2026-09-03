package blackjack.application

import blackjack.domain.Card
import blackjack.domain.Deck
import blackjack.domain.Player
import blackjack.domain.Rank
import blackjack.domain.Suit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackjackGameTest {
    @Test
    fun `참가자마다 카드 두 장을 지급한 뒤 현재 손패를 출력한다`() {
        val input = StubInput("pobi, jason", mapOf("pobi" to listOf(false), "jason" to listOf(false)))
        val output = RecordingOutput()
        val deck = Deck.ordered(cards(Rank.TEN, Rank.SEVEN, Rank.FIVE, Rank.SIX))

        BlackjackGame(input, output, deck).start()

        assertThat(output.initial).containsExactly(HandRecord("pobi", 17), HandRecord("jason", 11))
        assertThat(output.final).isEqualTo(output.initial)
    }

    @Test
    fun `y를 선택하면 카드를 지급하고 갱신된 손패를 출력한 뒤 다시 묻는다`() {
        val input = StubInput("pobi", mapOf("pobi" to listOf(true, false)))
        val output = RecordingOutput()
        val deck = Deck.ordered(cards(Rank.FIVE, Rank.SIX, Rank.SEVEN))

        BlackjackGame(input, output, deck).start()

        assertThat(output.updated).containsExactly(HandRecord("pobi", 18))
        assertThat(input.askedPlayers).containsExactly("pobi", "pobi")
    }

    @Test
    fun `카드를 받은 뒤 21을 초과하면 추가 선택 없이 차례를 종료한다`() {
        val input = StubInput("pobi", mapOf("pobi" to listOf(true)))
        val output = RecordingOutput()
        val deck = Deck.ordered(cards(Rank.TEN, Rank.NINE, Rank.FIVE))

        BlackjackGame(input, output, deck).start()

        assertThat(input.askedPlayers).containsExactly("pobi")
        assertThat(output.final).containsExactly(HandRecord("pobi", 24))
    }

    private fun cards(vararg ranks: Rank): List<Card> = ranks.map { Card(it, Suit.SPADES) }
}

private class StubInput(
    private val names: String,
    decisions: Map<String, List<Boolean>>,
) : GameInput {
    private val decisions = decisions.mapValues { ArrayDeque(it.value) }
    val askedPlayers = mutableListOf<String>()

    override fun readPlayerNames(): String = names

    override fun wantsHit(player: Player): Boolean {
        askedPlayers.add(player.name)
        return decisions.getValue(player.name).removeFirst()
    }
}

private class RecordingOutput : GameOutput {
    val initial = mutableListOf<HandRecord>()
    val updated = mutableListOf<HandRecord>()
    val final = mutableListOf<HandRecord>()

    override fun showInitialHands(players: List<Player>) {
        initial.addAll(players.map(::record))
    }

    override fun showHand(player: Player) {
        updated.add(record(player))
    }

    override fun showFinalHands(players: List<Player>) {
        final.addAll(players.map(::record))
    }

    private fun record(player: Player): HandRecord = HandRecord(player.name, player.score())
}

private data class HandRecord(
    val name: String,
    val score: Int,
)
