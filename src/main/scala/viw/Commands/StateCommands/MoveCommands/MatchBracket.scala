package viw.Commands.StateCommands.MoveCommands

import viw.internals.State

import scala.annotation.tailrec

object MatchBracket extends MoveCommand {

  private val forwardBrackets = Map('{' -> '}' ,  '[' -> ']',  '(' -> ')')
  private val backwardBrackets = Map('}' -> '{'  , ']'-> '[' , ')' -> '(')

  override def getNewPosition(state: State): State.Position = {
    val line = state.contentLines(state.position.line)
    val character = line(state.position.character)
    forwardBrackets.get(character) match{
      case Some(c) =>  matchBracketForward(state, state.position, 1, character, c)
      case None => backwardBrackets.get(character) match{
        case Some(c) => matchBracketBackward(state, state.position, 1, c, character)
        case None => firstBracketLine(line) match {
          case Some(i) => State.Position(state.position.line, i)
          case None => state.position
        }
      }
    }
  }

  @tailrec
  private def matchBracketForward(state: State, position: State.Position, bracketDepth: Int,
                                  openBracket: Char, closeBracket: Char): State.Position ={
    State.getPositionAfter(state.content, position) match {
      case Some(p) => state.contentLines(p.line)(p.character) match {
                      case `closeBracket` => if (bracketDepth == 1) p
                                           else matchBracketForward(state, p, bracketDepth - 1, openBracket, closeBracket)
                      case `openBracket` =>  matchBracketForward(state, p, bracketDepth + 1, openBracket, closeBracket)
                      case _ => matchBracketForward(state, p, bracketDepth, openBracket, closeBracket)
      }
      case None => state.position
    }
  }

  private def matchBracketBackward(state: State, position: State.Position, bracketDepth: Int,
                                   openBracket: Char, closeBracket: Char): State.Position ={
    State.getPositionBefore(state.content, position) match {
      case Some(p) => state.contentLines(p.line)(p.character) match {
        case `openBracket` => if (bracketDepth == 1) p
                              else matchBracketBackward(state, p, bracketDepth - 1, openBracket, closeBracket)
        case `closeBracket` =>  matchBracketBackward(state, p, bracketDepth + 1, openBracket, closeBracket)
        case _ => matchBracketBackward(state, p, bracketDepth, openBracket, closeBracket)
      }
      case None => state.position
    }
  }

  private def firstBracketLine(line: String): Option[Int] ={
    val brackets = forwardBrackets ++ backwardBrackets
    val indices = brackets.keys.map(bracket => line.indexOf(bracket)).filter(i => i != -1)
    if (!indices.isEmpty) Some(indices.min) else None
  }

}
