package seevibes

import org.joda.time.ReadableInstant
import java.util.concurrent.TimeUnit

case class PendingKeywordsChange(keywords: Set[String], lastChangedAt: ReadableInstant)
        extends State {
    def resetKeywords(now: ReadableInstant) =
        PendingKeywordsChange(Set.empty[String], now)

    def addKeyword(keyword: String, now: ReadableInstant) =
        PendingKeywordsChange(keywords + keyword, now)

    def nextChangeAt = lastChangedAt.toInstant.plus(TimeUnit.MINUTES.toMillis(2))

    def tick(now: ReadableInstant) =
        if (now.isAfter(nextChangeAt))
            Streaming(keywords, now)
        else
            this
}
