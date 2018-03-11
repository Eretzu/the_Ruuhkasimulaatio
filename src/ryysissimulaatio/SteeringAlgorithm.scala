package ryysissimulaatio

import scala.util.Random
import scala.math.pow

class SteeringAlgorithm( val human: Human, val room: Room) {
  def getAcceleration () : Vector2D = {
    var returnVector = new Vector2D(0,0)
    returnVector += seek().truncate(1.0)
    returnVector += avoid().truncate(2.0)
    returnVector += wander().truncate(1.0)
    returnVector += separation().truncate(1.0)
    returnVector
  }
  
  private def seek() : Vector2D = {
    val desiredVelocity = (room.getDoorCoordinates - human.position).truncate(Human.MaxSpeed)
    desiredVelocity - human.velocity
  }
  
  private def avoid() : Vector2D = {
    var x, y = 0
    val WallDistance = 20
    if(human.position.x < WallDistance && 
      (human.position.y < room.door-WallDistance || human.position.y > room.door+WallDistance)) 
      x = (30 - human.position.x.toInt)/WallDistance*2
    else if(room.width - human.position.x < WallDistance) x = (WallDistance - (room.width - human.position.x.toInt))/WallDistance*2
    if(human.position.y < WallDistance) y = (WallDistance - human.position.y.toInt)/WallDistance*2
    else if(room.width - human.position.y < WallDistance) y = (WallDistance - (room.width - human.position.y.toInt))/WallDistance*2 
    Vector2D(x,y)
  }
  
  private def separation() : Vector2D = {
    val neighborhood = room.humans.map(other => (human.position - other.position)).filter( _.length < 80 )
    neighborhood.map( distance => distance * pow(distance.length, 2) ).fold(Vector2D(0, 0)) { _ + _ }
  }
  
  // maybe not needed with separation
  //private def brake: Vector2D = ???
  
  private def wander() : Vector2D = {
    val rand = new Random()
    Vector2D(rand.nextDouble()-rand.nextDouble(), rand.nextDouble()-rand.nextDouble()).normalize
  }
}
