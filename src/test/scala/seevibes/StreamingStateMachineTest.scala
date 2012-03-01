package seevibes

import org.scalatest.Assertions
import org.junit.Test
import java.util.concurrent.TimeUnit
import org.joda.time.{Duration, Instant}

class StreamingStateMachineTest extends Assertions {
    val KEY1 = Set("a")
    val KEY2 = Set("b")

    val oneMinute = new Duration(TimeUnit.MINUTES.toMillis(1))
    val twoMinutes = new Duration(TimeUnit.MINUTES.toMillis(2))
    val threeMinutes = new Duration(TimeUnit.MINUTES.toMillis(3))

    val now = new Instant()
    val oneMinuteAgo = now.minus(oneMinute)
    val twoMinutesAgo = now.minus(twoMinutes)
    val threeMinutesAgo = now.minus(threeMinutes)

    @Test
    def whenStreaming_andReceiveReset_thenShouldBePending() {
        expect(PendingKeywordsChange(Set.empty[String], now)) {
            Streaming(KEY1, now).resetKeywords(now)
        }
    }

    @Test
    def whenStreaming_andReceiveTick_thenShouldStillBeStreaming() {
        expect(Streaming(KEY2, now)) {
            Streaming(KEY2, now).tick(new Instant())
        }
    }

    @Test
    def whenStreaming_andReceiveAddKeyword_thenShouldBePending() {
        expect(PendingKeywordsChange(KEY1 + "b", now)) {
            Streaming(KEY1, oneMinuteAgo).addKeyword("b", now)
        }
    }

    @Test
    def whenPending_andReceiveReset_thenResets() {
        expect(PendingKeywordsChange(Set.empty[String], now)) {
            PendingKeywordsChange(KEY1, oneMinuteAgo).resetKeywords(now)
        }
    }

    @Test
    def whenPending_andReceiveAddKeyword_thenShouldAddKeyword() {
        expect(PendingKeywordsChange(KEY1 + "c", now)) {
            PendingKeywordsChange(KEY1, oneMinuteAgo).addKeyword("c", now)
        }
    }

    @Test
    def whenPending_andReceiveTick_andInsufficientTimeHasPassed_thenShouldStayPending() {
        expect(PendingKeywordsChange(KEY1, oneMinuteAgo)) {
            PendingKeywordsChange(KEY1, oneMinuteAgo).tick(now)
        }

        expect(PendingKeywordsChange(KEY1, twoMinutesAgo)) {
            PendingKeywordsChange(KEY1, twoMinutesAgo).tick(now)
        }

        expect(PendingKeywordsChange(KEY1, twoMinutesAgo)) {
            PendingKeywordsChange(KEY1, twoMinutesAgo).tick(twoMinutesAgo)
        }
    }

    @Test
    def whenPending_andReceiveTick_andSufficientTimeHasPassed_thenShouldBeStreaming() {
        expect(Streaming(KEY1, now)) {
            PendingKeywordsChange(KEY1, twoMinutesAgo.minus(TimeUnit.SECONDS.toMillis(1))).tick(now)
        }
    }
}
