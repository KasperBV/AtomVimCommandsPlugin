package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object NextWord extends MoveCommand {


  override def getNewPosition(state: State): State.Position = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    var character = line.length - 1
    var i = state.position.character
    var notFound = true
    while ((i != line.length - 1) && notFound){
      if (line(i).equals(' ')) {
        while (i != line.length - 1 && line(i).equals(' ')) {
          i += 1
        }
        notFound = false
        character = i
      }
      i += 1
    }
    val position = if(character != line.length - 1)
      State.Position(state.position.line, character) else
      if(state.position.line != lines.length - 1)
        State.Position(state.position.line + 1, 0) else
        state.position
    position
  }
}
