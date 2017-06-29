

abstract class Thing
case class Foo() extends Thing
case class Bar() extends Thing
val order1 = Ordering.by[String, Int] {
  case "A" => 0
  case "B" => 1
  case "C" => 2
  case _ => -1
}
