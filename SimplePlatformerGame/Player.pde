public class Player extends AnimatedSprite{
  
  
  int lives;
  boolean onPlatform, inPlace;
  PImage[] standIdleRight;
  PImage[] standLeft;
  PImage[] standRight;
  PImage[] jumpLeft;
  PImage[] jumpRight;
  public Player(PImage img, float scale) {
    super(img, scale);
    lives = 3;
    direction = RIGHT_FACING;
    onPlatform = false;
    inPlace = true;
    
        

    
    standIdleRight = new PImage[2];
    standIdleRight[0] = playerArr[63];
    standIdleRight[1] = playerArr[64];

    
    standLeft = new PImage[1];
    standLeft[0] = mirrorImage(playerArr[63]);
    
    standRight = new PImage[1];
    standRight[0] = playerArr[63];
    
    jumpLeft = new PImage[1];
    jumpLeft[0] = mirrorImage(playerArr[78]);
    
    jumpRight = new PImage[1];
    jumpRight[0] = playerArr[78];
    
    moveLeft = new PImage[4];
    moveLeft[0] = mirrorImage(playerArr[56]);
    moveLeft[1] = mirrorImage(playerArr[57]);
    moveLeft[2] = mirrorImage(playerArr[58]);
    moveLeft[3] = mirrorImage(playerArr[59]);
    
    moveRight = new PImage[4];
    moveRight[0] = playerArr[56];
    moveRight[1] = playerArr[57];
    moveRight[2] = playerArr[58];
    moveRight[3] = playerArr[59];
    currentImages = standRight;
  }
  
  @Override
  public void updateAnimation(){
    onPlatform = isOnPlatforms(this, platforms);
    inPlace = change_x == 0 && change_y == 0;
    super.updateAnimation();
  }
  
  @Override
  public void selectDirection(){
    if(change_x > 0){
      direction = RIGHT_FACING;
    }
    else if(change_x < 0){
      direction = LEFT_FACING;
    }
    //TO CHECK
    else{
      direction = NEUTRAL_FACING;
    }
  }
  
  @Override
  public void selectCurrentImages(){
    if(direction == RIGHT_FACING){
      if(inPlace){
        currentImages = standRight;
      }
      else if(!onPlatform){
        currentImages = jumpRight;
      } else {
        currentImages = moveRight;
      }
    }
 
    if(direction == LEFT_FACING){
      if(inPlace){
        currentImages = standLeft;
      }
      else if(!onPlatform){
        currentImages = jumpLeft;
      } else {
        currentImages = moveLeft;
      }
    }

    if(direction == NEUTRAL_FACING){
      if(inPlace){
        currentImages = standIdleRight;
      }
      else if(!onPlatform){
        currentImages = jumpRight;
      } else {
        currentImages = standIdleRight;
      }
  
  }
/*
     if (direction != LEFT_FACING && direction != RIGHT_FACING) {
        currentImages = standRight; // Or any other default behavior you prefer
    }  
 */   
  }
}
