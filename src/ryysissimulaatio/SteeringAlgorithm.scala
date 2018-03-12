package ryysissimulaatio

import scala.util.Random
import scala.math.pow

class SteeringAlgorithm( val human: Human, val room: Room) {
  def getAcceleration () : Vector2D = {
    var returnVector = new Vector2D(0,0)
    returnVector += seek()
    //returnVector += avoid()
    //returnVector += wander()
    //returnVector += separation()
    returnVector
  }
  
  private def seek() : Vector2D = {
    val desiredPosition = room.door - human.position
    if(human.position.x < 0 || desiredPosition.length < room.doorWidth/2) return Vector2D(-10, 0)
    val desiredVelocity = desiredPosition.truncate(Human.MaxSpeed)
    desiredVelocity - human.velocity
  }
  
  private def avoid() : Vector2D = {
    var x, y = 0
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
