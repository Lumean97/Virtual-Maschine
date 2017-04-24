package de.lumeanit.hochschule.hwp.virtualMachine;

import java.util.Stack;

/**
 * VirtualMachine is a model of a CPU. It has a specified register- and memory
 * size to work with it.
 * 
 * @author m.schwalm
 *
 */
public class VirtualMachine {

	private int[] register;
	private int[] memory;
	private Stack<Integer> registerStack = new Stack<>();
	private Stack<Integer> subroutineStack = new Stack<>();

	private int programCounter = 0;

	/**
	 * creates a virtual machine with standard array lengths: 16 for register.
	 * 4096 for memory.
	 */
	public VirtualMachine() {
		this(16, 4096);
	}

	/**
	 * creates a virtual machine of the specified registerLength and
	 * memoryLength
	 * 
	 * @param registerLength
	 *            defines the quantity of registers.
	 * @param memoryLength
	 *            defines the quantity of memory.
	 */
	public VirtualMachine(int registerLength, int memoryLength) {
		register = new int[registerLength];
		memory = new int[memoryLength];
	}

	/**
	 * Executes the next Program line inside of Memory. Which program line is
	 * executed is defined by programCounter
	 */
	public int executeProgramLine() {

		int command = memory[programCounter] & 0xF;
		System.out.println(command);
		int value = memory[programCounter] >> 4;
		int rx = value & 0xF;
		int ry = (value >> 4) & 0xF;
		boolean fromMemory = ((value >> 8) & 0x1) == 1;
		boolean toMemory = ((value >> 9) & 0x1) == 1;

		programCounter++;
		switch (command) {
		case Assembler.LOAD:
			register[0] = value;
			break;
		case Assembler.MOV:
			if (fromMemory && toMemory) {
				memory[register[rx]] = memory[register[ry]];
			} else if (fromMemory && !toMemory) {
				register[rx] = memory[register[ry]];
			} else if (toMemory) {
				memory[register[rx]] = register[ry];
			} else {
				register[rx] = register[ry];
			}
			break;
		case Assembler.ADD:
			register[rx] += register[ry];
			break;
		case Assembler.SUB:
			register[rx] -= register[ry];
			break;
		case Assembler.MUL:
			register[rx] *= register[ry];
			break;
		case Assembler.DIV:
			register[rx] /= register[ry];
			break;
		case Assembler.PUSH:
			registerStack.push(rx);
			break;
		case Assembler.POP:

			register[rx] = registerStack.pop();
			break;
		case Assembler.JIZ:
			if (register[0] == 0)
				programCounter = value;
			break;
		case Assembler.JIH:
			if (register[0] > 0)
				programCounter = value;
			break;

		case Assembler.JMP:
			programCounter = value;
			break;
		case Assembler.JSR:
			subroutineStack.push(programCounter + 1);
			programCounter = value;
			break;
		case Assembler.RTS:
			if (subroutineStack.isEmpty()) {
				return -1;
			}
			programCounter = subroutineStack.pop();
			break;

		}
		return 0;
	}

	/**
	 * Writes the value inside memory
	 * 
	 * @param address
	 *            Memory address
	 * @param value
	 *            This should be written
	 */
	public void writeMemory(int address, int value) {
		memory[address] = value;
	}
	
	public int readMemory(int address){
		return memory[address];
	}

}
