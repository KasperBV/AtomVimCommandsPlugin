package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object EndOfLine extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    val line = state.contentLines(state.position.line)
    State.Position(state.position.line,line.length - 1)
  }

}
