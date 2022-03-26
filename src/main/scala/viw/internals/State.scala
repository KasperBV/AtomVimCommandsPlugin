package viw.internals

/**
  * State that represents the current contents of the text editor.
  */
trait StateFields {

  /**
    * @return all text in the buffer
    */
  def content: String

  /**
    * @return position of the cursor
    */
  def position: State.Position

  /**
    * @return region that is selected in content, start + end position
    */
  def selection: Option[(State.Position, State.Position)]

  /**
    * @return viw-mode toggle
    */
  def mode: Boolean

}

case class State(content: String,
                 position: State.Position,
                 selection: Option[(State.Position, State.Position)],
                 mode: Boolean)
    extends StateFields {
  val contentLines = State.properSplit(content)

  def isPositionAtEndOfLine(): Boolean = {
    State.isEndOfLine(content, position)
  }

  def isPositionAtStartOfLine(): Boolean = {
    State.isStartOfLine(content, position)
  }

  def isPositionAtEndOfText(): Boolean = {
    State.isEndOfText(content, position)
  }

  def isPositionOnLastLine(): Boolean = {
    State.isOnLastLine(content, position)
  }

  def isPositionOnFirstLine(): Boolean = {
    State.isOnFirstLine(content, position)
  }
}

object State {
  def properSplit(str: String): Vector[String] =
    (str: String).split("\n", -1).toVector

  def fromCursoredText(str: String, mode: Boolean = true): State = {
    val lines = properSplit(str)
    val characterPositions = lines.map { line =>
      "#.*#".r.findFirstMatchIn(line).map { m =>
        m.start
      }
    }
    val positions = characterPositions.zipWithIndex.collect {
      case (Some(c), line) => Position(line, c)
    }

    val content =
      "#.*#".r.replaceAllIn(str.replace("##", ""), m => m.matched(1).toString)
    val position = positions.headOption.getOrElse(Position(0, 0))

    State(content, position, None, mode)
  }

  def isValidPosition(content: String, position: Position): Boolean = {
    val lines = properSplit(content);
    position.line < lines.length && position.character < lines(position.line).length
  }

  def isEndOfLine(content: String, position: Position): Boolean = {
    val lines = properSplit(content);
    position.line < lines.length && position.character == lines(position.line).length - 1
  }

  def isStartOfLine(content: String, position: Position): Boolean = {
    val lines = properSplit(content);
    position.character == 0
  }

  def isEndOfText(content: String, position: Position): Boolean = {
    val lines = properSplit(content);
    position.line == lines.length -1 && position.character == lines(position.line).length -1
  }

  def isStartOfText(content: String, position: Position): Boolean = {
    position.line == 0 && position.character == 0
  }

  def isOnFirstLine(content: String, position: Position): Boolean = {
    position.line == 0
  }

  def isOnLastLine(content: String, position: Position): Boolean = {
    val lines = properSplit(content);
    position.line == lines.length -1
  }

  def getPositionAfter(content: String, position: Position): Option[Position] = {
    if (!isEndOfText(content, position)) {
      if (isEndOfLine(content,position)) Some(Position(position.line + 1 , 0))
      else Some(position.copy(character = position.character + 1))
    } else None
  }

  def getPositionBefore(content: String, position: Position): Option[Position] = {
    val lines = properSplit(content);
    val startOfLine = isStartOfLine(content, position);
    if (!isStartOfText(content,position)) {
      if (startOfLine) Some(Position(position.line - 1 , lines(position.line - 1).length -1))
      else Some(position.copy(character = position.character - 1))
    } else None
  }

  /**
    * Position of the cursor
    * @param line line'th line, starts from 0
    * @param character character'th character, starts from 0
    */
  case class Position(line: Int, character: Int) extends Ordered[Position]{

    override def compare(that: Position): Int = {
      line match{
        case that.line => character match {
          case that.character => 0
          case _ => if (character < that.character) -1 else 1
        }
        case _ => if (line < that.line) -1 else 1
      }
    }

  }
}
