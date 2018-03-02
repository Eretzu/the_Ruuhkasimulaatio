package ryysissimulaatio

import scala.util.Random

object SteeringAlgorithm {
  def getAcceleration ( human: Human, room: Room ) : Vector2D = {
    var returnVector = new Vector2D(0,0)
    returnVector += seek(human, room).truncate(1.0)
    returnVector += brake(human, room).truncate(10.0)
    returnVector += avoid(human, room).truncate(1.0)
    returnVector += wander().truncate(1.0)
    return returnVector.normalize()
  }
  
  private def seek(human: Human, room: Room) : Vector2D = {
    val desiredVelocity = (room.getDoorCoordinates - human.position).truncate(Human.MaxSpeed)
    desiredVelocity - human.velocity
  }
  
  private def brake(human: Human, room: Room) : Vector2D = Vector2D(0,0)
  
  private def avoid(human: Human, room: Room) : Vector2D = Vector2D(0,0)
  
  private def wander() : Vector2D = {
    val rand = new Random()
    Vector2D(rand.nextDouble()-rand.nextDouble(), rand.nextDouble()-rand.nextDouble()).normalize
  }
}
