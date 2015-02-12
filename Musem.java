import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;

public class Musem {
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		FileInputStream f = null;
		try{
			String inputfile = args[1]; 
			f = new FileInputStream(inputfile);
			BufferedReader br = new BufferedReader(new InputStreamReader(f));
			String line;
			String[] substrings;
			int num_of_lines =0;
			int m=0; // number of wall descriptions;
			double x_cor_starting =0, y_cor_starting=0;
			double x1=0, y1=0,x2=0,y2=0; //X and Y Coordinates of a point;
			double xt=0,yt=0; // dx and dy of a tangent;
			ArrayList<Line> Lines = new ArrayList<Line>();
			ArrayList<Arcs> Arcs = new  ArrayList<Arcs>();
			
			boolean draw_line = false;
			int num_of_wallsets =0, num_of_arts = 0, num_of_guards =0;
			while ((line = br.readLine()) != null) {
				num_of_lines++;
				substrings = line.split(" ");
					num_of_wallsets =  Integer.parseInt(substrings[0]);
					num_of_arts =  Integer.parseInt(substrings[1]);
					num_of_guards =  Integer.parseInt(substrings[2]);	
				
				int[] Arts_Level = new int[num_of_arts];
				int[] Guards_Level = new int[num_of_guards];
				Vector[] Arts_Pos = new Vector[num_of_arts];
				Vector[] Guards_Pos = new Vector[num_of_guards];
				
				for (int i=0;i<num_of_wallsets;i++){
					line = br.readLine();
					m = Integer.parseInt(line.split(" ")[0]);
					for (int index =0;index<m;index++){
						line = br.readLine();
						if (index==0){
							if (line.split(" ").length == 3 && line.contains("s")){					
								x1 = Double.parseDouble(line.split(" ")[0]);
								y1 = Double.parseDouble(line.split(" ")[1]);
								x_cor_starting = x1;
								y_cor_starting = y1;
								draw_line = true;
							}
							else
							{
								x1 = Double.parseDouble(line.split(" ")[0]);
								y1 = Double.parseDouble(line.split(" ")[1]);
								x_cor_starting = x1;
								y_cor_starting = y1;
								xt = Double.parseDouble(line.split(" ")[3]);
								yt = Double.parseDouble(line.split(" ")[4]);
								draw_line = false;
							}
							}
						else
						{
							if (line.split(" ").length == 3 && line.contains("s")){					
								x2 = Double.parseDouble(line.split(" ")[0]);
								y2 = Double.parseDouble(line.split(" ")[1]);
								if (draw_line){
									Lines.add(new Line(new Vector(x1,y1), new Vector(x2,y2)));
								}
								else{
									Arcs.add(new Arcs(new Vector(x1,y1), new Vector(x2,y2), new Vector(xt,yt)));
								}
								x1=x2;
								y1=y2;
								draw_line = true;
							}
							else
							{
								x2 = Double.parseDouble(line.split(" ")[0]);
								y2 = Double.parseDouble(line.split(" ")[1]);
								if (draw_line){
									Lines.add(new Line(new Vector(x1,y1), new Vector(x2,y2)));
								}
								else{
									Arcs.add(new Arcs(new Vector(x1,y1), new Vector(x2,y2), new Vector(xt,yt)));
								}
								x1=x2;
								y1=y2;
								draw_line=false;
								xt = Double.parseDouble(line.split(" ")[3]);
								yt = Double.parseDouble(line.split(" ")[4]); 						
							}
						} 					
					}	
					if (draw_line)
					{
						Lines.add(new Line(new Vector(x2,y2), new Vector(x_cor_starting,y_cor_starting)));
					}
					else
					{
						Arcs.add(new Arcs(new Vector(x2,y2), new Vector(x_cor_starting,y_cor_starting), new Vector(xt,yt)));
					} 
				}
					
				
				for (int index_of_arts=0;index_of_arts<num_of_arts;index_of_arts++){
					line = br.readLine();
					Arts_Pos[index_of_arts]=new Vector(Double.parseDouble(line.split(" ")[0]),Double.parseDouble(line.split(" ")[1]));
					Arts_Level[index_of_arts] = Integer.parseInt(line.split(" ")[2]);
				}
				
                for (int index_of_guards=0;index_of_guards<num_of_guards;index_of_guards++){
                	line = br.readLine();
					Guards_Pos[index_of_guards]=new Vector(Double.parseDouble(line.split(" ")[0]),Double.parseDouble(line.split(" ")[1]));
					Guards_Level[index_of_guards] = Integer.parseInt(line.split(" ")[2]);
				}
                StringBuilder sb = new StringBuilder();
                String content = null;                
    			File file = new File(args[2]);     
    			// if file doesn't exists, then create it
    			if (!file.exists()) {
    				file.createNewFile();
    			}
    			FileWriter fw = new FileWriter(file.getAbsoluteFile());
    			BufferedWriter bw = new BufferedWriter(fw);
    			for(int g_index=1;g_index<num_of_guards+1;g_index++){
    			//	System.out.print("\n" + g_index + "\n" + Guards_Level[g_index-1] + "\n\n");
    				sb.append(g_index);
    				sb.append(" ");
    				sb.append(Guards_Level[g_index-1]);
    				sb.append(" ");
    				content=sb.toString();
    			}
    			bw.write(content);
    			bw.newLine();
    			sb.setLength(0);
    			
    			content=null;
    			for(int g_index=0;g_index<num_of_guards;g_index++){
    				for(int a_index=0;a_index<num_of_arts;a_index++){
    					if(Line_of_Sight(Guards_Pos[g_index],Arts_Pos[a_index],Lines,Arcs)){
    						sb.append(num_of_guards+a_index+1);
    	    				sb.append(" ");
    	    				sb.append(1);
    	    				sb.append(" ");
    	    				content=sb.toString();
    						System.out.println("\nGuard " + (g_index+1) + "  is watching " + (a_index+1));
    					}
    				} 
    				bw.write(content);
                    bw.newLine();
                    sb.setLength(0);
                    content=null;
    			}
                sb.setLength(0);
                content=null;
                
                for(int a_index=1;a_index<num_of_arts+1;a_index++){
    				//System.out.print("\n" + a_index + "\n" + Arts_Level[a_index-1] + "\n\n");
    				sb.append(num_of_arts+num_of_guards+1);
    				sb.append(" ");
    				sb.append(Arts_Level[a_index-1]);
    				sb.append(" ");
    				content=sb.toString();
    				bw.write(content);
        			bw.newLine();
        			sb.setLength(0);
        			content=null;
    			}
    			bw.write(" ");
                bw.close();
             
                int sum=0;
                for(int i=0;i<Arts_Level.length;i++){
                	sum+=Arts_Level[i];
                }
                 FFS.file_name(args[2],sum,num_of_arts,num_of_guards,"output3.txt",0);
                 FFS.main(args);
                //add the link to ffs & bfs
			}// end of while loop

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean Line_of_Sight(Vector Guard, Vector Art,ArrayList<Line> lines,ArrayList<Arcs> Arcs) {
		for (Line l: lines){
			if(Test_Intersect(l, Guard, Art)){
				return false;
			}
		}
		for (Arcs Arc: Arcs){
			if(Find_Arc_Intersection(Arc, Guard, Art)){
				return false;
			}
		}
		return true; 
	}

	private static boolean Find_Arc_Intersection(Arcs arc, Vector guard, Vector art) {
		double Square_Dist_AB=0, dis_center_closet=0;
		double dx,dy,dt;
		double tempx,tempy;
		Vector Int_Point1,Int_Point2;
		Square_Dist_AB=art.minus(guard).magnitude();
		
		//Direction of the Vector from Guard to Art
		dx=(guard.x-art.x)/Square_Dist_AB;
		dy=(guard.y-art.y)/Square_Dist_AB;
		
		//Compute the value t closet point the line of sight from the center
		double t = dx*(arc.center.x-art.x) + dy*(arc.center.y-art.y); 
		
		//Coordinates of the point closet to center
		double ex,ey;
	
		ex=t*dx+art.x;
		ey=t*dy+art.y;
		
		Vector closest_point=new Vector(ex,ey);
		// compute the euclidean distance from E to C
		dis_center_closet=closest_point.minus(arc.center).magnitude_square();
		if(dis_center_closet<arc.Radius_square){
			dt = Math.sqrt(arc.Radius_square-dis_center_closet);
			tempx=(t-dt)*dx+art.x;
			tempy=(t-dt)*dy+art.y;
			
			Int_Point1=new Vector(tempx,tempy);
			
			tempx=(t+dt)*dx+art.x;
			tempy=(t+dt)*dy+art.y;
			
			Int_Point2=new Vector(tempx,tempy);
		}
		else
		{
			return false;
		}
		
		double rank1 = arc.start.dot(Int_Point1);
        double rank2 = arc.start.dot(Int_Point2);
        double rankEnd = arc.start.dot(arc.end);
        if (Int_Point1.dot(arc.tangent) < 0) rank1 = - rank1 - 2*arc.start.magnitude_square();
        if (Int_Point2.dot(arc.tangent) < 0) rank2 = - rank2 - 2*arc.start.magnitude_square();
        if (arc.end.dot(arc.tangent) < 0) rankEnd = - rankEnd - 2*arc.start.magnitude_square();
        if (Int_Point1.minus(guard).dot(Int_Point1.minus(art)) < 0 && rankEnd < rank1) {
            return true;
        }
        if (Int_Point2.minus(guard).dot(Int_Point2.minus(art)) < 0 && rankEnd < rank2) {
            return true;
        }
		
		return false;
	}
	

	private static boolean Test_Intersect(Line l, Vector guard, Vector art) {
		return (Find_Intersection(l,guard,art));
	}

	
	private static boolean Find_Intersection(Line l, Vector guard, Vector art) {
		double d = ((l.start.x-l.end.x)*(guard.y-art.y))-((l.start.y-l.end.y)*(guard.x-art.x));
		if(d==0){
			return false;
		}
		double x=0;
		double y=0;
		x=((guard.x-art.x)*(l.start.x*l.end.y-l.start.y*l.end.x)-(l.start.x-l.end.x)*(guard.x*art.y-guard.y*art.x))/d;
		y=((guard.y-art.y)*(l.start.x*l.end.y-l.start.y*l.end.x)-(l.start.y-l.end.y)*(guard.x*art.y-guard.y*art.x))/d;
		Vector intPt = new Vector(x,y);
        return (((guard.minus(intPt).dot(art.minus(intPt))) < 0) && (l.start.minus(intPt).dot(l.end.minus(intPt)) < 0));
	}
	
	public Vector Intersection_point(Line l, Vector guard, Vector art) {
		double d = ((l.start.x-l.end.x)*(guard.y-art.y))-((l.start.y-l.end.y)*(guard.x-art.x));
		if(d==0){
			return null;
		}
		double x=0;
		double y=0;
		x=((guard.x-art.x)*(l.start.x*l.end.y-l.start.y*l.end.x)-(l.start.x-l.end.x)*(guard.x*art.y-guard.y*art.x))/d;
		y=((guard.y-art.y)*(l.start.x*l.end.y-l.start.y*l.end.x)-(l.start.y-l.end.y)*(guard.x*art.y-guard.y*art.x))/d;
		return (new Vector(x,y));
	}
}

