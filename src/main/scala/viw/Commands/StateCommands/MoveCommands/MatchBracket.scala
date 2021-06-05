package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

object MatchBracket extends MoveCommand {

  private val forwardBrackets = Map('{' -> '}' ,  '[' -> ']',  '(' -> ')')
  private val backwardBrackets = Map('}' -> '{'  , ']'-> '[' , ')' -> '(')

  override def getNewPosition(state: State): State.Position = {
    val lines = State.properSplit(state.content)
    val line = lines(state.position.line)
    val character = line(state.position.character)
    forwardBrackets.get(character) match{
      case Some(c) =>  matchBracketForward(state, character.toString, c.toString)
      case None => backwardBrackets.get(character) match{
        case Some(c) => matchBracketBackward(state, c.toString, character.toString)
        case None => firstBracketLine(line) match {
          case Some(i) => State.Position(state.position.line, i)
          case None => state.position
        }
      }
    }
  }

  // Returns the index of the first bracket in the line or None if there is no bracket in the line
  private def firstBracketLine(line: String): Option[Int] ={
    val brackets = forwardBrackets ++ backwardBrackets
    val indices = brackets.keys.map(bracket => line.indexOf(bracket)).filter(i => i != -1)
    if (!indices.isEmpty) Some(indices.min) else None
  }

  private def matchBracketForward(state: State, openBracket: String, closeBracket: String): State.Position ={
    val lines = State.properSplit(state.content)
    var character = state.position.character + 1
    var foundCharacter = character
    var ctr = 1
    var i = state.position.line
    while (ctr !=  0 && i < lines.length){
      while (ctr !=  0 && character < lines(i).length){
        if (lines(i)(character).toString.equals(closeBracket)) ctr += -1
        else if (lines(i)(character).toString.equals(openBracket)) ctr += 1
        character += 1
      }
      foundCharacter = character-1
      character = 0
      i += 1
    }
    if (ctr == 0){
      State.Position(i-1, foundCharacter)
    }
    else state.position
  }

  private def matchBracketBackward(state: State, openBracket: String, closeBracket: String): State.Position ={
    val lines = State.properSplit(state.content)
    var character = state.position.character - 1
    var foundCharacter = character
    var ctr = 1
    var i = state.position.line
    while (ctr !=  0 && i > -1){
      while (ctr !=  0 && character > -1){
        if (lines(i)(character).toString.equals(openBracket)) ctr += -1
        else if (lines(i)(character).toString.equals(closeBracket)) ctr += 1
        character -= 1
      }
      foundCharacter = character + 1
      i -= 1
      if (i > -1)character = lines(i).length - 1
    }
    if (ctr == 0){
      State.Position(i + 1, foundCharacter)
    }
    else state.position
  }

}
