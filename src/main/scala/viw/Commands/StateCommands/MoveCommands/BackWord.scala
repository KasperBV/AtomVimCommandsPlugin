package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object BackWord extends MoveCommand {


  override def getNewPosition(state: State): State.Position = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    val i = state.position.character
    if (!(i == 0) && line(i-1).equals(' ')) {
      previousWord(state: State).position
    }
    else{
      startOfWord(state).position
    }
  }

  private def previousWord(state: State): State = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    var i = state.position.character
    var notFound = true
    while ((i != -1) && notFound){
      if (line(i).equals(' ')) {
        while (line(i).equals(' ')) {
          i -= 1
        }
        notFound = false
      }
      i -= 1
    }
    if ((state.position.line == 0) && (i == -1)) state
    else {
      val position = if (i != -1)
        State.Position(state.position.line, i) else
        State.Position(state.position.line - 1, 0)
      val rState = new State(state.content, position, state.selection, true)
      startOfWord(rState)
    }
  }

  private def startOfWord(state: State): State ={
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    var i = state.position.character
    if (i == 0){
      if (state.position.line == 0) state
      else {
        val position = State.Position(state.position.line - 1, lines(state.position.line-1).length-1)
        process(new State(state.content, position, state.selection, true ))
      }
    }
    else{
      var notFound = true
      while ((notFound) && (i != 0)){
        i -= 1
        if (line(i).equals(' ')){
          notFound = false
          i += 1
        }
      }
      val rState = new State(state.content, State.Position(state.position.line,i), state.selection, true)
      rState
    }
  }
}
