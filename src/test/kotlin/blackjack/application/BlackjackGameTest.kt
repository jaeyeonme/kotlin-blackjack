package blackjack.application

import blackjack.domain.Amount
import blackjack.domain.Card
import blackjack.domain.Dealer
import blackjack.domain.DealerRecord
import blackjack.domain.Deck
import blackjack.domain.Participant
import blackjack.domain.Player
import blackjack.domain.PlayerResult
import blackjack.domain.Rank
import blackjack.domain.Suit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackjackGameTest {
    @Test
    fun `참가자별 베팅 금액을 입력받아 플레이어를 생성한다`() {
        val input =
            StubInput(
                "pobi, jason",
                mapOf("pobi" to listOf(false), "jason" to listOf(false)),
                mapOf("pobi" to "10000", "jason" to "20000"),
            )
        val output = RecordingOutput()
        val deck = Deck.ordered(cards(Rank.TEN, Rank.SEVEN, Rank.FIVE, Rank.SIX, Rank.EIGHT, Rank.NINE))

        BlackjackGame(deck).start(input, output)

        assertThat(input.askedBettingNames).containsExactly("pobi", "jason")
        assertThat(output.initialBettingAmounts).containsExactlyEntriesOf(
            mapOf("pobi" to Amount.from("10000"), "jason" to Amount.from("20000")),
        )
    }

    @Test
    fun `최초 배분에는 딜러의 첫 카드와 플레이어의 카드 두 장을 출력한다`() {
        val input = StubInput("pobi, jason", mapOf("pobi" to listOf(false), "jason" to listOf(false)))
        val output = RecordingOutput()
        val deck = Deck.ordered(cards(Rank.TEN, Rank.SEVEN, Rank.FIVE, Rank.SIX, Rank.EIGHT, Rank.NINE))

        BlackjackGame(deck).start(input, output)

        assertThat(output.initialDealerCard).isEqualTo(card(Rank.TEN))
        assertThat(output.initialPlayers).containsExactly(
            record("pobi", 11, Rank.FIVE, Rank.SIX),
            record("jason", 17, Rank.EIGHT, Rank.NINE),
        )
        assertThat(output.finalDealer).isEqualTo(record("딜러", 17, Rank.TEN, Rank.SEVEN))
        assertThat(output.dealerHitCount).isZero()
    }

    @Test
    fun `플레이어가 y를 선택하면 카드를 지급하고 출력한 뒤 다시 묻는다`() {
        val input = StubInput("pobi", mapOf("pobi" to listOf(true, false)))
        val output = RecordingOutput()
        val deck = Deck.ordered(cards(Rank.TEN, Rank.SEVEN, Rank.FIVE, Rank.SIX, Rank.SEVEN))

        BlackjackGame(deck).start(input, output)

        assertThat(output.updated).containsExactly(record("pobi", 18, Rank.FIVE, Rank.SIX, Rank.SEVEN))
        assertThat(input.askedPlayers).containsExactly("pobi", "pobi")
    }

    @Test
    fun `플레이어가 21을 초과하면 추가 선택 없이 패배한다`() {
        val input = StubInput("pobi", mapOf("pobi" to listOf(true)))
        val output = RecordingOutput()
        val deck = Deck.ordered(cards(Rank.TEN, Rank.SEVEN, Rank.TEN, Rank.NINE, Rank.FIVE))

        BlackjackGame(deck).start(input, output)

        assertThat(input.askedPlayers).containsExactly("pobi")
        assertThat(output.playerResults).containsEntry("pobi", PlayerResult.LOSE)
    }

    @Test
    fun `딜러의 최초 합계가 16이면 카드 한 장을 받고 결과를 출력한다`() {
        val input = StubInput("pobi", mapOf("pobi" to listOf(false)))
        val output = RecordingOutput()
        val deck = Deck.ordered(cards(Rank.TEN, Rank.SIX, Rank.TEN, Rank.TEN, Rank.FIVE))

        BlackjackGame(deck).start(input, output)

        assertThat(output.dealerHitCount).isEqualTo(1)
        assertThat(output.finalDealer).isEqualTo(record("딜러", 21, Rank.TEN, Rank.SIX, Rank.FIVE))
        assertThat(output.playerResults).containsEntry("pobi", PlayerResult.LOSE)
        assertThat(output.dealerRecord?.wins()).isEqualTo(1)
    }

    @Test
    fun `딜러가 21을 초과하면 남아 있는 플레이어가 승리한다`() {
        val input = StubInput("pobi", mapOf("pobi" to listOf(false)))
        val output = RecordingOutput()
        val deck = Deck.ordered(cards(Rank.TEN, Rank.SIX, Rank.TEN, Rank.TEN, Rank.KING))

        BlackjackGame(deck).start(input, output)

        assertThat(output.finalDealer).isEqualTo(record("딜러", 26, Rank.TEN, Rank.SIX, Rank.KING))
        assertThat(output.playerResults).containsEntry("pobi", PlayerResult.WIN)
        assertThat(output.dealerRecord?.losses()).isEqualTo(1)
    }

    private fun cards(vararg ranks: Rank): List<Card> = ranks.map(::card)

    private fun card(rank: Rank): Card = Card(rank, Suit.SPADES)

    private fun record(
        name: String,
        score: Int,
        vararg ranks: Rank,
    ): HandRecord = HandRecord(name, cards(*ranks), score)
}

