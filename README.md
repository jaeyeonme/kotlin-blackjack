# kotlin-blackjack

## 구현 현황

| 단계 | 구현 상태 | 관련 문서 |
| --- | --- | --- |
| 1단계 - Kotlin DSL 학습 테스트 | 완료 | [Issue #1](https://github.com/jaeyeonme/kotlin-blackjack/issues/1) · [PR #2](https://github.com/jaeyeonme/kotlin-blackjack/pull/2) |
| 2단계 - 플레이어 중심 블랙잭 | 완료 | [Issue #3](https://github.com/jaeyeonme/kotlin-blackjack/issues/3) · [PR #4](https://github.com/jaeyeonme/kotlin-blackjack/pull/4) |
| 3단계 - 딜러와 승패 판정 | 구현 완료 · 리뷰 중 | [Issue #5](https://github.com/jaeyeonme/kotlin-blackjack/issues/5) · [PR #6](https://github.com/jaeyeonme/kotlin-blackjack/pull/6) |

## 3단계 기능 목록

- 플레이어와 딜러가 카드 수령, 손패 조회와 점수 계산을 공통으로 사용한다.
- 딜러는 최초 두 장의 합계가 16 이하이면 카드 한 장을 추가로 받고, 17 이상이면 받지 않는다.
- 최초 배분 결과에는 딜러의 첫 번째 카드와 플레이어의 카드 두 장을 출력한다.
- 플레이어 관점에서 승·무·패를 판정하고 딜러 전적을 집계한다.
- 딜러와 플레이어의 최종 손패·점수를 출력한 뒤 승패 결과를 출력한다.
