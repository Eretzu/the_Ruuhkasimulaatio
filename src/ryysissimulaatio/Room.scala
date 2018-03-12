package ryysissimulaatio

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Room(val width: Int, val height: Int, humanAmount: Int) {
  require(width >= 5 && height >= 5)
  
  private val rand = new Random
  
  private var _humans = ArrayBuffer.fill[Human](humanAmount)(new Human(0.5+rand.nextInt(width-2)+rand.nextDouble(), 
                                                                       0.5+rand.nextInt(height-2)+rand.nextDouble(), this))
    
  def runRound() = _humans.foreach( _.move() )
  
  // Door coordinates and width
  val door = Vector2D(0, height.toDouble/2)
  val doorWidth = 40
    
  def humans = _humans.toVector
}