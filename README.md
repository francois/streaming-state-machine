Twitter Streaming State Machine
-------------------------------

Twitter requires users of the Streaming API to reconnect at most every few minutes. This calls for a state machine to control the actual reconnection. The code in this repository refers to a blog post I made which is available at [Scala Case Classes and State Machines Using Akka Actors][1]

  [1]: http://blog.teksol.info/2012/02/29/scala-case-classes-and-state-machines-using-akka-actors
