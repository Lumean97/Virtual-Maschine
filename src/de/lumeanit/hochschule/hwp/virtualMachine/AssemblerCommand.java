package de.lumeanit.hochschule.hwp.virtualMachine;

public enum AssemblerCommand {

	
	NOP(0),
	LOAD(1),
	MOV(2),
	ADD(3),
	SUB(4),
	MUL(5),
	DIV(6),
	PUSH(7),
	POP(8),
	JMP(9),
	JIZ(10),
	JIH(11),
	JSR(12),
	RTS(13);
	
	public final int value;
	private AssemblerCommand(int value){
		this.value = value;
	}
	
	
	
	

	
	
}
