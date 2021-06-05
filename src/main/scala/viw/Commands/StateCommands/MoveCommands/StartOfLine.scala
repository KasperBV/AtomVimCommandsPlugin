package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object StartOfLine extends MoveCommand {

  override def getNewPosition(state: State): State.Position = State.Position(state.position.line,0)

}
