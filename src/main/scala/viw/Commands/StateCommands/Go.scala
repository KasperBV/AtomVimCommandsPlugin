package viw.Commands.StateCommands

import viw.internals.State

object Go extends StateCommand {

  override def process(state: State): State = {
    val lines = State.properSplit(state.content)
    val position = State.Position(lines.length - 1, 0)
    new State(state.content, position, state.selection, false)
  }
}
