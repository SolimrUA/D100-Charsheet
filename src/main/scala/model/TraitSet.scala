package model

import model.BD.{traitExpression, defaultTrait}

object TraitSet {
  
  def express(name: String): String = {
    val e = traitExpression.getOrElse(name, defaultTrait)
    s"${e._1}D6${if (e._2 > 0) " + " else ""}${if (e._2 != 0) e._2 else ""}"
  }

  def parse(s: String): Trait = {
    try {
      val numbers = s.split("D6", 2)
      (numbers.head.toInt, numbers.tail.head.toInt)
    } catch {
      case e: UnsupportedOperationException => defaultTrait
      case e: NumberFormatException => defaultTrait
    }
  }
}
