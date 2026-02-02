//Adrian Klimaševski

PImage img;
PImage[] imgArr;
int imgW;
int imgH;
int imgCol;
int imgRow;
int tileSize = 16;
int mousePosX, mousePosY;
int imageNumber;

int saveButtonX = 25, saveButtonY = 345, saveButtonH = 60, saveButtonW = 200;
int destroyButtonX = 560, destroyButtonY = 345, destroyButtonH = 60, destroyButtonW = 227;
int voidButtonX = 285, voidButtonY = 345, voidButtonH = 60, voidButtonW = 100;
int coinButtonX = 400, coinButtonY = 345, coinButtonH = 60, coinButtonW = 100;

MapButtons mapBtn_1, mapBtn_2, mapBtn_3, mapBtn_4, mapBtn_5;
int mapButtonX = 25, mapButtonY = 570;

String mapName = "data/maps/map_1.csv";
int mapNum = 1;

//Grid
int gridLenght = 50;
int gridHeight = 20;
int gridblocksize = 16;
Grid[][] boxes = new Grid[gridLenght][gridHeight];

int[][] map;

PImage coinImage;

PrintWriter output;

//MANO

void setup() {
  size (1000,630);
  fill(255);
  
  
  img = loadImage("data/assets/coin.png");
  imgW = img.width;
  imgH = img.height;
  imgCol = imgW / tileSize;
  imgRow = imgH / tileSize;
  coinImage = img.get(2 * tileSize, 0 * tileSize, tileSize, tileSize);
  
  
  img = loadImage("data/assets/environment.png");
  imgW = img.width;
  imgH = img.height;
  imgCol = imgW / tileSize;
  imgRow = imgH / tileSize;
  
  imgArr = new PImage[imgCol * imgRow];
  createArr();
  
  
  map = new int[gridLenght][gridHeight];
  readAndSaveMapFileContent(mapName);
  
  for(int i = 0; i < gridLenght; ++i) {
    for(int j = 0; j < gridHeight; ++j) {
        boxes[i][j] = new Grid(i*gridblocksize, j*gridblocksize, map[i][j]);
    }
  }
  
  mapBtn_1 = new MapButtons();
  mapBtn_1.makeButton(mapButtonX, mapButtonY, 1);
  
  mapBtn_2 = new MapButtons();
  mapBtn_2.makeButton(mapButtonX+50, mapButtonY, 2);
  
  mapBtn_3 = new MapButtons();
  mapBtn_3.makeButton(mapButtonX+100, mapButtonY, 3);
  
  mapBtn_4 = new MapButtons();
  mapBtn_4.makeButton(mapButtonX+150, mapButtonY, 4);
  
  mapBtn_5 = new MapButtons();  
  mapBtn_5.makeButton(mapButtonX+200, mapButtonY, 5);  
  
  
}

