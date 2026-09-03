package blackjack.view

import blackjack.application.GameInput
import blackjack.domain.Amount
import blackjack.domain.Player

class ConsoleInputView : GameInput {
    override fun readPlayerNames(): String {
        println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)")
        return readln()
    }

    override fun readBettingAmount(name: String): Amount {
        println("${name}의 베팅 금액은?")
        return Amount.from(readln())
    }

    override fun wantsHit(player: Player): Boolean {
        println("${player.name}는 한 장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)")
        return readln().trim().lowercase() == HIT_COMMAND
    }

    private companion object {
        const val HIT_COMMAND = "y"
    }
}
