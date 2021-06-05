package viw

import org.scalatest.{BeforeAndAfter, FunSuite}
import viw.Commands.StateCommands.MoveCommands._
import viw.Commands.DeleteCommands._
import viw.Commands.EditorCommands.Bind
import viw.Commands.StateCommands._
import viw.Commands._


class CommandFactoryTest extends FunSuite with ViwTest with BeforeAndAfter {

  val defaultEditorState = EditorState(None, false, Map[Char, Command](), "")

  before {
    // setup your test
  }

  after {
    // cleanup your history
  }

  test ("Produce Single Character Commands") {

    assert(CommandFactory.produceCommand(Vector("h"), defaultEditorState) == Some(Left))
    assert(CommandFactory.produceCommand(Vector("j"), defaultEditorState) == Some(Down))
    assert(CommandFactory.produceCommand(Vector("k"), defaultEditorState) == Some(Up))
    assert(CommandFactory.produceCommand(Vector("l"), defaultEditorState) == Some(Right))
    assert(CommandFactory.produceCommand(Vector(" "), defaultEditorState) == Some(Start))
    assert(CommandFactory.produceCommand(Vector("i"), defaultEditorState) == Some(Exit))
    assert(CommandFactory.produceCommand(Vector("w"), defaultEditorState) == Some(NextWord))
    assert(CommandFactory.produceCommand(Vector("b"), defaultEditorState) == Some(BackWord))
    assert(CommandFactory.produceCommand(Vector("e"), defaultEditorState) == Some(EndOfWord))
    assert(CommandFactory.produceCommand(Vector("$"), defaultEditorState) == Some(EndOfLine))
    assert(CommandFactory.produceCommand(Vector("0"), defaultEditorState) == Some(StartOfLine))
    assert(CommandFactory.produceCommand(Vector("%"), defaultEditorState) == Some(MatchBracket))
    assert(CommandFactory.produceCommand(Vector("x"), defaultEditorState) == Some(Delete))
    assert(CommandFactory.produceCommand(Vector("X"), defaultEditorState) == Some(DeleteBackwards))
    assert(CommandFactory.produceCommand(Vector("D"), defaultEditorState) == Some(DeleteLine))
    assert(CommandFactory.produceCommand(Vector("J"), defaultEditorState) == Some(JoinLine))
    assert(CommandFactory.produceCommand(Vector("a"), defaultEditorState) == Some(Append))
    assert(CommandFactory.produceCommand(Vector("o"), defaultEditorState) == Some(Open))
    assert(CommandFactory.produceCommand(Vector("s"), defaultEditorState) == Some(Substitute))
    assert(CommandFactory.produceCommand(Vector("G"), defaultEditorState) == Some(Go))
    assert(CommandFactory.produceCommand(Vector("I"), defaultEditorState) == Some(InsertInLine))
    assert(CommandFactory.produceCommand(Vector("A"), defaultEditorState) == Some(InsertAfterLine))
    assert(CommandFactory.produceCommand(Vector("C"), defaultEditorState) == Some(ChangeLine))
    assert(CommandFactory.produceCommand(Vector("."), defaultEditorState) == Some(NotBound))
    assert(CommandFactory.produceCommand(Vector("."), EditorState(Some(Left), false, Map[Char, Command](), "")) == Some(Left))
    assert(CommandFactory.produceCommand(Vector("p"), defaultEditorState) == Some(new Paste("")))
    assert(CommandFactory.produceCommand(Vector("P"), defaultEditorState) == Some(new PasteBehind("")))
    assert(CommandFactory.produceCommand(Vector("@"), defaultEditorState) == None)
    assert(CommandFactory.produceCommand(Vector("d"), defaultEditorState) == None)
    assert(CommandFactory.produceCommand(Vector("f"), defaultEditorState) == None)
    assert(CommandFactory.produceCommand(Vector("c"), defaultEditorState) == None)
    assert(CommandFactory.produceCommand(Vector("5"), defaultEditorState) == None)

  }

