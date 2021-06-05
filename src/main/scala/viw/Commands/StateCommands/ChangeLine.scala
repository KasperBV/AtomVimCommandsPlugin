package viw.Commands.StateCommands

import viw.internals.State

object ChangeLine extends StateCommand {

  override def process(state: State): State = {
    val rState = Append.process(DeleteLine.process(state))
    rState
  }
}