void draw() {
  background(#171717);
  
   for (int i = 0; i < gridLenght; ++i){
     for (int j = 0; j < gridHeight; ++j) {
       boxes[i][j].display();
     }
   }
     
  image(img, width-(imgW/2), 0, imgW / 2, imgH / 2);
  
  if(imageNumber == 0){
    image(imgArr[200], mouseX, mouseY, 50, 50);
  } else if (imageNumber == 1873) {
    image(coinImage, mouseX, mouseY, 50, 50);
  } else {
    image(imgArr[imageNumber], mouseX, mouseY, 50, 50);
  }
   
  fill(#171717); 
  rect(saveButtonX, saveButtonY, saveButtonW, saveButtonH);
   
  fill(#0DD300);
  textSize(30);
  text("Save map", saveButtonX+40, saveButtonY+40);
  
  
  fill(#171717); 
  rect(voidButtonX, voidButtonY, voidButtonW, voidButtonH);
  fill(#4D4C4C);
  text("void", voidButtonX+23, voidButtonY+40);
  
  fill(#171717); 
  rect(destroyButtonX, destroyButtonY, destroyButtonW, destroyButtonH);
  fill(255,0,0);
  textSize(30);
  text("Destroy & Save", destroyButtonX+20, destroyButtonY+40);
  
  
  fill(#4D4C4C);
  text("Select map:", mapButtonX, mapButtonY-17);
  
  
  fill(#171717); 
  rect(coinButtonX, coinButtonY, coinButtonW, coinButtonH);
  fill(#FCE10D);
  textSize(30);
  text("coin", coinButtonX+23, coinButtonY+40);
  
  
  mapBtn_1.diplayButton();
  mapBtn_2.diplayButton();
  mapBtn_3.diplayButton();
  mapBtn_4.diplayButton();
  mapBtn_5.diplayButton();
  
}

void mousePressed(){
  
  if(mouseX >= width - (imgW/2) && mouseX <= width && mouseY >= 0 && mouseY <= (imgH/2)){
    mousePosX = int((mouseX - width + (imgW/2)) /(tileSize/2));
    mousePosY = int(mouseY/(tileSize/2));
    
    imageNumber  = mousePosY * imgCol + mousePosX;
  }
  
  if (mouseX >= saveButtonX && mouseX <= saveButtonX+saveButtonW && mouseY >= saveButtonY && mouseY <= saveButtonY+saveButtonH){
  output = createWriter(mapName); 
  for(int row = 0; row < gridHeight; row++){
    for(int col = 0; col < gridLenght; col++){
     // System.out.print(map[col][row]+",");
      output.print(map[col][row]);
      if(col<gridLenght-1)
        output.print(",");
    }
     if(row<gridHeight-1)
        output.println();
  }
    output.flush();
    output.close();
    System.out.println("Map saved.");
  }
  
  if (mouseX >= voidButtonX && mouseX <= voidButtonX+voidButtonW && mouseY >= voidButtonY && mouseY <= voidButtonY+voidButtonH){
    imageNumber = 0;
  }
  
  if (mouseX >= coinButtonX && mouseX <= coinButtonX+coinButtonW && mouseY >= coinButtonY && mouseY <= coinButtonY+coinButtonH){
    imageNumber = 1873;
  }
  
  
  if (mouseX >= destroyButtonX && mouseX <= destroyButtonX+destroyButtonW && mouseY >= destroyButtonY && mouseY <= destroyButtonY+destroyButtonH){
  output = createWriter(mapName); 
  for(int row = 0; row < gridHeight; row++){
    for(int col = 0; col < gridLenght; col++){
     // System.out.print(map[col][row]+",");
      output.print("0");
      if(col<gridLenght-1)
        output.print(",");
    }
     if(row<gridHeight-1)
        output.println();
  }
    output.flush();
    output.close();
    
    setup();
    System.out.println("Map destroyed."); 
  }
  
  mapBtn_1.checkMapButton();
  mapBtn_2.checkMapButton();
  mapBtn_3.checkMapButton();
  mapBtn_4.checkMapButton();
  mapBtn_5.checkMapButton();
  
  
  for (int i = 0; i < gridLenght; ++i){
    for (int j = 0; j < gridHeight; ++j) { 
        boxes[i][j]. clicable();  
    }
  } 
}

void mouseDragged() {
  for (int i = 0; i < gridLenght; ++i){
    for (int j = 0; j < gridHeight; ++j) { 
        boxes[i][j]. clicable();  
    }
  } 
}
 //<>//

void createArr(){
  PImage temp;
  for(int j = 0; j < imgRow; ++j){
    for(int i = 0; i < imgCol; ++i){
      temp = img.get(i * tileSize, j * tileSize, tileSize, tileSize);
      imgArr[j * imgCol + i] = temp;
    
    }
  }
}


void readAndSaveMapFileContent(String filename){
  String[] lines = loadStrings(filename);
  for(int row = 0; row < lines.length; row++){
    String[] values = split(lines[row], ",");
    for(int col = 0; col < values.length; col++){
      
      if (values[col] == "c")
        map[col][row] = 1871;
      else 
        map[col][row] = Integer.parseInt(values[col]);
    }
  }
}

/*
void printMap(){
  for(int row = 0; row < gridHeight; row++){
    for(int col = 0; col < gridLenght; col++){
      System.out.print(map[col][row]);
      if(col<gridLenght-1)
        System.out.print(",");
    }
    System.out.println();
  }
  System.out.println();
}
*/
