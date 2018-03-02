package ryysissimulaatio

import scala.util.Random

class SteeringAlgorithm( val human: Human, val room: Room) {
  def getAcceleration () : Vector2D = {
    var returnVector = new Vector2D(0,0)
    returnVector += seek().truncate(1.0)
    returnVector += brake().truncate(10.0)
    returnVector += avoid().truncate(1.0)
    returnVector += wander().truncate(1.0)
    return returnVector.normalize()
  }
  
  private def seek() : Vector2D = {
    val desiredVelocity = (room.getDoorCoordinates - human.position).truncate(Human.MaxSpeed)
    desiredVelocity - human.velocity
  }
  
  private def brake() : Vector2D = Vector2D(0,0)
  
  private def avoid() : Vector2D = Vector2D(0,0)
  
  private def wander() : Vector2D = {
    val rand = new Random()
    Vector2D(rand.nextDouble()-rand.nextDouble(), rand.nextDouble()-rand.nextDouble()).normalize
  }
}
