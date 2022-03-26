package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object Left extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    if (state.isPositionAtStartOfLine()) state.position
    else state.position.copy(character = state.position.character - 1)
  }

}
