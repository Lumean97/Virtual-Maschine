package de.lumeanit.hochschule.hwp.virtualMachine;

import java.io.File;
import java.io.FileNotFoundException;

public class Application {
public static void main(String[] args) {
	VirtualMachine vm = new VirtualMachine(16, 4096);
	Assembler a = new Assembler(vm);
	File f = new File("files/test.txt");
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
	
}
}
