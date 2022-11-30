# sleeping-queens
The main page of the task http://www.dcs.fmph.uniba.sk/~lukotka/PTS1/2022/du1.htm

The project structure differs from the basic outline in some ways:
- the Player class doesn't have many responsibilies on its own (translated: does nothing)
- EvaluateNumberedCards, EvaluateAttack and MoveQueen have been replaced with a more OOP approach: every type of action is represented by a class implementing the TurnAction interface. This way, the player tells the game their intentions using a neatly-wrapped object, which performs everything needed to complete the turn
- Position and the other three classes connected to it have been omitted in favour of the TurnAction hierarchy - they contain all the information necessary for the action, including the positions of the played cards, the targeted player and the targeted queen
- QueenCollection has been made into a concrete class, since there is no functional difference between the planned SleepingQueensCollection and AwokenQueensCollection

An array of tests has been incluced in the package.
Batteries not included.
May contain small parts.
