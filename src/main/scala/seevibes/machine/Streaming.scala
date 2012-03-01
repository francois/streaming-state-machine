package seevibes.machine

import org.joda.time.ReadableInstant


case class Streaming(keywords: Set[String], lastChangedAt: ReadableInstant)
        extends State {
    def resetKeywords(now: ReadableInstant) =
        PendingKeywordsChange(Set.empty[String], now)

    def addKeyword(keyword: String, now: ReadableInstant) =
        PendingKeywordsChange(keywords + keyword, now)

    def tick(now: ReadableInstant) = this

    def isStreaming = true
}
