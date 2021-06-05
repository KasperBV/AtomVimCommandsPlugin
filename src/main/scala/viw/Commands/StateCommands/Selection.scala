package viw.Commands.StateCommands


import viw.Commands.StateCommands.MoveCommands.MoveCommand
import viw.internals.State

class Selection(m: MoveCommand) extends StateCommand{

  val moveCommand: MoveCommand = m

  override def process(state: State): State = {
    val rState = moveCommand.process(state)
    val selection: (State.Position, State.Position) = state.selection match{
      case None => if (rState.position > state.position) (state.position, rState.position) else (rState.position, state.position)
      case Some(s) =>
         (List(rState.position, state.position, s._1).reduceLeft( (x,y) => if (x < y)  x else y),
          List(rState.position, state.position, s._1).reduceLeft( (x,y) => if (x > y)  x else y))
    }
    new State(state.content, state.position, Some(selection), true)
  }

  override def equals(x: Any): Boolean = {
    x match {
      case s:Selection => moveCommand == s.moveCommand
      case _ => false
    }
  }

}