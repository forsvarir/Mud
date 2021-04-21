# Roadmap (aka task list)

### TO DO (priority order):

* Develop linkage between rooms to allow a structure to be formed
* Develop directional commands to allow rooms to be explored.
* Provide storage for rooms so that they can be loaded rather than hard-coded.
* Provide storage for players so that they can be validated at logon
* Develop player attributes that can be adjusted during player creation (class perhaps)
* User player where possible, rather than principal/session
* Session disconnections / timeout actions

### DONE

* Minimal project with active web socket connection between clients and services.
* Tests run, with spring tests running as integration (mvn verify)
  and normal tests running earlier (mvn test).
* Use message classes to send/receive server side
* Encapsulate processing, away from controller
* Provide mechanism for sending message to individual connection --> STOMP uses the Principal to allow to user message
  routing However, all tabs / sessions connected as that user get all messages. Passing the STOMP sessionId back in
  response messages allows the session to filter messages so that it only processes those for its session.
* Need to have a player representation
* Add the concept of a 'Room' to limit visibility.
* Improve "tell" command processing, including error conditions
* Develop command processor - possibly tokeniser, command classes?

### Puzzles

* Should MessageSender know how to send to room, or should individual commands / something else?
* Should commands return messagesActions to be executed/sent rather than sending them itself?
  ~~* Should `MessageSender` only deal with text and have responsibility for constructing messages from the string?~~