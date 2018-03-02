package ryysissimulaatio

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Room(val width: Int, val height: Int, humanAmount: Int) {
  val rand = new Random
  var humans = ArrayBuffer.fill[Human](humanAmount)(new Human(rand.nextInt(width), rand.nextInt(height), this))
  
  def runRound() = humans.foreach( _.move() )
  
  def getDoorCoordinates: Vector2D = Vector2D(0, height/2)
}