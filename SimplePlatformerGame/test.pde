//Adrian KLimaševski

final static float MOVE_SPEED = 5;
final static float SPRITE_SCALE = 1.8;
final static float SPRITE_SIZE = 28;
final static float GRAVITY = 0.7;
final static float JUMP_SPEED = 14;

final static float RIGHT_MARGIN = 400;
final static float LEFT_MARGIN = 60;
final static float VERTICAL_MARGIN = 40;

final static int NEUTRAL_FACING = 0;
final static int RIGHT_FACING = 1;
final static int LEFT_FACING = 2;


final static float WIDTH = SPRITE_SIZE * 50;
final static float HEIGHT = SPRITE_SIZE * 20;
final static float GROUND_LEVEL = HEIGHT - SPRITE_SIZE;

int tileSize = 16;

//Sprite p;
Sprite c;
PImage dirt, stone, sand, gold, spider, p;
ArrayList<Sprite> platforms;
ArrayList<Sprite> coins;

Player player;
Enemy enemy;

int numCoins;
int lives;
boolean isGameOver;

float view_x = 0;
float view_y = 0;

PImage[] coinArr;
PImage[] playerArr;
PImage[] environmentArr;
PImage[] enemyArr;


String mapName = "editor/data/Maps/map_1.csv";
int mapNum = 1;

void setup() {
  
  size(800, 561);
  imageMode(CENTER);
  
  lives = 0;
  numCoins = 0;
  isGameOver = false;

  playerArr = createImageArray("editor/data/assets/player.png");
  p = playerArr[63];
  player = new Player(p, 2.5);
  player.center_x = 117;
  player.center_y = 430;

  platforms = new ArrayList<Sprite>();
  
  coinArr = createImageArray("editor/data/assets/coin.png");
  coins = new ArrayList<Sprite>();
  gold = coinArr[2]; 
  
  spider = loadImage("data/cat.png");
  dirt = loadImage("data/dirt.jpg");
  stone = loadImage("data/stone.png");
  sand = loadImage("data/sand.jpeg");
  
  
  //createPlatforms(mapName);
  
  
  environmentArr = createImageArray("editor/data/assets/environment.png");
  createPlatforms(mapName);
  
  view_x = 0;
  view_y = 0;
  
}

