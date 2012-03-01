package seevibes

import org.joda.time.ReadableInstant

trait State {
    def resetKeywords(now: ReadableInstant): State
    def addKeyword(keyword: String, now: ReadableInstant): State
    def tick(now: ReadableInstant): State
    def keywords: Set[String]
}
