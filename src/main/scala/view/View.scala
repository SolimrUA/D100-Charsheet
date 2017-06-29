package view

import java.net.URL
import javafx.{scene => jfxs}

import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{GridPane, HBox, VBox}
import scalafx.scene.paint.Color

object View extends Scene {
  
  val stylesheet: URL = getClass.getResource("jfx.css")
  stylesheets.add(stylesheet.toString)

  val instructionsArea = new TextArea() {promptText = "Instructions"}
  val nameField = new InfoField()
  val ageField = new InfoField()
  val sexField = new InfoField()
  val reRollButton = new Button("Reroll")
  
  val characterInfo = new GridBlock("Character Info")
  val traitBlock = new GridBlock("Characteristics")
  val skillView = new GridBlock("Skills")
  
  characterInfo.addRowToGrid(0, new InfoLabel("Name:"), nameField)
  characterInfo.addRowToGrid(1, new InfoLabel("Age:"), ageField)
  characterInfo.addRowToGrid(2, new InfoLabel("Sex:"), sexField)
  
  content = new VBox() {
    val topLine = new HBox() {
      children.addAll(characterInfo, traitBlock)
      id = "top-line"
    }
    children.addAll(topLine, skillView, instructionsArea)
  }
  
  class GridBlock(title: String) extends TitledPane {
    text = title
    val grid = new GridPane() {
      styleClass.add("grid-pane")
    }
    content = grid
    maxHeight = Double.MaxValue
    minWidth = 250
    
    def addToGrid(n: jfxs.Node, col: Int, row: Int) = grid.add(n, col, row)
    def addToGrid(n: jfxs.Node, col: Int, row: Int, colSpan: Int, rowSpan: Int) = grid.add(n, col, row, colSpan, rowSpan)
    def clearGrid() = grid.children.clear()
    def addRowToGrid(row: Int, values: jfxs.Node*) = grid.addRow(row, values:_*)
    def addColumnToGrid(col: Int, values: jfxs.Node*) = grid.addColumn(col, values:_*)
    def getFromGrid(col: Int, row: Int) =
      for {
        node <- grid.children
        if GridPane.getRowIndex(node) == row
        if GridPane.getColumnIndex(node) == col
      } yield node
    
    def replaceInGrid(n: jfxs.Node, col: Int, row: Int): Unit = {
      grid.children.removeAll(getFromGrid(col, row))
      addToGrid(n, col ,row)
    }
  }

  class InfoLabel(text: String) extends Label(text) {
    styleClass.add("info-name")
  }
  class InfoField extends TextField {
    styleClass.add("info-value")
  }
  class ExpressionLabel(expressionText: String) extends Label(expressionText) {
    styleClass.add("expression")
    textFill <== when (text === "Error") choose Color.Red otherwise Color.LightGray
  }
  class ValueLabel(text: String) extends Label(text) {
    styleClass.add("value-label")
  }
}
