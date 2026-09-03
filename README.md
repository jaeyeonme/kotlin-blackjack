# kotlin-blackjack

## 구현 현황

| 단계 | 구현 상태 | 관련 문서 |
| --- | --- | --- |
| 1단계 - Kotlin DSL 학습 테스트 | 완료 | [Issue #1](https://github.com/jaeyeonme/kotlin-blackjack/issues/1) · [PR #2](https://github.com/jaeyeonme/kotlin-blackjack/pull/2) |
| 2단계 - 플레이어 중심 블랙잭 | 완료 | [Issue #3](https://github.com/jaeyeonme/kotlin-blackjack/issues/3) · [PR #4](https://github.com/jaeyeonme/kotlin-blackjack/pull/4) |
| 3단계 - 딜러와 승패 판정 | 완료 | [Issue #5](https://github.com/jaeyeonme/kotlin-blackjack/issues/5) · [PR #6](https://github.com/jaeyeonme/kotlin-blackjack/pull/6) |
| 4단계 - 블랙잭(베팅) | 완료 | [Issue #7](https://github.com/jaeyeonme/kotlin-blackjack/issues/7) · [PR #8](https://github.com/jaeyeonme/kotlin-blackjack/pull/8) |

## 4단계 기능 목록

- 플레이어별 베팅 금액을 입력받아 도메인 값으로 관리한다.
- 최초 두 장의 카드로 블랙잭 여부를 판단하고 Bust 여부를 제공한다.
- 일반 승패와 블랙잭 여부에 따라 플레이어별 수익을 계산한다.
- 플레이어 수익의 합을 반대로 집계해 딜러 수익을 계산한다.
- 게임 종료 후 딜러와 플레이어별 최종 수익을 출력한다.
