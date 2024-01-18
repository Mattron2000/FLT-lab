package visitor;

import java.util.ArrayList;

/**
 * @author Palmieri Matteo
 */
public class Register {

	private static ArrayList<Character> registers;

	public static void init() {
        registers = new ArrayList<Character>() {
            {
                add('a');
				add('b');
				add('c');
				add('d');
				add('e');
				add('f');
				add('g');
				add('h');
				add('i');
				add('j');
				add('k');
				add('l');
				add('m');
				add('n');
				add('o');
				add('p');
				add('q');
				add('r');
				add('s');
				add('t');
				add('u');
				add('v');
				add('w');
				add('x');
				add('y');
				add('z');
				add('A');
				add('B');
				add('C');
				add('D');
				add('E');
				add('F');
				add('G');
				add('H');
				add('I');
				add('J');
				add('K');
				add('L');
				add('M');
				add('N');
				add('O');
				add('P');
				add('Q');
				add('R');
				add('S');
				add('T');
				add('U');
				add('V');
				add('W');
				add('X');
				add('Y');
				add('Z');
            }
        };
    }

	public static char newRegister() {
        char register = registers.get(0);
        registers.remove(0);
        return register;
    }

	public static int size() {
		return (registers.size());
	}
}
