public class Vector{
		double x;
		double y;
		public Vector(double x, double y){
			this.x = x;
			this.y = y;
		}
		public Vector minus(Vector v){
			return new Vector(x-v.x, y-v.y);
		}
		public double dot(Vector v){
			return (x*v.x+y*v.y);
		}	
		public Vector plus(Vector v){
			return new Vector(x+v.x, y+v.y);
		}
		public Vector Rotate_by_90(){
			return new Vector(y,-x);
		}
		public double magnitude_square(){
			return x*x+y*y;
		}
		public double magnitude(){
			return Math.sqrt(magnitude_square());
		}		
	}