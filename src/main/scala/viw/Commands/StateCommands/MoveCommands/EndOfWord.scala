package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object EndOfWord extends MoveCommand {

  override def getNewPosition(state: State): State.Position = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    if (line(state.position.character).equals(' ')) return state.position
    for (i <- state.position.character to line.length - 1){
      if (line(i).equals(' ')) {
        return State.Position(state.position.line, i - 1)
      }
    }
    State.Position(state.position.line, line.length - 1)
  }
}
