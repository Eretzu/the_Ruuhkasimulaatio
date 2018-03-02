package ryysissimulaatio

import scala.util.Random

object SteeringAlgorithm {
  def getAcceleration ( human: Human, room: Room ) : Vector2D = {
    var returnVector = new Vector2D(0,0)
    returnVector += seek(human, room)
    returnVector += brake(human, room)
    returnVector += avoid(human, room)
    returnVector += wander()
    return returnVector
  }
  
  private def seek(human: Human, room: Room) : Vector2D = {
    return Vector2D(0, 0)
  }
  
  private def brake(human: Human, room: Room) : Vector2D = return Vector2D(0,0)
  
  private def avoid(human: Human, room: Room) : Vector2D = return Vector2D(0,0)
  
  private def wander() : Vector2D = {
    val rand = new Random()
    return Vector2D(rand.nextDouble()-rand.nextDouble(), rand.nextDouble()-rand.nextDouble()).normalize
  }
}