private class StubInput(
    private val names: String,
    decisions: Map<String, List<Boolean>>,
    bettingAmounts: Map<String, String> = names.split(",").associate { it.trim() to "10000" },
) : GameInput {
    private val decisions = decisions.mapValues { ArrayDeque(it.value) }
    private val bettingAmounts = bettingAmounts.mapValues { Amount.from(it.value) }
    val askedPlayers = mutableListOf<String>()
    val askedBettingNames = mutableListOf<String>()

    override fun readPlayerNames(): String = names

    override fun readBettingAmount(name: String): Amount {
        askedBettingNames.add(name)
        return bettingAmounts.getValue(name)
    }

    override fun wantsHit(player: Player): Boolean {
        askedPlayers.add(player.name)
        return decisions.getValue(player.name).removeFirst()
    }
}

private class RecordingOutput : GameOutput {
    var initialDealerCard: Card? = null
    val initialPlayers = mutableListOf<HandRecord>()
    val updated = mutableListOf<HandRecord>()
    var dealerHitCount = 0
    var finalDealer: HandRecord? = null
    val finalPlayers = mutableListOf<HandRecord>()
    var dealerRecord: DealerRecord? = null
    val playerResults = mutableMapOf<String, PlayerResult>()
    val initialBettingAmounts = linkedMapOf<String, Amount>()

    override fun showInitialHands(
        dealerCard: Card,
        players: List<Player>,
    ) {
        initialDealerCard = dealerCard
        initialPlayers.addAll(players.map(::record))
        initialBettingAmounts.putAll(players.associate { it.name to it.bettingAmount })
    }

    override fun showHand(player: Player) {
        updated.add(record(player))
    }

    override fun showDealerHit() {
        dealerHitCount++
    }

    override fun showFinalHands(
        dealer: Dealer,
        players: List<Player>,
    ) {
        finalDealer = record(dealer)
        finalPlayers.addAll(players.map(::record))
    }

    override fun showResults(
        dealerRecord: DealerRecord,
        playerResults: Map<Player, PlayerResult>,
    ) {
        this.dealerRecord = dealerRecord
        this.playerResults.putAll(playerResults.mapKeys { it.key.name })
    }

    private fun record(participant: Participant): HandRecord = HandRecord(participant.name, participant.cards(), participant.score())
}

private data class HandRecord(
    val name: String,
    val cards: List<Card>,
    val score: Int,
)
