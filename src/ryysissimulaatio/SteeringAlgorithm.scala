package ryysissimulaatio

import scala.util.Random

class SteeringAlgorithm( val human: Human, val room: Room) {
  def getAcceleration () : Vector2D = {
    var returnVector = new Vector2D(0,0)
    returnVector += seek().truncate(1.0)
    returnVector += avoid().truncate(2.0)
    returnVector += wander().truncate(1.0)
    returnVector += separation()
    return returnVector.normalize()
  }
  
  private def seek() : Vector2D = {
    val desiredVelocity = (room.getDoorCoordinates - human.position).truncate(Human.MaxSpeed)
    desiredVelocity - human.velocity
  }
  
  private def avoid() : Vector2D = {
    var x, y = 0
    if(human.position.x < 30 && (human.position.y < room.door-25 || human.position.y > room.door+25)) x = (30 - human.position.x.toInt)/15
    else if(room.width - human.position.x < 30) x = (30 - (room.width - human.position.x.toInt))/15
    if(human.position.y < 30) y = (30 - human.position.y.toInt)/15
    else if(room.width - human.position.y < 30) y = (30 - (room.width - human.position.y.toInt))/15 
    Vector2D(x,y)
  }
  
  private def separation() : Vector2D = {
    val neighborhood = room.humans.filter(other => (other.position - human.position).length < 50) 
    Vector2D(0,0)
  }
  
  // maybe not needed with separation
  private def brake: Vector2D = ???
  
  private def wander() : Vector2D = {
    val rand = new Random()
    Vector2D(rand.nextDouble()-rand.nextDouble(), rand.nextDouble()-rand.nextDouble()).normalize
  }
}
