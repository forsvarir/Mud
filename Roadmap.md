# Roadmap (aka task list)

### TO DO (priority order):

* Provide mechanism for sending message to individual connection --> Requires connection identifiers
* Add the concept of a 'Room' to limit visibility.

### DONE

* Minimal project with active web socket connection between clients and services.
* Tests run, with spring tests running as integration (mvn verify)
  and normal tests running earlier (mvn test).
* Use message classes to send/receive server side
* Encapsulate processing, away from controller

### Puzzles

~~* Should `MessageSender` only deal with text and have responsibility for constructing messages from the string?~~