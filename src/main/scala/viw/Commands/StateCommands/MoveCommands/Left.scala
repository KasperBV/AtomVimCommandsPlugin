package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object Left extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    if (state.position.character != 0) State.Position(state.position.line, state.position.character - 1) else
      state.position
  }
}
