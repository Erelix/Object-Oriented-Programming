public class Coin extends AnimatedSprite{
  public Coin(PImage img, float scale){
    super(img, scale);
    standNeutral = new PImage[4];
    
    standNeutral[0] = coinArr[0];
    standNeutral[1] = coinArr[1];
    standNeutral[2] = coinArr[2];
    standNeutral[3] = coinArr[3];
    currentImages = standNeutral;
  }
}
