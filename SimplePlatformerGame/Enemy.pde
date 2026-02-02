public class Enemy extends AnimatedSprite{
  float boundaryLeft, boundaryRight;
  public Enemy(PImage img, float scale, float bLeft, float bRight){
    super(img, scale);
    moveLeft = new PImage[3];
    moveLeft[0] = loadImage("data/crab.png");
    moveLeft[1] = loadImage("data/spider.png");
    moveLeft[2] = loadImage("data/bat.png");
    
    moveRight = new PImage[3];
    moveRight[0] = loadImage("data/cat.png");
    moveRight[1] = loadImage("data/cat2.png");
    moveRight[2] = loadImage("data/cat.jpg");   
    
    currentImages = moveRight;
    direction = RIGHT_FACING;
    boundaryLeft = bLeft;
    boundaryRight = bRight;
    change_x = 2;
  }
  void update(){
    super.update();
    if(getLeft() <= boundaryLeft){
      setLeft(boundaryLeft);
      change_x *= -1;
    }
    else if(getRight() >= boundaryRight){
      setRight(boundaryRight);
      change_x *= -1;
    }
  }
}
