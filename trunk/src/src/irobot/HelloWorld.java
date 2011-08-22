package irobot;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class HelloWorld {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		imprimir1();
		//imprimir2();
	}
	
	public static void imprimir1(){
		LCD.drawString("Hola mundo!", 2, 2);
		LCD.refresh();
		
		
		try {
			Thread.sleep(5000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			}
			LCD.clear();
	}
	
	public static void imprimir2(){
		System.out.println("Hello World");
		Button.waitForPress();
		//Button.ENTER.waitForPressAndRelease();
		
	}

}
