package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

class Find(val key: String) extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)

    for (i <- 0 to line.length-1){
      if ((line(i).toString.equals(key)) && ((i == 0) ||(line(i-1).equals(' ')))) {
        return State.Position(state.position.line, i)
      }
    }
    State.Position(state.position.line, state.position.character)
  }

  override def equals(x: Any): Boolean ={
    x match {
      case f: Find => key == f.key
      case _ => false
    }
  }

}
