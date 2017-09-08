/**
 * author :Anushka Bhandari
 * 2016134
 */



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

class Knight implements Comparable<Knight>{
	String Name;
	Coordinate co;
	boolean leave=false;
	Stack<String[]> stack = new Stack<>();
	Knight(String s,Coordinate co){
		this.Name=s;
		this.co=co;
	}
	@Override
	public int compareTo(Knight arg0) {
		// TODO Auto-generated method stub
		return this.Name.compareTo(arg0.Name);
	}



}
class NonCoordinateException extends Exception{  
	NonCoordinateException(String s){  
		super(s);  
	}  
}  


class QueenFoundException extends Exception{

	public QueenFoundException(String string) {
		// TODO Auto-generated constructor stub
		super(string);
		
	}  
}  
class OverlapException extends Exception{  
	OverlapException(String s){  
		super(s);  
	}  
}  

class Coordinate{
	int x,y;
	Coordinate(int i,int j)
	{
		this.x=i;
		this.y=j;
	}
	@Override
	public boolean equals(Object o) {
		Coordinate o1=(Coordinate) o;
		if (o1.x ==this.x && o1.y==this.y) {
			return true;
		}
		return false;
	}

}

public class Lab6 {
	static ArrayList<Knight> y=new ArrayList<>(); 
	static Coordinate queen;

	public static void overlap(Coordinate c,Knight p ) throws OverlapException {
		for(Knight k:y)
		{
			if(!k.leave)
			{
				if(k.co.equals(c))
				{
					k.leave=true;
					p.co.x=k.co.x;
					p.co.y=k.co.y;


					throw new OverlapException("Knights Overlap Exception"+ " " +k.Name);

				}
			}

		}
	}
	static void run(Knight thi) throws NonCoordinateException, OverlapException, QueenFoundException, IOException {
		try{
			String[] u=thi.stack.pop();
			if(!u[0].equals("Coordinate"))
			{
				throw new NonCoordinateException("Not a coordinate Exception  "+u[1]);
			}
			Coordinate nu=new Coordinate(Integer.parseInt(u[1]),Integer.parseInt(u[2]));
			overlap(nu,thi);
			thi.co.x=nu.x;
			thi.co.y=nu.y;
			queeny(thi.co);



			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));

			writer.write("No exception"+" "+thi.co.x+" "+thi.co.y+"\n");
			writer.close();


		}
		catch(EmptyStackException e){
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));

			writer.write("StackEmptyException: Stack Empty exception"+"\n");
			writer.close();

		}
		catch(OverlapException e){

			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));

			writer.write(e.getMessage()+"\n");
			writer.close();
		}
		catch(NonCoordinateException e){
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));

			writer.write(e.getMessage()+"\n");
			writer.close();
		}
		catch(QueenFoundException e){
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));

			writer.write(e.getMessage()+"\n");
			writer.close();
			System.exit(0);	}



	}

	public static void queeny(Coordinate co) throws QueenFoundException {
		// TODO Auto-generated method stub
		if(co.equals(queen))
		{
			throw new QueenFoundException("Queen has been Found. Abort");
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException, NonCoordinateException, OverlapException, QueenFoundException {
		// TODO Auto-generated method stub
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter The number of knights");
		Integer i=Integer.parseInt(reader.readLine());
		System.out.println("Enter The total number of iterations ");
		Integer itr=Integer.parseInt(reader.readLine());
		System.out.println("Enter the Coordinates of queen x and y ");
		String[] in=reader.readLine().split(" ");
		queen=new Coordinate(Integer.parseInt(in[0]),Integer.parseInt(in[1]));
		for(int i1=1;i1<=i;i1++) 
		{
			FileInputStream fr = new FileInputStream("./src/" + i1 + ".txt");
			DataInputStream in1 = new DataInputStream(fr);
			String strLine;

			int flag = 0; 
			while((strLine = in1.readLine()) != null )
			{
				String name = strLine;
				String[] inarr=in1.readLine().split(" ");
				Coordinate noo=new Coordinate(Integer.parseInt(inarr[0]),Integer.parseInt(inarr[1]));
				Knight uu=new Knight(name,noo);
				y.add(uu);
				int len=Integer.parseInt(in1.readLine());
				for(int a=0;a<len;a++)
				{
					String[] lol=in1.readLine().split(" ");
					uu.stack.push(lol);

				}
			}
		}
		Collections.sort(y);
		for(int p=0;p<itr;p++)
		{
			for(Knight k:y)
			{
				if(!k.leave)
				{
					BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));

					writer.write((int)(p+1)+" "+k.Name+" "+k.co.x+" "+k.co.y+"\n");
					writer.close();

					run(k);
				}
			}


		}
	}
}
