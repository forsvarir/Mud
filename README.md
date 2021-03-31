# Mud

Simple Mud to allow multiple connections to a shared text based dungeon experience.

The backend provides websocket interactions, based on STOMP. The built-in webpage offers a simple command/response
interface.

Currently, assumes a single player, so broadcasts messages to all connected websockets.

