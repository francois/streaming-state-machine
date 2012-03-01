Twitter Streaming State Machine
-------------------------------

Twitter requires users of the Streaming API to reconnect at most every few minutes. This calls for a state machine to control the actual reconnection. The code in this repository refers to a blog post I made which is available at [Scala Case Classes and State Machines Using Akka Actors][1]

  [1]: http://blog.teksol.info/2012/02/29/scala-case-classes-and-state-machines-using-akka-actors

Running the code
================

    $ mvn compile exec:java -Dexec.mainClass=seevibes.Main

The code in this repository is slightly different from the blog post: the time scale was changed such that developers don't wait minutes before seeing a change. Since this code doesn't connect to Twitter, it doesn't really matter how long we wait before we actually change the keywords.
