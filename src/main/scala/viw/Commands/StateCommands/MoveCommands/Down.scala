package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object Down extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    if (state.isPositionOnLastLine()) state.position
    else {
      val lineLength = state.contentLines(state.position.line + 1).length
      State.Position(state.position.line + 1, Math.min(lineLength, state.position.character))
    }
  }

}
