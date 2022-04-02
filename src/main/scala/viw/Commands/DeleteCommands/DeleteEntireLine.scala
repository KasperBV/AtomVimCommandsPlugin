package viw.Commands.DeleteCommands

import viw.Commands.StateCommands.DeleteLine
import viw.EditorState
import viw.internals.State

object DeleteEntireLine extends DeleteCommand {

  override def process(state: State, eState: EditorState): (State, EditorState) = {
    val lines = state.contentLines
    (process(state), eState.copy(copyBuffer = lines(state.position.line)))
  }

  def process(state: State): State =
    DeleteLine.process(state.copy(position = state.position.copy(character = 0)))

}
