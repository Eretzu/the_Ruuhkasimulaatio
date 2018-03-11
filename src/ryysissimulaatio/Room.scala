package ryysissimulaatio

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Room(val width: Int, val height: Int, humanAmount: Int) {
  private val rand = new Random
  
  // Default Human.radius is currently 8, but it could be randomized with mass
  private var _humans = ArrayBuffer.fill[Human](humanAmount)(new Human(10+rand.nextInt(width-20), 10+rand.nextInt(height-20), this, 8))
    
  def runRound() = _humans.foreach( _.move() )
  
  def door: Vector2D = Vector2D(0, height/2)
  
  def humans = _humans.toVector
  
  //def delete(human: Human) = _humans -= human
}