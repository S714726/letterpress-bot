package me.rickychang.lpb.solver

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import javax.imageio.ImageIO
import java.io.File
import me.rickychang.lpb.imageparser.JavaOCRCharParser
import me.rickychang.lpb.imageparser.MultiDeviceParser
import me.rickychang.lpb.board.GameBoard

@RunWith(classOf[JUnitRunner])
class SolverSuite extends FunSuite {
  
  private val boardSolver = new BoardSolver(new WordDictionary("/dicts/testDict.txt"))
  private val parser = new MultiDeviceParser(new JavaOCRCharParser)

  test("iPhone 5 Solver test board 1") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board1.png"))
    val gameBoard = parser.parseGameBoard(boardImage)
    val wordsToPlay = boardSolver.findWords(gameBoard, 5).map {
      case (w, t) => (w, boardSolver.scoreDeltas(t))
    }
    assert(wordsToPlay == List(("ATTACHMENT", (5, -4)),
      ("ATTACHMENTS", (5, -4)),
      ("PENCHANT", (5, -4)),
      ("PENCHANTS", (5, -4)),
      ("PHANTAST", (5, -4))))
  }

  test("iPhone 5 Solver test board 2") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board2.png"))
    val gameBoard = parser.parseGameBoard(boardImage)
    val wordsToPlay = boardSolver.findWords(gameBoard, 5).map {
      case (w, t) => (w, boardSolver.scoreDeltas(t))
    }
    assert(wordsToPlay == List(("UNSTEADYING", (11, -5)),
      ("MAGNITUDES", (10, -5)),
      ("NUTMEGGING", (10, -5)),
      ("UNDEAFING", (9, -6)),
      ("ANGUISHED", (9, -5))))
  }

  test("iPhone 5 Solver test board 3") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-test-board3.png"))
    val gameBoard = parser.parseGameBoard(boardImage)
    val wordsToPlay = boardSolver.findWords(gameBoard, 5).map {
      case (w, t) => (w, boardSolver.scoreDeltas(t))
    }
    assert(wordsToPlay == List(("BETTER", (6, 0)),
      ("TRIO", (4, 0)),
      ("COPE", (4, 0)),
      ("BEE", (3, 0)),
      ("POT", (3, 0))))
  }

  test("iPhone 5 Solver twitter board 1") {
    val boardImage = ImageIO.read(getClass.getResource("/images/test/iphone5-twitter-board1.jpg"))
    val gameBoard = parser.parseGameBoard(boardImage)
    val wordsToPlay = boardSolver.findWords(gameBoard, 5).map {
      case (w, t) => (w, boardSolver.scoreDeltas(t))
    }
    assert(wordsToPlay == List(("WALLYDRAGS", (9, -8)),
      ("GLASSWORK", (8, -8)),
      ("KARYOPLASMS", (8, -8)),
      ("VOLKSRAADS", (9, -7)),
      ("GALLOWGLASS", (8, -7))))
  }

  test("Winning word rankings") {
    val b = GameBoard("AB AB AB AB AB AB AB AB AB AB AB AB AB AB AB AB AB AB Aw Er Br Er Tr Tr Rr")
    val wordsAndTiles = boardSolver.findWords(b, 5)
    val wordsToPlay = wordsAndTiles.map {
      case (w, t) => {
        val (p, o) = boardSolver.scoreDeltas(t)
        val a = if (boardSolver.isWinningWord(b, t)) "*" else ""
        "%s%s (+%d,%d)".format(w, a, p, o)
      }
    }.mkString(", ")
    assert(wordsToPlay == "BAT* (+3,-2), BETTER (+6,-6), BEE (+3,-3)")
  }

}