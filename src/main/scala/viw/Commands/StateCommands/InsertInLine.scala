package viw.Commands.StateCommands

import viw.Commands.StateCommands.MoveCommands.StartOfLine
import viw.internals.State

object InsertInLine extends StateCommand {

  override def process(state: State): State = Exit.process(StartOfLine.process(state))

}
