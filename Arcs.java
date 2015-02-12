
public class Arcs{
  Vector start, end, tangent;
  Vector midpoint, center;
  double Radius_square;
  public Arcs(Vector start,Vector end, Vector tangent){
    this.start=start;
    this.end=end; 
    this.tangent=tangent;
    midpoint=new Vector((start.x+end.x)/2, (start.y+end.y)/2);
    Line perpendicular_mid = new Line(midpoint,midpoint.plus(start.minus(end).Rotate_by_90()));
    Musem obj = new Musem();
    center=obj.Intersection_point(perpendicular_mid, start, start.plus(tangent.Rotate_by_90()));
    Radius_square=center.minus(start).magnitude_square();
  } 
  
}
