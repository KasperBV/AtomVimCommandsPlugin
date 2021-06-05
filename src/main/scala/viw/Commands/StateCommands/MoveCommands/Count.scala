package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

class Count(val movement: MoveCommand, val counter: Int) extends MoveCommand {


  override def getNewPosition(state: State): State.Position = {
    var rState = state
    for (i <- 1 to counter){
      rState = movement.process(rState)
    }
    rState.position
  }

  override def equals(x: Any): Boolean ={
    x match {
      case c: Count => (movement == c.movement) && (counter == c.counter)
      case _ => false
    }
  }

}