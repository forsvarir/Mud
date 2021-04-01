# Roadmap (aka task list)

### TO DO (priority order):

* Need to have a player representation
* Add the concept of a 'Room' to limit visibility.

### DONE

* Minimal project with active web socket connection between clients and services.
* Tests run, with spring tests running as integration (mvn verify)
  and normal tests running earlier (mvn test).
* Use message classes to send/receive server side
* Encapsulate processing, away from controller
* Provide mechanism for sending message to individual connection --> STOMP uses the Principal to allow to user message
  routing However, all tabs / sessions connected as that user get all messages. Passing the STOMP sessionId back in
  response messages allows the session to filter messages so that it only processes those for its session.

### Puzzles

~~* Should `MessageSender` only deal with text and have responsibility for constructing messages from the string?~~