package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object Up extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    if (state.position.line != 0) State.Position(state.position.line - 1, state.position.character) else
      state.position
  }
}
