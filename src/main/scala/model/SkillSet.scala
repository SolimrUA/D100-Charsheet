package model

import model.BD.{baseSkills, skillGroups, defaultSkill}

object SkillSet {
    
  def transformSkills(instructions: Array[String], skills: SkillMap = baseSkills): SkillMap = {
    instructions.foldLeft(skills) { 
      case (transformedSkills, instruction) => performInstruction(instruction, transformedSkills)
    }
  }

  private def performInstruction(instruction: String, skills: SkillMap): SkillMap = 
    instruction.trim match {
      case r"\+ skill ([a-zA-Z]+)$skillName ([\w\*\s]*)$value" => skills + (skillName -> parse(value))
      case r"\+ skill \'([a-zA-Z\s]+)\'$skillName ([\w\*\s]*)$value" => skills + (skillName -> parse(value))
      case r"\+ skill ([a-zA-Z]+)$skillName" => skills + (skillName -> defaultSkill)
      case r"\+ skill \'([a-zA-Z\s]+)\'$skillName" => skills + (skillName -> defaultSkill)
      case r"\- skill ([a-zA-Z]+)$skillName" => skills - skillName
      case r"\- skill \'([a-zA-Z\s]+)\'$skillName" => skills - skillName
      case r"system ([a-zA-Z]+)$groupName" => transformSkills(skillGroups.getOrElse(groupName, Array()), skills)
      case _ => skills
    }

  def parse(s: String): SkillExpression =
    try {
      s.trim match {
        case r"(\d+)$value" => Literal(value.toInt)
        case r"([a-zA-Z]+)$value" => Reference(value)
        case r"(\d+)$modifier\s*\*\s*(\w+)$value" => Product(Literal(modifier.toInt), parse(value))
        case _ => Empty()
      }
    } catch {
      case e: NumberFormatException => Empty()
    }
}
