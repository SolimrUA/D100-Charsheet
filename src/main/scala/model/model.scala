import scala.collection.SortedMap
import scala.util.matching.Regex

package object model {
  
  implicit class RegexContext(sc: StringContext) {
    def r = new Regex(sc.parts.mkString, sc.parts.tail.map(_ => "x"): _*)
  }

  sealed abstract class SkillExpression {
    def express(e: SkillExpression): String = e match {
      case Literal(value) => value.toString
      case Reference(name) => name
      case Product(a, b) => s"${express(a)}*${express(b)}"
      case Empty() => "Error"
    }
    
    override def toString: String = express(this)
  }
  case class Literal(v: Int) extends SkillExpression
  case class Reference(s: String) extends SkillExpression
  case class Product(a: SkillExpression, b: SkillExpression) extends SkillExpression
  case class Empty() extends SkillExpression
  
  type SkillMap = SortedMap[String, SkillExpression]

  type Trait = (Int, Int) //multiplier and modifier, i.e. 3D6+1 => (3, 1) 
  type TraitExpression = SortedMap[String, Trait]
  type TraitValues = SortedMap[String, Int]
}
