import java.util.*;
import javax.swing.*;

//实现Runnable接口实现多线程
class SnakeModel implements Runnable{
  GreedSnake gs;
  boolean[][] matrix;
  LinkedList nodeArray = new LinkedList();
  Node food;
  int maxX;
  int maxY;
  int direction = 2;
  boolean running = false;

  int timeInterval = 200;
  double speedChangeRate = 0.75;
  boolean paused = false;
  
  int score = 0;
  int countMove = 0;

  // UP and DOWN should be even
  // RIGHT and LEFT should be odd
  public static final int UP = 2;
  public static final int DOWN = 4;
  public static final int LEFT = 1;
  public static final int RIGHT = 3;

  //构造函数
  public SnakeModel(GreedSnake gs, int maxX, int maxY){
    this.gs = gs;
    this.maxX = maxX;
    this.maxY = maxY;

    // initial matirx
    matrix = new boolean[maxX][];
    for(int i=0; i<maxX; ++i){
      matrix[i] = new boolean[maxY];
      Arrays.fill(matrix[i],false);
    }

    // initial the snake
    int initArrayLength = maxX > 20 ? 10 : maxX/2;
    for(int i = 0; i < initArrayLength; ++i){
      int x = maxX/2+i;
      int y = maxY/2;
      nodeArray.addLast(new Node(x, y));
      matrix[x][y] = true;
    }

    food = createFood();
    matrix[food.x][food.y] = true;
  }

  public void changeDirection(int newDirection){
    if (direction % 2 != newDirection % 2){
      direction = newDirection;
    }
  }

  public boolean moveOn(){
    Node n = (Node)nodeArray.getFirst();
    int x = n.x;
    int y = n.y;

    switch(direction){
      case UP:
        y--;
        break;
      case DOWN:
        y++;
        break;
      case LEFT:
        x--;
        break;
      case RIGHT:
        x++;
        break;
    }

    if ((0 <= x && x < maxX) && (0 <= y && y < maxY)){
      if (matrix[x][y]){
        if(x == food.x && y == food.y){
          nodeArray.addFirst(food);
          
          int scoreGet = (10000 - 200 * countMove) / timeInterval;
          score += scoreGet > 0? scoreGet : 10;
          countMove = 0;
          
          food = createFood();
          matrix[food.x][food.y] = true;
          return true;
        }
        else
          return false;
      }
      else{
        nodeArray.addFirst(new Node(x,y));
        matrix[x][y] = true;
        n = (Node)nodeArray.removeLast();
        matrix[n.x][n.y] = false;
        countMove++;
        return true;
      }
    }
    return false;
  }

  public void run(){
    running = true;
    while (running){
      try{
        Thread.sleep(timeInterval);
      }
      catch(Exception e){
        break;
      }

      if(!paused){
        if (moveOn()){
          gs.repaint();
        }
        else{
          JOptionPane.showMessageDialog(
              null,
              "you failed",
              "Game Over",
              JOptionPane.INFORMATION_MESSAGE);
          break;
        }
      }
    }
    running = false;
  }

  //创建食物
  private Node createFood(){
    int x = 0;
    int y = 0;
    do{
      Random r = new Random();
      x = r.nextInt(maxX);
      y = r.nextInt(maxY);
    }while(matrix[x][y]);

    return new Node(x,y);
  }

  //加速
  public void speedUp(){
    timeInterval *= speedChangeRate;
  }

  //减速
  public void speedDown(){
    timeInterval /= speedChangeRate;
  }

  //暂停
  public void changePauseState(){
    paused = !paused;
  }

  public String toString(){
    String result = "";
    for(int i=0; i<nodeArray.size(); ++i){
      Node n = (Node)nodeArray.get(i);
      result += "[" + n.x + "," + n.y + "]";
    }
    return result;
  }
}//SnackMOdel 类结束 

class Node{
  int x;
  int y;
  Node(int x, int y){
    this.x = x;
    this.y = y;
  }
} 
