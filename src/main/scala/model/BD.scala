package model

import scala.collection.SortedMap

object BD {
  val defaultTrait: Trait = (0, 0)
  
  val traitOrder = Ordering.by[String, Int] {
    case "STR" => 0
    case "CON" => 1
    case "POW" => 2
    case "DEX" => 3
    case "APP" => 4
    case "SIZ" => 5
    case "INT" => 6
    case "EDU" => 7
    case _ => -1
  }
  
  val traitExpression = SortedMap(
    "STR" -> (3, 0), "CON" -> (3, 0),
    "POW" -> (3, 0), "DEX" -> (3, 0),
    "APP" -> (3, 0), "SIZ" -> (2, 6),
    "INT" -> (2, 6), "EDU" -> (3, 3)
  )(traitOrder)

  val defaultSkill = Literal(1)
  
  val skillGroups: Map[String, Array[String]] = Map(
    "Cthulhu" -> Array(
      "- skill Appraise",
      "+ skill Accounting 10",
      "+ skill Anthropology 01",
      "+ skill Archeology 01",
      "+ skill Astronomy 01",
      "+ skill Biology 01",
      "+ skill Chemistry 01",
      "- skill Command",
      "+ skill 'Computer Use' 01",
      "+ skill Conceal 15",
      "+ skill 'Credit Rating' 15",
      "+ skill 'Cthulhu Mythos' 00",
      "- skill Demolition",
      "+ skill Drive 20",
      "+ skill 'Electrical Repair' 10",
      "+ skill Electronics 01",
      "- skill Etiquette",
      "- skill 'Fine Manipulation'",
      "+ skill Fist 50",
      "+ skill Geology 01",
      "+ skill Grapple 25",
      "+ skill Handgun 20",
      "+ skill 'Head Butt' 10",
      "- skill 'Heavy Machine'",
      "+ skill History 20",
      "- skill Insight",
      "+ skill Kick 25",
      "+ skill 'Language, Own' 5*EDU",
      "+ skill 'Language, Other' 01",
      "+ skill Law 05",
      "+ skill 'Library Use' 25",
      "+ skill Locksmith 01",
      "- skill Literacy",
      "+ skill 'Machine Gun' 15",
      "+ skill 'Mechanical Repair' 20",
      "+ skill Medicine 05",
      "+ skill 'Natural History' 10",
      "+ skill Occult",
      "+ skill 'Operate Heavy Machine' 01",
      "- skill Perform",
      "+ skill Pharmacy 01",
      "+ skill Photography 10",
      "+ skill Physics 01",
      "- skill Projection",
      "+ skill Psychoanalysis 01",
      "+ skill Psychology 05",
      "- skill Psychotherapy",
      "- skill Repair",
      "- skill Research",
      "+ skill Rifle 25",
      "- skill Science",
      "- skill Sense",
      "+ skill Shotgun 30",
      "- skill 'Sleight of Hand'",
      "+ skill Sneak 10",
      "- skill Status",
      "- skill Stealth",
      "- skill Strategy",
      "+ skill 'Submachine Gun' 15",
      "- skill Teach",
      "- skill 'Technical Skill'",
      "+ skill Track 10"
    ),
    "Hedgehogs" -> Array(
      "- skill Appraise",
      "+ skill Chemistry 01",
      "- skill Command",
      "+ skill Conceal 15",
      "- skill Demolition",
      "- skill Etiquette",
      "- skill 'Fine Manipulation'",
      "+ skill Fist 50",
      "+ skill Grapple 25",
      "+ skill 'Head Butt' 10",
      "- skill 'Heavy Machine'",
      "+ skill History 20",
      "- skill Insight",
      "+ skill Kick 25",
      "+ skill Law 05",
      "+ skill 'Library Use' 25",
      "- skill Literacy",
      "+ skill Medicine 05",
      "+ skill 'Other Language' 01",
      "+ skill 'Own Language' 5*EDU",
      "- skill Perform",
      "- skill Projection",
      "+ skill Psychoanalysis 01",
      "+ skill Psychology 05",
      "- skill Psychotherapy",
      "- skill Repair",
      "- skill Research",
      "- skill Science",
      "- skill Sense",
      "- skill 'Sleight of Hand'",
      "+ skill Sneak 10",
      "- skill Status",
      "- skill Stealth",
      "- skill Strategy",
      "- skill Teach",
      "- skill 'Technical Skill'",
      "+ skill Track 10"
    )
  )

  val baseSkills: SkillMap = SortedMap(
    "Appraise" -> Literal(15),
    "Art" -> Literal(5),
    "Bargain" -> Literal(5),
    "Climb" -> Literal(40),
    "Command" -> Literal(5),
    "Craft" -> Literal(5),
    "Demolition" -> defaultSkill,
    "Disguise" -> defaultSkill,
    "Dodge" -> Product(Literal(2), Reference("DEX")),
    "Etiquette" -> Literal(5),
    "Fast Talk" -> Literal(5),
    "Fine Manipulation" -> Literal(15),
    "First Aid" -> Literal(30),
    "Heavy Machine" -> defaultSkill,
    "Hide" -> Literal(10),
    "Insight" -> Literal(5),
    "Jump" -> Literal(25),
    "Listen" -> Literal(25),
    "Literacy" -> defaultSkill,
    "Martial Arts" -> defaultSkill,
    "Medicine" -> defaultSkill,
    "Navigate" -> Literal(10),
    "Other Language" -> Literal(0),
    "Own Language" -> Product(Literal(5), Reference("INT")),
    "Perform" -> Literal(5),
    "Persuade" -> Literal(15),
    "Pilot" -> defaultSkill,
    "Projection" -> Product(Literal(2), Reference("DEX")),
    "Psychotherapy" -> defaultSkill,
    "Repair" -> Literal(15),
    "Research" -> Literal(25),
    "Ride" -> Literal(5),
    "Science" -> defaultSkill,
    "Sense" -> Literal(10),
    "Sleight of Hand" -> Literal(5),
    "Spot" -> Literal(25),
    "Status" -> Literal(15),
    "Stealth" -> Literal(10),
    "Strategy" -> defaultSkill,
    "Swim" -> Literal(25),
    "Teach" -> Literal(10),
    "Technical Skill" -> defaultSkill,
    "Throw" -> Literal(25),
    "Track" -> Literal(10)
  )
}
