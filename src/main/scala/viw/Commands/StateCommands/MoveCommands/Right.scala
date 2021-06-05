package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object Right extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    if (state.position.character != line.length - 1) State.Position(state.position.line, state.position.character + 1) else
      state.position
  }
}
