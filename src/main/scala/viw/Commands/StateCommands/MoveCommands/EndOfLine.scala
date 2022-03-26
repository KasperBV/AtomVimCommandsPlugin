package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object EndOfLine extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    State.Position(state.position.line,line.length - 1)
  }

}
