package ryysissimulaatio

import scala.util.Random
import scala.math._


// Steering algorithm implementation based on following paper: https://www.red3d.com/cwr/steer/gdc99/
class SteeringAlgorithm( val human: Human, val room: Room) {
  def getAcceleration () : Vector2D = {
    var returnVector = new Vector2D(0, 0)
    returnVector += seek()*1
    //returnVector += avoid()*1
    //returnVector += wander()*1
    returnVector += separation()*1
    returnVector
  }
  
  private def seek() : Vector2D = {
    val desiredPosition = room.door - human.position
    if(human.position.x < 0 || desiredPosition.length < room.doorWidth/2) return Vector2D(-10,0)  
    val desiredVelocity = desiredPosition.truncate(Human.MaxSpeed)
    desiredVelocity - human.velocity
  }
  
  private def avoid() : Vector2D = {
    var x, y = 0
    Vector2D(x,y)
  }
   
  private def separation() : Vector2D = {
    // Filter away those in rear arc.
    val back = -human.velocity
    val visible = room.humans.filter( other => (other.position angle back) < Pi/3 )
    // Map to distances from other humans and filter to contain only near humans.
    val neighborhood = visible.map(other => (human.position - other.position)).filter( _.length < 2 )
    neighborhood.map( distance => distance / pow(distance.length, 2) ).fold(Vector2D(0, 0)) { _ + _ }
  }
  
  private def wander() : Vector2D = {
    val rand = new Random()
    Vector2D(rand.nextDouble()-rand.nextDouble(), rand.nextDouble()-rand.nextDouble()).normalize
  }
}
