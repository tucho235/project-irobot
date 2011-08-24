
import lejos.nxt.*;
import lejos.nxt.remote.NXTCommand;
import lejos.pc.comm.*;

public class ContadorGrados {
 
  public static void main(String [] args) throws Exception {
    NXTConnector conn = new NXTConnector();

    if (!conn.connectTo("NXT", NXTComm.LCP)) {
      System.err.println("Conexión Fallida");
      System.exit(1);
    }
    NXTCommand.getSingleton().setNXTComm(conn.getNXTComm());
   
    Motor.A.resetTachoCount();
    Motor.B.resetTachoCount();
    System.out.println("Tacómetro A: " + Motor.A.getTachoCount());
    System.out.println("Tacómetro B: " + Motor.B.getTachoCount());
    System.out.println("Tacómetro C: " + Motor.C.getTachoCount());
    //Motor.A.rotate(3600, true);
    //Motor.B.rotate(-3600);
    Thread.sleep(1000);
    System.out.println("Tacómetro A: " + Motor.A.getTachoCount());
    System.out.println("Tacómetro B: " + Motor.B.getTachoCount());
    System.out.println("Tacómetro C: " + Motor.C.getTachoCount());
    Thread.sleep(1000);
    
    
    UltrasonicSensor sensorUltrasonico = new UltrasonicSensor(SensorPort.S2);
    ColorLightSensor sensorLuz = new ColorLightSensor(SensorPort.S2, ColorLightSensor.TYPE_COLORFULL);
    TouchSensor sensorTacto = new TouchSensor(SensorPort.S1);
    
    System.out.println("Senseor de Ultrasonido: " + sensorUltrasonico.getDistance());
    System.out.println("Senseor de Tacto: " + sensorTacto.isPressed());
    System.out.println("Senseor de Luz: " +sensorLuz.readColor().name());
    //lejos.nxt.Button.waitForPress();
    Thread.sleep(1000);
    sensorUltrasonico.reset();
    System.out.println("Senseor de Ultrasonido: " + sensorUltrasonico.getDistance());
    System.out.println("Senseor de Tacto: " + sensorTacto.isPressed());
    sensorUltrasonico.reset();
    //lejos.nxt.Button.waitForPress();
    Thread.sleep(1000);
    System.out.println("Senseor de Ultrasonido: " + sensorUltrasonico.getDistance());
    System.out.println("Senseor de Tacto: " + sensorTacto.isPressed());
    sensorUltrasonico.reset();
    //lejos.nxt.Button.waitForPress();
    Thread.sleep(1000);
    System.out.println("Senseor de Ultrasonido: " + sensorUltrasonico.getDistance());
    System.out.println("Senseor de Tacto: " + sensorTacto.isPressed());
    sensorUltrasonico.reset();
    //lejos.nxt.Button.waitForPress();
    Thread.sleep(1000);
    conn.close();
  }
}
