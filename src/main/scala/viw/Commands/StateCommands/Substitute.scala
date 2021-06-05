package viw.Commands.StateCommands


import viw.internals.State

object Substitute extends StateCommand {

  override def process(state: State): State = Exit.process(Delete.process(state))

}
