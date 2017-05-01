package de.lumeanit.hochschule.hwp.virtualMachine;

import java.io.File;
import java.io.FileNotFoundException;

public class Application {
	private static int[] fib;
	private static int fibC = 0;
public static void main(String[] args) {
	
	VirtualMachine vm = new VirtualMachine(16, 4096);
	Assembler a = new Assembler(vm);
	File f = new File("files/ofib.txt");
	System.out.println(f.exists());
	try {
		a.assembleFile(f);
		int i = 0;
 		while(vm.executeProgramLine() != -1){
			
			System.out.println("Program " + i++ + " executed!");
			
			
		}
		System.out.println(vm.readMemory(1000));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	/*
	int nx = 46;
	fib = new int[nx+1];
	fibAs(nx);
	for(int i : fib){
		System.out.println(i);
	}
	*/
}


public static void fibAs(int n){
	if(n < 2){
		fib[fibC] = n;
	}else{
		int owFib = fibC;
		fibC++;
		int fib1 = fibC;
		int fib2 = fibC+1;
		fibAs(n-1);
		fib[owFib] = fib[fib1] + fib[fib2];
	}
}
}