void draw() {
    background(#171717);
  scroll();
  
  displayAll();
  
  if(!isGameOver){
   updateAll();
    collectCoins();
    checkDeath();
  }
   //<>//
}


void displayAll(){
 for(Sprite s: platforms)
    s.display();
 

  player.display();
  
  
  //TO FIX
  //enemy.display();
    
   fill(#4D4C4C); 
   textSize(40);
   text(numCoins + "  coins", view_x + 25, view_y + 50);
   text(player.lives + "  lives", view_x + 25, view_y + 100);  
   
   if(isGameOver){
     fill(#171717);
     rect(view_x, view_y, 800, 561);
     
     textSize(40);
     fill(#B2B2B2); 
     text("GAME OVER", view_x + width/2 - 100, view_y + height/2-50);
     textSize(30);
     if(player.lives <= 0){
       fill(255,0,0);
       text("You lost.", view_x + width/2 - 52, view_y + height/2 - 10);
     }else{
       fill(#0DD300); 
       text("You won!", view_x + width/2 - 60, view_y + height/2 - 15);
     }
        
     textSize(23);
     fill(#4D4C4C); 
     text("Press space to restart.", view_x + width/2 - 105, view_y + height/2 + 80);
   }
}

void updateAll(){
  player.updateAnimation();
  resolvePlatformCollisions(player, platforms);
  
  //TO FIX
  //enemy.update();
  //enemy.updateAnimation();
  
  for(Sprite coin : coins) {
  coin.display();
  if(coin instanceof AnimatedSprite) {
    ((AnimatedSprite)coin).updateAnimation();
  }
}
  
  collectCoins();
  checkDeath();
}

void checkDeath(){
  //TO FIX
  //boolean collideEnemy = checkCollision(player, enemy);
  boolean fallOffCliff = player.getBottom() > GROUND_LEVEL;
  if(/*collideEnemy ||*/ fallOffCliff){
    player.lives--;
    if(player.lives == 0){
      isGameOver = true;
    } else {
      player.center_x = 117;
      player.setBottom(GROUND_LEVEL);
    }
  }
}


void collectCoins(){
  ArrayList<Sprite> coin_list = checkCollisionList(player, coins);
  if(coin_list.size() > 0){
    for(Sprite coin: coin_list){
      numCoins++;
      coins.remove(coin);
    }
  }
  
  if(coins.size() == 0){
    isGameOver = true;
  }
}

void scroll() {
  float right_bounadry = view_x + width - RIGHT_MARGIN;
  if(player.getRight() > right_bounadry){
    view_x += player.getRight() - right_bounadry;
  }
  
  float left_boundary = view_x + LEFT_MARGIN;
  if(player.getLeft() < left_boundary){
    view_x -= left_boundary - player.getLeft();
  }
  
  float bottom_boundary = view_y + height - VERTICAL_MARGIN;
  if(player.getBottom() > bottom_boundary){
    view_y += player.getBottom() - bottom_boundary;
  }
  float top_boundary = view_y + VERTICAL_MARGIN;
  if (player.getTop() < top_boundary){
    view_y -= top_boundary - player.getTop();  
  }
  translate(-view_x, -view_y);
  
}

/*
public boolean isOnPlatforms(Sprite s, ArrayList<Sprite> walls){
  s.center_y += 5;
  ArrayList<Sprite> col_list = checkCollisionList(s, walls);
  s.center_y -= 5;
  if(col_list.size() > 0){
    return true;
  }
  else {
    return false;
  }
}
*/

public boolean isOnPlatforms(Sprite s, ArrayList<Sprite> walls){
  float playerBottom = s.getBottom(); // Get the player's bottom position
  s.setBottom(playerBottom + 5); // Temporarily move the player's bottom down by 5 units
  ArrayList<Sprite> col_list = checkCollisionList(s, walls); // Check for collisions
  s.setBottom(playerBottom); // Restore the player's original position
  return !col_list.isEmpty(); // Return true if there are collisions, indicating the player is on a platform
}




public void resolvePlatformCollisions(Sprite s, ArrayList<Sprite> walls){
 
  if (isOnPlatforms(s, walls) == false)
    s.change_y += GRAVITY;
  
  s.center_y += s.change_y;
      
  ArrayList<Sprite> col_list = checkCollisionList(s, walls);
  
  if(col_list.size() > 0){
    Sprite collided = col_list.get(0);
    if(s.change_y > 0){
      s.setBottom(collided.getTop());
      
    } else if(s.change_y < 0){
      s.setTop(collided.getBottom());
    }
  } //else {

  //}
  
  s.center_x += s.change_x;
  col_list = checkCollisionList(s, walls);
  
  if(col_list.size() > 0){
    Sprite collided = col_list.get(0);
    if(s.change_x > 0){
      s.setRight(collided.getLeft());
      
    } else if(s.change_x < 0){
      s.setLeft(collided.getRight());
    }
  }
  
  
}

boolean checkCollision(Sprite s1, Sprite s2) {
  boolean noXOverlap = s1.getRight() <= s2.getLeft() || s1.getLeft() >= s2.getRight();
  boolean noYOverlap = s1.getBottom() <= s2.getTop() || s1.getTop() >= s2.getBottom();
  if (noXOverlap || noYOverlap) {
    return false;
  } else {
    return true;
  }
}

public ArrayList<Sprite> checkCollisionList(Sprite s, ArrayList<Sprite> list){
  ArrayList<Sprite> collision_list = new ArrayList<Sprite>();
  for(Sprite p: list){
    if(checkCollision(s, p))
      collision_list.add(p);
  }
  return collision_list;
}

void createPlatforms(String filename){
  String[] lines = loadStrings(filename);
  
  for(int row = 0; row < lines.length; row++){
    String[] values = split(lines[row], ",");
    
    for(int col = 0; col < values.length; col++){
      if(!values[col].equals("0")){       
        if(values[col].equals("1873")){
          Coin c = new Coin(gold, 2.5);      //TO CHANGE
          c.center_x = SPRITE_SIZE/2 + col * SPRITE_SIZE;
          c.center_y = SPRITE_SIZE/2 + row * SPRITE_SIZE;
          coins.add(c);
        } else if(values[col].equals("1874")){
          float bLeft = col * SPRITE_SIZE;
          float bRight = bLeft + 4 * SPRITE_SIZE;
          enemy = new Enemy(spider, 0.03, bLeft, bRight);
          enemy.center_x = SPRITE_SIZE/2 + col * SPRITE_SIZE;
          enemy.center_y = SPRITE_SIZE/2 + row * SPRITE_SIZE;
        } else {
          Sprite s = new Sprite(environmentArr[Integer.valueOf(values[col])], SPRITE_SCALE);
          s.center_x = SPRITE_SIZE/2 + col * SPRITE_SIZE;
          s.center_y = SPRITE_SIZE/2 + row * SPRITE_SIZE;
          platforms.add(s);
        }
      }
    }
  }
}

void keyPressed(){
  if (keyCode == RIGHT){
    player.change_x = MOVE_SPEED;
  }
  else if (keyCode == LEFT){
    player.change_x = -MOVE_SPEED;
  }
  else if (keyCode == UP && isOnPlatforms(player, platforms)) {
    player.change_y = -JUMP_SPEED;
  } 
  else if (isGameOver && key == ' ')
    setup();
  
  else if (key == '1'){ 
    mapName = "editor/data/Maps/map_1.csv";
    setup();
  }
  else if (key == '2'){
    mapName = "editor/data/Maps/map_2.csv";
    setup();
  }
  else if (key == '3'){
    mapName = "editor/data/Maps/map_3.csv";
    setup();
  }
}

void keyReleased(){
  if (keyCode == RIGHT){
    player.change_x = 0;
  }
  else if (keyCode == LEFT){
    player.change_x = 0;
  }
}


PImage[] createImageArray(String imageName) {
  PImage allAssets = loadImage(imageName);
  int imgW = allAssets.width;
  int imgH = allAssets.height;
  int imgCol = imgW / tileSize;
  int imgRow = imgH / tileSize;

  PImage[] array = new PImage[imgCol * imgRow];

  PImage temp;
  for (int j = 0; j < imgRow; ++j) {
    for (int i = 0; i < imgCol; ++i) {
      temp = allAssets.get(i * tileSize, j * tileSize, tileSize, tileSize);
      array[j * imgCol + i] = temp;
    }
  }
  return array;
}


PImage mirrorImage(PImage original) {
  original.loadPixels();
  int len = original.width * original.height;
  PImage mirrored = createImage(original.width, original.height, ARGB); // Use ARGB to preserve alpha channel
  mirrored.loadPixels();
  
  for (int y = 0; y < original.height; y++) {
    for (int x = 0; x < original.width; x++) {
      int srcIndex = y * original.width + x;
      int destIndex = y * original.width + (original.width - 1 - x);
      if (original.pixels[srcIndex] >> 24 == 0) { // Check if the pixel is transparent
        mirrored.pixels[destIndex] = color(0, 0); // Set transparent pixels to black
      } else {
        mirrored.pixels[destIndex] = original.pixels[srcIndex]; // Copy non-transparent pixels as is
      }
    }
  }
  
  mirrored.updatePixels();
  return mirrored;
}
