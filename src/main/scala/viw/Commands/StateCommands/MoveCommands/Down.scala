package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object Down extends MoveCommand {


  override def getNewPosition(state: State): State.Position = {
    val lines = State.properSplit(state.content)
    val position = if (state.position.line != lines.length - 1)
      State.Position(state.position.line + 1, state.position.character) else
      state.position
    position
  }
}
