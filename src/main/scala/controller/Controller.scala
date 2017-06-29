package controller

import model._
import view.View
import view.View.{ExpressionLabel, InfoLabel, ValueLabel}

import scala.collection.SortedMap
import scala.util.Random
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.{IntegerProperty, LongProperty, ObjectProperty}
import scalafx.scene.control.TextField
import scalafx.scene.input.KeyEvent

object Controller extends JFXApp {

  //===================================================================
  //Trait control

  val seed = LongProperty(System.currentTimeMillis())

  View.reRollButton.onAction = handle {
    seed() = System.currentTimeMillis()
  }
  
  val traitMap : ObjectProperty[TraitValues] = ObjectProperty(SortedMap.empty[String, Int])

  def calcTraits(seed: LongProperty): TraitValues = {
    val randomGenerator = new Random(seed())
    SortedMap.empty(BD.traitOrder) ++ {
      for {
        (name, traitExpr) <- BD.traitExpression
        value = evalTrait(traitExpr, randomGenerator)
      } yield name -> value
    }
  }

  private def evalTrait(e: Trait, r: Random): Int =
    (for (i <- 1 to e._1) yield
      r.nextInt(6) + 1).foldLeft(e._2)(_ + _)
  
  traitMap <== createObjectBinding(
    () => calcTraits(seed),
    seed
  )
  
  traitMap.onChange {
    repaintTraitGrid()
  }

  //===================================================================
  // Skill control

  val skillMap = ObjectProperty(SortedMap.empty[String, SkillExpression])
  val skillBaseValues = ObjectProperty(SortedMap.empty[String, Int])
  val skillModifiers = ObjectProperty(SortedMap.empty[String, Int])
  val skillGridHeight = IntegerProperty(0)
  
  private final def maxSkill = 90

  def evalSkill(e: SkillExpression): Int = {
    val result = e match {
      case Literal(value) => value
      case Reference(name) => traitMap().getOrElse(name, 0)
      case Product(a, b) => evalSkill(a) * evalSkill(b)
      case Empty() => 0
    }
    if (result < maxSkill) result else maxSkill
  }

  skillMap.onChange {
    repaintSkillGrid()
  }
  skillBaseValues.onChange {
    repaintSkillGrid()
  }
  skillModifiers.onChange {
    println(skillModifiers)
  }

  skillGridHeight <==
    createIntegerBinding(
      () => Math.max(Math.min(20, (skillMap().size + 2) / 3), 5),
      skillMap
    )

  skillBaseValues <==
    createObjectBinding(
      () => for ((name, expr) <- skillMap()) yield (name, evalSkill(expr)),
      skillMap, traitMap
    )

  skillMap <==
    createObjectBinding(
      () => SkillSet.transformSkills(View.instructionsArea.text().split("\n")),
      View.instructionsArea.text
    )
  
  stage = new PrimaryStage {
    scene = View
  }
  seed() = System.currentTimeMillis()
  
  //===============================================================
  // Paint Control

  for (((name, _), index) <- traitMap().zipWithIndex) {
    View.traitBlock.addToGrid(new InfoLabel(name), 0, index)
    View.traitBlock.addToGrid(new ExpressionLabel(TraitSet.express(name)), 1, index)
  }
  View.traitBlock.addToGrid(View.reRollButton, 3, 0)
  
  def repaintTraitGrid() =
    for ( ((_, value), index) <- traitMap().zipWithIndex)
      View.traitBlock.replaceInGrid(new ValueLabel(value.toString), 2, index)
    
  def repaintSkillGrid(): Unit = {
    View.skillView.clearGrid()
    for {
      ((key, expression), index) <- skillMap().toList.zipWithIndex
      baseValue = skillBaseValues()(key)
      value = baseValue + skillModifiers().getOrElse(key, 0)
    } {
      val skillValue = new SkillField(value.toString)
      skillValue.text.onChange {
        if (skillValue.text().nonEmpty)
          skillModifiers() = skillModifiers().updated(key, skillValue.text().toInt - baseValue)
      }
      View.skillView.addRowToGrid(
        index % skillGridHeight(),
        new InfoLabel(key),
        new ExpressionLabel(expression.toString),
        skillValue)
    }
  }
  
  class SkillField(value: String) extends TextField {
    styleClass.add("skill-field")
    text = value
    onKeyTyped =
      (event: KeyEvent) => {
        if (!event.character.forall(Character.isDigit))
          event.consume()
        else if (text().length >= 2)
          event.consume()
      }
  }
}
