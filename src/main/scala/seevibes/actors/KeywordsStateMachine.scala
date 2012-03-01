package seevibes.actors

import akka.actor.Actor
import org.joda.time.Instant
import seevibes.machine.{State, Streaming}

class KeywordsStateMachine extends Actor {
    private var state: State = Streaming(Set.empty[String], new Instant())
    private var currentKeywords = state.keywords

    println("start => " + state)

    def receive = {
        case Tick =>
            state = state.tick(new Instant())
            if (state.isStreaming && state.keywords != currentKeywords) {
                currentKeywords = state.keywords
            }

            println("tick => " + state)

        case ResetKeywords =>
            state = state.resetKeywords(new Instant())
            println("reset => " + state)

        case AddKeyword(keyword) =>
            state = state.addKeyword(keyword, new Instant())
            println("add(" + keyword + ") => " + state)
    }
}
