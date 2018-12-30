
public class Interpret {
	private byte[] pole;
	int ukazatel;
	
	public Interpret(int velikostPole) {
		pole = new byte[velikostPole];
		ukazatel = 0;
	}
	
	public void vetsitko() throws Exception {
		if (++ukazatel > pole.length) throw new Exception("Memory overrun");
	}
	
	public void mensitko() throws Exception {
		if (--ukazatel < 0) throw new Exception("Memory underrun");
	}
	
	public void plus() {
		pole[ukazatel]++;
	}
	
	public void minus() {
		pole[ukazatel]--;
	}
	
	public byte tecka() {
		return pole[ukazatel];
	}
	
	public void carka(byte b) {
		pole[ukazatel] = b;
	}
	
	public boolean jeNula() {
		return pole[ukazatel] == 0;
	}
}
