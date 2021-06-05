package viw.Commands.StateCommands


import viw.Commands.StateCommands.MoveCommands.EndOfLine
import viw.internals.State

object InsertAfterLine extends StateCommand {

  override def process(state: State): State = Append.process(EndOfLine.process(state))

}
