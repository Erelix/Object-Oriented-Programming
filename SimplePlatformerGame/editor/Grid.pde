class Grid {
  int x;
  int y;
  int savedImageNumber;
  boolean clicked;
  
  
  Grid(int tempx, int tempy, int imageNumber) {
    x = tempx;
    y = tempy;
    savedImageNumber = imageNumber;
  }
  
  
  void display() {
    stroke(#4D4C4C);
    if (clicked || savedImageNumber != 0) {
      
     if(savedImageNumber != 0 && savedImageNumber != 1873){
      image(imgArr[savedImageNumber], x, y);
     }else if(savedImageNumber == 1873){
       image(coinImage, x, y);
     }else{
      fill(#171717);
      rect(x, y, gridblocksize, gridblocksize);
     }
     
    map[x/gridblocksize][y/gridblocksize] = savedImageNumber;
    
    } else {
      fill(#171717);
      rect(x, y, gridblocksize, gridblocksize);
    }
  }
  
  void clicable() {
    if (mouseX > x && mouseX < x + gridblocksize && 
        mouseY > y && mouseY < y + gridblocksize && 
        mousePressed) { // Check if the mouse is pressed
      clicked = true; // Set the state of the grid to clicked
      savedImageNumber = imageNumber;
    }
  }
}