  test ("Produce Multiple Character Commands") {

    assert(CommandFactory.produceCommand(Vector("d", "h"), defaultEditorState).get == new DeleteMovement(Left))
    assert(CommandFactory.produceCommand(Vector("d", "d"), defaultEditorState).get == DeleteEntireLine)
    assert(CommandFactory.produceCommand(Vector("f", "@"), defaultEditorState).get == new Find("@"))
    assert(CommandFactory.produceCommand(Vector(">", ">"), defaultEditorState) == Some(IndentLine))
    assert(CommandFactory.produceCommand(Vector(">", "l"), defaultEditorState) == Some(IndentLine))
    assert(CommandFactory.produceCommand(Vector(">", "h"), defaultEditorState) == Some(IndentLine))
    assert(CommandFactory.produceCommand(Vector(">", "j"), defaultEditorState) == Some(IndentTwoLines))
    assert(CommandFactory.produceCommand(Vector("<", "<"), defaultEditorState) == Some(DedentLine))
    assert(CommandFactory.produceCommand(Vector("<", "l"), defaultEditorState) == Some(DedentLine))
    assert(CommandFactory.produceCommand(Vector("<", "h"), defaultEditorState) == Some(DedentLine))
    assert(CommandFactory.produceCommand(Vector("<", "j"), defaultEditorState) == Some(DedentTwoLines))
    assert(CommandFactory.produceCommand(Vector("5", "h"), defaultEditorState).get == new Count(Left, 5))
    assert(CommandFactory.produceCommand(Vector("5", "5", "h"), defaultEditorState).get == new Count(Left, 55))
    assert(CommandFactory.produceCommand(Vector("5", "5", "5", "h"), defaultEditorState).get == new Count(Left, 555))
    assert(CommandFactory.produceCommand(Vector("d", "5", "h"), defaultEditorState).get == new DeleteMovement(new Count(Left, 5)))
    assert(CommandFactory.produceCommand(Vector("d", "5", "5", "h"), defaultEditorState).get == new DeleteMovement(new Count(Left, 55)))
    assert(CommandFactory.produceCommand(Vector("c", "c"), defaultEditorState).get  == DeleteLine)
    assert(CommandFactory.produceCommand(Vector("c", "d", "h"), defaultEditorState).get  == new ChangeMovement(new DeleteMovement(Left)))
    assert(CommandFactory.produceCommand(Vector("d","3"), defaultEditorState) == None)
    assert(CommandFactory.produceCommand(Vector("c","3"), defaultEditorState) == None)
    assert(CommandFactory.produceCommand(Vector("c", "d","3"), defaultEditorState) == None)
    assert(CommandFactory.produceCommand(Vector("c", "d","3", "3"), defaultEditorState) == None)
    assert(CommandFactory.produceCommand(Vector("z", "5","h"), EditorState(Some(Left), false, Map[Char, Command](), "")).get == new ChainCommand(new Count(Left, 5), Left))
    assert(CommandFactory.produceCommand(Vector("z", "z", "5","h"), EditorState(Some(Left), false, Map[Char, Command](), "")).get == new ChainCommand(new ChainCommand(new Count(Left, 5), Left), Left))
    assert(CommandFactory.produceCommand(Vector("B", "g"), EditorState(Some(Left), false, Map[Char, Command](), "")).get == new Bind('g'))
    assert(CommandFactory.produceCommand(Vector("y", "h"), defaultEditorState).get == new YankMovement(Left))

  }

  test ("Not Bound Commands") {

    assert(CommandFactory.produceCommand(Vector("d","x"), defaultEditorState) == Some(NotBound))
    assert(CommandFactory.produceCommand(Vector("g"), defaultEditorState) == None)
    assert(CommandFactory.produceCommand(Vector("g","g"), defaultEditorState) == Some(NotBound))
    assert(CommandFactory.produceCommand(Vector("c","x"), defaultEditorState) == Some(NotBound))
    assert(CommandFactory.produceCommand(Vector("5","x"), defaultEditorState) == Some(NotBound))
    assert(CommandFactory.produceCommand(Vector(">","x"), defaultEditorState) == Some(NotBound))
    assert(CommandFactory.produceCommand(Vector("<","x"), defaultEditorState) == Some(NotBound))
    assert(CommandFactory.produceCommand(Vector("d","5", "x"), defaultEditorState) == Some(NotBound))
    assert(CommandFactory.produceCommand(Vector("d","5", "5", "x"), defaultEditorState) == Some(NotBound))


  }

  test ("Produce Commands ViewMode") {

    assert(CommandFactory.produceCommand(Vector("5", "5", "5", "h"), EditorState(None, true, Map[Char, Command](), "")).get == new Selection(new Count(Left, 555)))

  }


}
