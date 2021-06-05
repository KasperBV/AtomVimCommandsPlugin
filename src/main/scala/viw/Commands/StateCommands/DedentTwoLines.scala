package viw.Commands.StateCommands

import viw.internals.State

object DedentTwoLines extends StateCommand {

  override def process(state: State): State = {
    val lines = State.properSplit(state.content)
    if (state.position.line < lines.length -1) {
      val line = lines(state.position.line)
      val line2 = lines(state.position.line + 1)
      var newLines = lines
      if (line(0).equals(' ')) {
        var newLine = line.substring(1)
        if (newLine(0).equals(' ')) newLine = newLine.substring(1)
        newLines = newLines.updated(state.position.line, newLine)
      }
      if (line2(0).equals(' ')) {
        var newLine2 = line2.substring(1)
        if (newLine2(0).equals(' ')) newLine2 = newLine2.substring(1)
        newLines = newLines.updated(state.position.line+1, newLine2)
      }
      val content = newLines.mkString("\n")
      new State(content, State.Position(state.position.line, state.position.character-2), state.selection, true)
    }
    else {
      DedentLine.process(state)
    }
  }
}
