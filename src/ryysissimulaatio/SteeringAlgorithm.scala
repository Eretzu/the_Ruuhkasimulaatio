package ryysissimulaatio

import scala.util.Random
import scala.math.pow

class SteeringAlgorithm( val human: Human, val room: Room) {
  def getAcceleration () : Vector2D = {
    var returnVector = new Vector2D(0, 0)
    if((room.door - human.position).length <= room.doorWidth/2) {
      returnVector += passThrough()*0.05
      returnVector += Vector2D(-1, 0)
    } else if (human.position.x < 0) {
      returnVector += Vector2D(-1, 0)
    } else {
      returnVector += seek()*1
      returnVector += avoidWalls()*5
    }
    //returnVector += wander()*1
    returnVector += separation()*5
    returnVector
  }
  
  private def passThrough() : Vector2D = {
    Vector2D(0, room.door.y - human.position.y)
  }
  
  private def seek() : Vector2D = {
    val desiredPosition = room.door - human.position
    //if(human.position.x < 0 || desiredPosition.length < room.doorWidth/2) return Vector2D(-1, 0)
    val desiredVelocity = desiredPosition.truncate(Human.MaxSpeed)
    desiredVelocity - human.velocity
  }
  
  private def avoidWalls() : Vector2D = {
    val posX = human.position.x
    val posY = human.position.y
    //if(posX < 0 || (room.door - human.position).length < room.doorWidth/2) return Vector2D(0, 0)
    
    var x, y = 0.0
    // Nearest 2 walls create force away from them.
    val rightX = room.width - posX
    if(posX < 200) x = posX / math.pow(posX, 2) 
    else if(rightX < 200) x = -(rightX / math.pow(rightX, 2))
    
    val bottomY = room.height - posY
    if(posY < 200) y = posY / math.pow(posY, 2)
    else if (bottomY < 200) y = -(bottomY / math.pow(bottomY, 2))
    
    Vector2D(x,y)
  }
   
  private def separation() : Vector2D = {
    // Visibility is 240 degrees so PI*2/3 rad, but it needs to be divided by 2 as the velosity is in the middle
    val visible = room.humans.filter(other => ((other.position-human.position) angle human.velocity) < ( 2.0*math.Pi/3.0))
    val neighborhood = visible.map(other => (human.position - other.position)).filter( x => x.length < 100 && x.length > 0.01 )
    neighborhood.map( distance => distance / pow(distance.length, 2) ).fold(Vector2D(0, 0)) { _ + _ }
  }
  
  // To be updated to a more nuanced variation.
  private def wander() : Vector2D = {
    val rand = new Random()
    Vector2D(rand.nextDouble()-rand.nextDouble(), rand.nextDouble()-rand.nextDouble()).normalize
  }
}
