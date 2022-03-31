package viw.Commands.DeleteCommands

import viw.Commands.StateCommands.DeleteLine
import viw.EditorState
import viw.internals.State

object DeleteEntireLine extends DeleteCommand {

  override def process(state: State, eState: EditorState): (State, EditorState) = {
    val lines = state.contentLines
    (process(state), EditorState(eState.lastCommand, eState.viewMode, eState.bindings, lines(state.position.line)))
  }

  def process(state: State): State =
    DeleteLine.process(new State(state.content, State.Position(state.position.line, 0), state.selection, true))

}
