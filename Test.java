import java.io.*;
import java.util.*;

public class Test {
	public static void main(String args[]) throws IOException
	{
    	String ch = args[0];
    	switch(ch)
        {
		case "-b": 
			bfs.main(args);
			break;
		case "-f":
			FFS.file_name(args[1],0,0,0,"output_FFS.txt",1);
		    FFS.main(args);
		    break;
		case "-m":
			Musem.main(args);
			break;
		default:
			System.out.println("Error in the input");
		break;
		}
    }
}
