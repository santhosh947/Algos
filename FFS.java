

import java.io.*;
import java.util.*;

class Node2{
	public Integer value;
	public Integer Node2name;

	public Node2(Integer Node2) {
		this.Node2name = Node2;
	}

	Map<Integer, Integer> hp = new HashMap<Integer, Integer>();
	public String color;
	public Integer distance;
	public Integer parent;
	public Integer count=0;
}

public class FFS {
	static ArrayList<Integer> objList = new ArrayList<Integer>();
	
	static int pathflow = Integer.MAX_VALUE;
	static int flow = 0;
	static int start_Node2 = 0;
	static int val = 0;
	static int no_of_guards=0, no_of_arts=0,art_lev_summation=0;
	static String file_name=null,file_name_1=null;
	static int c;
	static Object[] newgraph=null;
	static BufferedReader br;
	static BufferedWriter bw;
	StringBuilder sb = new StringBuilder();
	static StringBuilder sbToStorePath = new StringBuilder();
	public static void file_name(String fname,int art_level_summation,int num_of_arts,int num_of_guards,String s1,int count)
	{
		file_name=fname;
		file_name_1=fname;
		c=count;
		if(c==1){
			file_name_1="output3.txt";
			}		
		art_lev_summation=art_level_summation;
		no_of_guards=num_of_guards;
		no_of_arts=num_of_arts;
		try {
			FFS.fname_1(s1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void fname_1(String s) throws IOException
	{
		 bw= new BufferedWriter(new FileWriter(new File(s)));
	}		
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		FileInputStream f = null;
		boolean copy_bool=true;
		Object[] copy_graph=null;
		ArrayList<Node2> Node2array = new ArrayList<Node2>();
		// Reading the input file line by line and differentiating as keys and
		// values in every line
       		try {
			f = new FileInputStream(file_name);
			br = new BufferedReader(new InputStreamReader(f));
			List<List<String>> list = new ArrayList<List<String>>();
			String[] s;
			List<String> l;
			String line;
			while ((line = br.readLine()) != null) {
				String str = line;
				l = new ArrayList<String>();
				s = str.split(" ");
				for (String s1 : s) {
					l.add(s1);
				}
				list.add(l);
			}
			int k = 0;
			for (List<String> s3 : list) {
				Node2 n3 = new Node2(k);
				k++;
				String[] s6 = new String[s3.size()];
				String[] s8 = s3.toArray(s6);
				for (int i = 0; i < s8.length; i++) {
					String key1 = s8[i];
					i++;
					String value1 = s8[i];
					n3.hp.put(Integer.parseInt(key1), Integer.parseInt(value1));
				}
				Node2array.add(n3);
			}
			Object[] graph = Node2array.toArray(); // graph as an array
			if(copy_bool){
				copy_graph=graph;
				copy_bool=false;
			}
			BFS(graph,copy_graph);
			
			long endTime = System.currentTimeMillis();
			System.out.println("\nThe time of execution in MilliSeconds: "
					+ (endTime - startTime));
			br.close();
			bw.close();
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			bw.close();
		}
	}

	public static void BFS(Object[] graph,Object[] copy_graph) {
		// Gives the shortest path
		boolean copy=true;
		//Object[] newgraph=null;
		Node2 length = (Node2) graph[graph.length - 1];
		for (int i = 0; i < graph.length; i++) {
			Node2 Node2 = (Node2) graph[i];
			if (i == 0) {
				Node2.color = "gray";
				Node2.distance = 0;
				Node2.parent = null;
			} else {
				Node2.color = "white";
				Node2.distance = 0;
				Node2.parent = null;
			}
		}
		List<Node2> queue = new ArrayList<Node2>();
		queue.add((Node2) graph[0]);
		while (!queue.isEmpty()) {
			Node2 Node2 = queue.remove(0);
			for (Map.Entry<Integer, Integer> entry : Node2.hp.entrySet()) {
				Node2 neighbor = (Node2) graph[entry.getKey()];
				if (neighbor.color.equalsIgnoreCase("white")) {
					neighbor.color = "gray";
					neighbor.distance += 1;
					neighbor.parent = Node2.Node2name;
					queue.add(neighbor);
				}
			}
			Node2.color = "black";
		}
		if(copy){
			//Object[] newgraph=null;
			newgraph=graph;
			copy=false;
		}
		if (length.color.equalsIgnoreCase("black")) {
			pathflow = Integer.MAX_VALUE;
			getpath(graph, start_Node2, graph.length - 1,copy_graph);
		} else {
			maxflow(flow, copy_graph, start_Node2, graph.length - 1);
		}
	}

	private static void maxflow(int maxflow,Object[] graph, Integer start_Node2,Integer final_Node2) {
		System.out.println("\n Max Flow: " + flow);
		String cond = "YES";
		StringBuilder sb1 = new StringBuilder();
		if(c==0)
		{
		if (flow==art_lev_summation){
			System.out.println("\n" +cond);
			String str4 =  cond;
			sb1.append('\n');
			sb1.append(str4);
		//	printpath(graph, start_Node2, graph.length - 1);
		}
		else
		{
			System.out.print("\n **************   NO *************************");
		}
		}
		try {
			bw.write(sb1.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getpath(Object[] graph, Integer start_Node2,Integer final_Node2, Object[] copy_graph) {
		// Prints the shortest path and finds the min capacity on the edges of a
		// shortest path found
		Node2 sink = (Node2) graph[final_Node2];
		sink.color = "Red";
		Integer parent = sink.parent;
		if (parent != null) {
			Node2 lastNode2 = (Node2) graph[parent];
			Integer value = lastNode2.hp.get(final_Node2);
			pathflow = Math.min(pathflow, value);
			getpath(graph, start_Node2, parent, copy_graph);
		} else {
			flow = flow + pathflow;
			sbToStorePath = printpath(graph, start_Node2, graph.length - 1);
			
			try {
				bw= new BufferedWriter(new FileWriter(new File(file_name_1)));
				
				bw.write(sbToStorePath.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			updategraph(graph, graph.length - 1, copy_graph);
		}
	}
	
	
	
	public static StringBuilder printpath(Object[] graph, Integer start_Node2,Integer final_Node2) {
		Node2 sink = (Node2) graph[final_Node2];
		Integer parent = sink.parent;
		//StringBuilder sb = new StringBuilder();
		if (parent != null) {
			if (parent != start_Node2) {
				if(c==0){
				String str = " -> " + parent;
				sbToStorePath.append(str);
			//	sb.append('\n');
				System.out.print(" -> " + parent);
				printpath(graph, start_Node2, parent);
				//bufferedOutputStream.write(args[2]);
			}
			}
			else if(c==0)
			{
				//	if(c==0)
				//{
				String str1 = " -> " + start_Node2 + "(Source Node)";
				sbToStorePath.append(str1);
				sbToStorePath.append('\n');
				//String[] temp1=str1.split("->");
				//sbToStorePath.append(temp1);
				System.out.println(" -> " + start_Node2 + "(Source Node)");
			}
			else if(c==0) {
			String str2 =  " -> (no source found)" + " ----> " + " No Path Found for the input given";
			sbToStorePath.append(str2);
			sbToStorePath.append('\n');
			System.out.println(" -> (no source found)" + " ----> " + " No Path Found for the input given");
		}
			}
		//System.out.println("Op:" +sb.toString());
		//System.out.println(sb.toString());
		
				/*try {
					
					bw.write(sb.toString());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				return sbToStorePath;
				
	}	
	private static void updategraph(Object[] graph, Integer lastvertex, Object[] copy_graph) {
		// Updates the graph by substracting the min capacity on the forward
		// direction
		// and by adding the new edge with the min capacity in the reverse
		// direction if
		// reverse edge doesn't exist else updates the reverse edge if already
		// exists
		Node2 sink = (Node2) graph[lastvertex];
		Integer parent = sink.parent;
		if (parent != null) {
			Node2 parent1 = (Node2) graph[parent];
			if (sink.color == "Red" && parent1.color == "Red") {
				Integer value = parent1.hp.get(lastvertex);
				if (value != null) {
					int value1 = value - pathflow;
					parent1.hp.put(lastvertex, value1);
				}
				Integer returnflow = sink.hp.get(parent);
				if (returnflow != null) {
					int reverse = -pathflow;
					int value2 = returnflow - (reverse);
					sink.hp.put(parent, value2);
				} else {
					sink.hp.put(parent, pathflow);
				}
				updategraph(graph, parent,copy_graph);
			}
		} else {
			removeedge(graph, graph.length - 1,copy_graph);
		}
	}

	private static void removeedge(Object[] graph, Integer lastvertex, Object[] copy_graph) {
		// Updates the graph by removing the forward and reverse edges in a
		// graph
		// with the capacity zero.
		Node2 sink = (Node2) graph[lastvertex];
		Integer parent = sink.parent;
		if (parent != null) {
			Node2 parent1 = (Node2) graph[parent];
			if (sink.color == "Red" && parent1.color == "Red") {
				Integer value = parent1.hp.get(lastvertex);
				if (value == 0) {
					parent1.hp.remove(lastvertex);
				}
				Integer reversevalue = sink.hp.get(parent);
				if (reversevalue == 0) {
					sink.hp.remove(parent);
				}
				removeedge(graph, parent, copy_graph);
			}
		} else {
			BFS(graph, copy_graph);
		}
	}
	//Operations on output file
}