package ryysissimulaatio

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Room(val width: Int, val height: Int, humanAmount: Int) {
  require(width >= 200 && height >= 200)
  
  private val rand = new Random
  
  private var _humans = ArrayBuffer.fill[Human](humanAmount)(new Human(10+rand.nextInt(width-20), 10+rand.nextInt(height-20), this))
  
  // Variables for checkBoxes
  private var _seek = true
  private var _wander = true
  private var _wallAvoidance = true
  private var _separation = true
  
  def seek = _seek
  def wander = _wander
  def avoidWalls = _wallAvoidance
  def separation = _separation
  
  def toggleSeek() = _seek = !_seek
  def toggleWander() = _wander = !_wander
  def toggleWallAvoidance() = _wallAvoidance = !_wallAvoidance
  def toggleSeparation() = _separation = !_separation
  
  def runRound() = _humans.foreach( _.move() )
  
  // Door coordinates and width
  val door = Vector2D(0, height.toDouble/2)
  val doorWidth = 40
    
  def humans = _humans.toVector
}