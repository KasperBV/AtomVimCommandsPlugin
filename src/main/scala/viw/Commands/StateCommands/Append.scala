package viw.Commands.StateCommands

import viw.internals.State

object Append extends StateCommand {

  override def process(state: State): State = {
    val position = State.Position(state.position.line, state.position.character + 1)
    val rState = new State(state.content, position, state.selection, false)
    rState
  }
}
