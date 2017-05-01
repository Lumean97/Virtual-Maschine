package de.lumeanit.hochschule.hwp.virtualMachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Assembler {

	public static final int NOP = 0;
	public static final int LOAD = 1;
	public static final int MOV = 2;
	public static final int ADD = 3;
	public static final int SUB = 4;
	public static final int MUL = 5;
	public static final int DIV = 6;
	public static final int PUSH = 7;
	public static final int POP = 8;
	public static final int JMP = 9;
	public static final int JIZ = 10;
	public static final int JIH = 11;
	public static final int JSR = 12;
	public static final int RTS = 13;

	private VirtualMachine vm;

	
	public Assembler(){
		// TODO REMOVE
	}
	
	public Assembler(VirtualMachine vm) {
		this.vm = vm;

		// LOAD 500
	}

	public void assembleFile(File toAssemble) throws FileNotFoundException {
		
		
		
		Scanner fileReader = new Scanner(toAssemble);
		for (int i = 0; fileReader.hasNext(); i++) {
			

			
			String assemblerLine = fileReader.nextLine();
			String command = assemblerLine.split(" ")[0];

			int opCode = 0;
			// Value for later rx and ry. rxy[0] = rx; rxy[1] = ry.
			int[] rxy;
			switch (command) {
			case "LOAD":
				opCode = LOAD + (Integer.valueOf(assemblerLine.split(" ")[1]) << 4);
				break;
			case "MOV":
				opCode = MOV;
				rxy = findRXY(assemblerLine);
				// check for toMem and fromMem bits
				for(int j = 3; j<assemblerLine.length(); j++){
					 if(assemblerLine.charAt(j) == ')'){
						 if(assemblerLine.charAt(j-3) == '(' || assemblerLine.charAt(j-4) == '('){
							 
							 //Check if it is fromMem (Last char in Line) or fromMem.
							 if(j == assemblerLine.length()-1){
								 opCode += 1 << 12;
							 }else{
								 opCode += 1 << 13;
							 }
						 }else{
							 // TODO THROW SYNTAX EXCEPTION
						 }
					 }
				}
				opCode += rxy[0] << 4;
				opCode += rxy[1] << 8;
				break;
			case "ADD":
				opCode -= 1;
			case "SUB":
				opCode -= 1;
			case "MUL":
				opCode -= 1;
			case "DIV":
				rxy = findRXY(assemblerLine);
				opCode += DIV;
				opCode += rxy[0] << 4;
				opCode += rxy[1] << 8;
				break;
			case "PUSH":
				opCode = -1;
			case "POP":
				rxy = findRXY(assemblerLine);
				opCode += POP;
				opCode += rxy[0] << 4;
				break;
			case "JMP":
				opCode -= 1;
			case "JIZ":
				opCode -= 1;
			case "JIH":
				opCode -= 1;
			case "JSR":
				opCode += JSR;
				opCode += (Integer.valueOf(assemblerLine.split(" ")[1]) << 4);
				break;
			case "RTS":
				opCode = RTS;
				break;
			}
			vm.writeMemory(i, opCode);
		System.out.println("line " + i  + " assembled: " + opCode) ;
		}
		
		
	}

	public int checkMemory(String code, int linePosition) {
		int retval = 0;
		if (code.charAt(linePosition - 1) == '(') {
			if ((code.charAt(linePosition + 2) == ')' || code.charAt(linePosition + 3) == ')')) {
				retval += (1 << 12);
			} else {
				// TODO : THROW SYNTAX EXCEPTION
			}
		}

		return retval;
	}

	public int[] findRXY(String code) {
		String[] xySplit = code.split("R");
		int[] retval = new int[2];
		for (int i = 1; i < xySplit.length; i++) {
			if (xySplit[i].length() >= 2 && isNumber(xySplit[i].charAt(1))) {
				retval[i-1] = Integer.valueOf("" + xySplit[i].charAt(0) + "" + xySplit[i].charAt(1));
			} else {
				retval[i-1] = Integer.valueOf(xySplit[i].charAt(0) + "");
			}
		}
		return retval;
	}

	public boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}

}
