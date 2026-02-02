class MapButtons{
  int x, y;
  String number_text;
  int number;
  String buttonMapName;
  final int h = 45;
  final int w = 40;
  
  void makeButton(int x_given, int y_given, int number_given) {
    x = x_given;
    y = y_given;
    switch(number_given){
      case 1:
        number_text = "1";
        number = 1;
        buttonMapName = "data/maps/map_1.csv";
        break;
      case 2:
        number_text = "2";
        number = 2;
        buttonMapName = "data/maps/map_2.csv";
        break;
      case 3:
        number_text = "3";
        number = 3;
        buttonMapName = "data/maps/map_3.csv";
        break;
      case 4:
        number_text = "4";
        number = 4;
        buttonMapName = "data/maps/map_4.csv";
        break;
      case 5:
        number_text = "5";
        number = 5;
        buttonMapName = "data/maps/map_5.csv";
        break;        
    }
  }

  void diplayButton() {
    
    fill(#171717); 
    rect(x, y, w, h);
    textSize(30);
      
    if(mapNum == number)
      fill(255); 
    else{
      fill(#4D4C4C);
    }
    text(number, x+13, y+35);

  }
  
  void checkMapButton() {
    if (mouseX >= x && mouseX <= x+w && mouseY >= y && mouseY <= y+h){
      mapName = buttonMapName;
      mapNum = number;
      setup();
    }
  }
  
} 
