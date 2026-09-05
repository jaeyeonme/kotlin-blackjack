# Kotlin Blackjack

[NEXTSTEP 블랙잭 미션](https://github.com/next-step/kotlin-blackjack)을 Kotlin으로 구현한 콘솔 게임입니다. 플레이어와 딜러가 카드를 받아 Blackjack과 Bust 여부를 판정하고, 게임 결과에 따라 베팅 금액의 수익을 계산합니다.

## 주요 기능

- 참가자 이름과 각 플레이어의 베팅 금액을 입력받습니다.
- 표준 52장 덱에서 플레이어와 딜러에게 처음 두 장씩 카드를 지급합니다.
- 플레이어는 Bust 전까지 추가 카드를 선택할 수 있고, 딜러는 처음 받은 카드의 점수가 16 이하이면 한 장을 더 받습니다.
- Ace는 1 또는 11로 계산하며, 처음 두 장의 합이 21이면 Blackjack, 카드 합이 21을 초과하면 Bust로 판정합니다.
- 게임 결과에 따라 플레이어별 수익을 계산하고, 그 합의 부호를 바꾼 금액을 딜러 수익으로 출력합니다.

## 설계

### 덱 생성

실행에서는 `Deck.shuffled()`로 표준 52장의 순서를 섞고, 테스트에서는 `Deck.ordered(cards)`로 카드 순서를 지정합니다. 두 방식 모두 `draw()`를 사용해 카드를 꺼냅니다.

### 손패 계산

`Hand`는 현재 카드 목록에서 점수를 계산합니다. Ace는 11로 계산하되 카드 합이 21을 넘으면 필요한 만큼 1로 조정하며, 현재 손패를 기준으로 Blackjack과 Bust 여부를 판단합니다.

### 플레이어와 딜러

`Participant`는 카드 수령과 손패 조회 같은 공통 동작을 제공합니다. `Player`는 추가 카드를 받을 수 있는지, `Dealer`는 추가 카드가 필요한지 판단합니다. `BlackjackGame`은 이 조건과 플레이어 입력에 따라 게임을 진행합니다.

### 수익 계산

`Amount`는 베팅 금액과 수익을 표현하고, 수익 계산에 필요한 금액 연산을 제공합니다. `PlayerResult`는 게임 결과에 따른 수익을 계산하고, `ProfitReport`는 플레이어별 수익과 딜러 수익을 집계합니다.

## 테스트

전체 테스트와 코드 스타일은 다음 명령으로 확인합니다.

```shell
./gradlew clean test ktlintCheck
```

## 개발 기록

단계별 변경 내용은 Issue와 PR에 기록했습니다.

| 단계 | 내용 | 기록 |
| --- | --- | --- |
| Step 1 | Kotlin DSL 학습 테스트 | [Issue #1](https://github.com/jaeyeonme/kotlin-blackjack/issues/1) · [PR #2](https://github.com/jaeyeonme/kotlin-blackjack/pull/2) |
| Step 2 | 덱과 손패 | [Issue #3](https://github.com/jaeyeonme/kotlin-blackjack/issues/3) · [PR #4](https://github.com/jaeyeonme/kotlin-blackjack/pull/4) |
| Step 3 | 플레이어·딜러와 승패 판정 | [Issue #5](https://github.com/jaeyeonme/kotlin-blackjack/issues/5) · [PR #6](https://github.com/jaeyeonme/kotlin-blackjack/pull/6) |
| Step 4 | 베팅과 수익 계산 | [Issue #7](https://github.com/jaeyeonme/kotlin-blackjack/issues/7) · [PR #8](https://github.com/jaeyeonme/kotlin-blackjack/pull/8) |

## 실행 방법

JDK 25 환경에서 IDE로 `src/main/kotlin/blackjack/Main.kt`의 `main` 함수를 실행합니다.

참가자 이름과 각 플레이어의 베팅 금액을 입력해 게임을 진행합니다. 덱은 게임을 시작할 때 무작위로 섞입니다.
