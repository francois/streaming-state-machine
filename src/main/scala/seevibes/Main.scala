package seevibes

import actors.{ResetKeywords, AddKeyword, KeywordsStateMachine, Tick}
import akka.actor.{Scheduler, Actor}
import java.util.concurrent.{CountDownLatch, TimeUnit}

object Main {
    def main(args: Array[String]) {
        val keywordsStateMachine = Actor.actorOf[KeywordsStateMachine].start()

        // Check keywords every minute
        Scheduler.schedule(keywordsStateMachine, Tick, 0, 5, TimeUnit.SECONDS)

        // Schedule a few messages so you see what's going on
        Scheduler.scheduleOnce(keywordsStateMachine, AddKeyword("ibm"), 3, TimeUnit.SECONDS)
        Scheduler.scheduleOnce(keywordsStateMachine, AddKeyword("microsoft"), 5, TimeUnit.SECONDS)
        Scheduler.scheduleOnce(keywordsStateMachine, ResetKeywords, 13, TimeUnit.SECONDS)
        Scheduler.scheduleOnce(keywordsStateMachine, AddKeyword("apple"), 20, TimeUnit.SECONDS)

        // Keep the VM alive until we're really done
        new CountDownLatch(1).await()
    }
}
