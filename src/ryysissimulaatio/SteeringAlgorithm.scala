package ryysissimulaatio

import scala.util.Random
import scala.math.pow

class SteeringAlgorithm( val human: Human, val room: Room) {
  def getAcceleration () : Vector2D = {
    var returnVector = new Vector2D(0, 0)
    returnVector += seek()*1
    //returnVector += avoid()*1
    //returnVector += wander()*1
    returnVector += separation()*3
    returnVector
  }
  
  private def seek() : Vector2D = {
    val desiredPosition = room.door - human.position
    if(human.position.x < 0 || desiredPosition.length < room.doorWidth/2) return Vector2D(-1, 0)
    val desiredVelocity = desiredPosition.truncate(Human.MaxSpeed)
    desiredVelocity - human.velocity
  }
  
  private def avoid() : Vector2D = {
    var x, y = 0
    Vector2D(x,y)
  }
   
  private def separation() : Vector2D = {
    // Visibility is 240 degrees so PI*2/3 rad, but it needs to be divided by 2 as the velosity is in the middle
    val visible = room.humans.filter(other => ((other.position-human.position) angle human.velocity) < ( 2.0*math.Pi/3.0))
    val neighborhood = visible.map(other => (human.position - other.position)).filter( x => x.length < 100 && x.length > 0.01 )
    neighborhood.map( distance => distance / pow(distance.length, 2) ).fold(Vector2D(0, 0)) { _ + _ }
  }
  
  private def wander() : Vector2D = {
    val rand = new Random()
    Vector2D(rand.nextDouble()-rand.nextDouble(), rand.nextDouble()-rand.nextDouble()).normalize
  }
}
