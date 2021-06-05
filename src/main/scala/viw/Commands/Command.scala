package viw.Commands

import viw.EditorState
import viw.internals.State

trait Command {

  def process(state: State, eState: EditorState): (State, EditorState)

}















