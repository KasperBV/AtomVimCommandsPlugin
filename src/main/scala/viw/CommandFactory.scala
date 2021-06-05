package viw

import viw.Commands.DeleteCommands._
import viw.Commands.StateCommands.MoveCommands._
import viw.Commands.StateCommands._
import viw.Commands.EditorCommands._
import viw.Commands._


object CommandFactory {


  // Returns the command associated with the given input buffer and lastCommand.
  // If the inputBuffer is incomplete (not all keys for the command given yet) the result will be None.
  // If the inputBuffer has no command associated the result will be the NotBound Command.
  def produceCommand(inputBuffer: Vector[String], eState: EditorState): Option[Command] = {

    val editorState = eState.lastCommand match {
      case Some(c) => eState
      case None => new EditorState(Some(NotBound), eState.viewMode, eState.bindings, eState.copyBuffer)
    }

    if (eState.viewMode) produceSelectionCommand(inputBuffer, editorState)

    else if (inputBuffer.length == 1) {
      inputBuffer(0) match {
        case "h" => Some(Left)
        case "j" => Some(Down)
        case "k" => Some(Up)
        case "l" => Some(Right)
        case " " => Some(Start)
        case "i" => Some(Exit)
        case "w" => Some(NextWord)
        case "b" => Some(BackWord)
        case "e" => Some(EndOfWord)
        case "$" => Some(EndOfLine)
        case "0" => Some(StartOfLine)
        case "%" => Some(MatchBracket)
        case "x" => Some(Delete)
        case "X" => Some(DeleteBackwards)
        case "D" => Some(DeleteLine)
        case "J" => Some(JoinLine)
        case "a" => Some(Append)
        case "o" => Some(Open)
        case "s" => Some(Substitute)
        case "G" => Some(Go)
        case "I" => Some(InsertInLine)
        case "A" => Some(InsertAfterLine)
        case "C" => Some(ChangeLine)
        case "p" => Some(new Paste(eState.copyBuffer))
        case "P" =>   Some(new PasteBehind(eState.copyBuffer))
        case "." => editorState.lastCommand
        case _ => eState.bindings get inputBuffer(0)(0)
      }
    }

    else {
      inputBuffer(0) match {
        case "d" => produceDeleteCommand(inputBuffer.tail, editorState)
        case "c" => produceChangeCommand(inputBuffer.tail, editorState)
        case "f" => Some(new Find(inputBuffer(1)))
        case ">" => Some(produceIndentCommand(inputBuffer(1)))
        case "<" => Some(produceDedentCommand(inputBuffer(1)))
        case "B" => Some(new Bind(inputBuffer(1)(0)))
        case "z" => produceChainCommand(inputBuffer.tail, editorState)
        case "y" => produceYankCommand(inputBuffer.tail, editorState)
        case _ => if (inputBuffer(0)(0).isDigit) produceCountCommand(inputBuffer.tail, inputBuffer(0), editorState) else  Some(NotBound)
      }
    }

  }

  private def produceDeleteCommand(nextInput: Vector[String], editorState: EditorState): Option[Command] = {
    nextInput(0) match {
      case "d" => Some(DeleteEntireLine)
      case _ => val nextCommand = produceCommand(nextInput, editorState)
        nextCommand match {
          case None => None
          case Some(i) => i match {
            case m: MoveCommand => Some(new DeleteMovement(m))
            case _ => Some(NotBound)
          }
        }
    }
  }

  private def produceChangeCommand(nextInput: Vector[String], editorState: EditorState): Option[Command] = {
    nextInput(0) match {
      case "c" => Some(DeleteLine)
      case _ => produceCommand(nextInput, editorState) match {
        case None => None
        case Some(i) => i match {
          case d: DeleteCommand => Some(new ChangeMovement(d))
          case _ => Some(NotBound)
        }
      }
    }
  }

  private def produceCountCommand(nextInput: Vector[String], counter: String, editorState: EditorState): Option[Command] = {
    var fCounter: String = counter
    for (c <- nextInput) {
      if (c(0).isDigit) fCounter += c else {
        val nextCommand = produceCommand(nextInput.slice(nextInput.indexOf(c), nextInput.length), editorState)
        nextCommand match {
          case None => return None
          case Some(i) => i match {
            case m: MoveCommand => return Some(new Count(m, fCounter.toInt))
            case _ => return Some(NotBound)
          }
        }
      }
    }
    None
  }

  private def produceIndentCommand(nextInput: String): Command = {
    nextInput match {
      case ">" => IndentLine
      case "l" => IndentLine
      case "h" => IndentLine
      case "j" => IndentTwoLines
      case _ => NotBound
    }
  }

  private def produceDedentCommand(nextInput: String): Command = {
    nextInput match {
      case "<" => DedentLine
      case "l" => DedentLine
      case "h" => DedentLine
      case "j" => DedentTwoLines
      case _ => NotBound
    }
  }

  private def produceYankCommand(nextInput: Vector[String], editorState: EditorState): Option[Command] = {
    produceCommand(nextInput, editorState) match {
          case None => None
          case Some(i) => i match {
            case m: MoveCommand => Some(new YankMovement(m))
            case _ => Some(NotBound)
          }
        }
    }

  private def produceChainCommand(nextInput: Vector[String], editorState: EditorState): Option[Command] = {
    val nextCommand = produceCommand(nextInput, editorState)
    nextCommand match {
      case None => None
      case Some(i) => Some(new ChainCommand(i, editorState.lastCommand.get))
    }
  }

  private def produceSelectionCommand(inputBuffer: Vector[String], eState: EditorState): Option[Command] = {
    produceCommand(inputBuffer, EditorState(eState.lastCommand, false, eState.bindings, eState.copyBuffer)) match {
      case None => None
      case Some(c) => c match {
        case m: MoveCommand => Some(new Selection(m))
        case _ => Some(NotBound)
      }
    }
  }

}
