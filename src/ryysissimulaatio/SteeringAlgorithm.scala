package ryysissimulaatio

import scala.util.Random
import scala.math.pow

class SteeringAlgorithm( val human: Human, val room: Room) {
  private var _wanderDirection = Vector2D(0, 0)

  // Steering algorithm sums different parts together and returns the resulting vector.
  def getAcceleration () : Vector2D = {
    var returnVector = new Vector2D(0, 0)
    if((room.door - human.position).length <= room.doorWidth/2) {
      returnVector += passThrough()*0.05
      returnVector += Vector2D(-1, 0)
    } else if (human.position.x < 0) {
      returnVector += Vector2D(-1, 0)
    } else {
      if(room.seek) returnVector += seek()*1
      if(room.avoidWalls) returnVector += avoidWalls()*10
    }
    if(room.wander) returnVector += wander()*0.1
    if(room.separation) returnVector += separation()*5
    returnVector
  }
  
  // Passing through the door
  private def passThrough() : Vector2D = {
    Vector2D(0, room.door.y - human.position.y)
  }
  
  // Seeking the door
  private def seek() : Vector2D = {
    val desiredPosition = room.door - human.position
    //if(human.position.x < 0 || desiredPosition.length < room.doorWidth/2) return Vector2D(-1, 0)
    val desiredVelocity = desiredPosition.truncate(Human.MaxSpeed)
    desiredVelocity - human.velocity
  }
  
  // Avoid walls
  private def avoidWalls() : Vector2D = {
    val posX = human.position.x
    val posY = human.position.y
    //if(posX < 0 || (room.door - human.position).length < room.doorWidth/2) return Vector2D(0, 0)
    
    var x, y = 0.0
    // Nearest 2 walls create force away from them.
    val rightX = room.width - posX
    if(posX < 50) x = posX / math.pow(posX, 2) 
    else if(rightX < 100) x = -(rightX / math.pow(rightX, 2))
    
    val bottomY = room.height - posY
    if(posY < 50) y = posY / math.pow(posY, 2)
    else if (bottomY < 100) y = -(bottomY / math.pow(bottomY, 2))
    
    Vector2D(x,y)
  }
   
  // Avoid other people
  private def separation() : Vector2D = {
    // Visibility is 240 degrees so PI*2/3 rad, but it needs to be divided by 2 as the velosity is in the middle
    val visible = room.humans.filter(other => ((other.position-human.position) angle human.velocity) < ( 2.0*math.Pi/3.0))
    val neighborhood = visible.map(other => (human.position - other.position)).filter( x => x.length < 30 && x.length > 0.01 )
    neighborhood.map( distance => distance / pow(distance.length, 2) ).fold(Vector2D(0, 0)) { _ + _ }
  }
  
  // A wandering method where wandering vector is constrained to a circle with chosen radius.
  private def wander() : Vector2D = {
    val radius = 5
    val rate = 0.3
    val center = human.heading*radius
    val rand = new Random()
        
    val addedVector = new Vector2D(rand.nextDouble()-rand.nextDouble(), rand.nextDouble()-rand.nextDouble())*rate
    _wanderDirection += addedVector
    _wanderDirection = center + (_wanderDirection-center).normalize*radius
    _wanderDirection
  }
}
