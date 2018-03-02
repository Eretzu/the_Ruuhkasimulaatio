package ryysissimulaatio

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Room(val width: Int, val height: Int, humanAmount: Int) {
  private val rand = new Random
  private var _humans = ArrayBuffer.fill[Human](humanAmount)(new Human(rand.nextInt(width), rand.nextInt(height), this))
  
  def door = height/2
  
  def runRound() = _humans.foreach( _.move() )
  
  def getDoorCoordinates: Vector2D = Vector2D(-30, height/2)
  
  def humans = _humans.toVector
}