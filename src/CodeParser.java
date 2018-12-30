import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class CodeParser {
	private List<Character> instructions;
	private int ukazatel;
	private Interpret interpret;
	
	public CodeParser(File f, int arraySize) throws Exception {
		ukazatel = 0;
		interpret = new Interpret(arraySize);
		instructions = new ArrayList<Character>();
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			DepthCounter dc = new DepthCounter();
			int chi = -1;
			int i = 1;
			int line = 1;
			Stack<Integer> lastIs = new Stack<Integer>();
			Stack<Integer> lastLines = new Stack<Integer>();
			while((chi = br.read()) != -1) {
				char ch = (char)chi;
				if (Character.isWhitespace(ch))
					i++;
				if (ch == '>' || ch == '<' || ch == '+' || ch == '-' || ch == '.' || ch == ',' || ch == '[' || ch == ']')
					instructions.add(ch);
				if (ch == '[') {
					dc.increment();
					lastIs.push(i);
					lastLines.push(line);
				}
				if (ch == ']') {
					try {
						dc.decrement();
						lastIs.pop();
						lastLines.pop();
					} catch (Exception e) {
						throw new Exception(String.format("Unopened cycle - line %d character %d", line, i));
					}
				}
				if (ch == '\n') {
					line++;
					i = 1;
				} else
					i++;
			}

			br.close();
			
			if (!dc.isZero())
				throw new Exception(String.format("Unclosed cycle - line %d character %d", lastLines.pop(), lastIs.pop()));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void execute() throws Exception {
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter("");
		while(ukazatel < instructions.size()) {
			char instruction = instructions.get(ukazatel);
			switch(instruction) {
			case '>':
				interpret.vetsitko();
				ukazatel++;
				break;
			case '<':
				interpret.mensitko();
				ukazatel++;
				break;
			case '+':
				interpret.plus();
				ukazatel++;
				break;
			case '-':
				interpret.minus();
				ukazatel++;
				break;
			case '.':
				System.out.print((char)interpret.tecka());
				ukazatel++;
				break;
			case ',':
				char b = scan.next().charAt(0);
				interpret.carka((byte)b);
				ukazatel++;
				break;
			case '[':
				if (interpret.jeNula())
					posunoutDoprava();
				else
					ukazatel++;
				break;
			case ']':
				if (!interpret.jeNula())
					posunoutDoleva();
				ukazatel++;
				break;
			}
		}
		scan.close();
	}

	private void posunoutDoleva() {
		DepthCounter dc = new DepthCounter();
		dc.increment();
		ukazatel--;
		
		while(!dc.isZero()) {
			char instruction = instructions.get(ukazatel--);
			if (instruction == ']')
				dc.increment();
			if (instruction == '[') {
				try {
					dc.decrement();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void posunoutDoprava() {
		DepthCounter dc = new DepthCounter();
		dc.increment();
		ukazatel++;
		
		while(!dc.isZero()) {
			char instruction = instructions.get(ukazatel++);
			if (instruction == '[')
				dc.increment();
			if (instruction == ']') {
				try {
					dc.decrement();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
