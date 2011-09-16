
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.imageio.ImageIO;

import lejos.nxt.Motor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ControlRemoto.java
 *
 * Created on 21/08/2011, 23:28:10
 */

/**
 *
 * @author Victor
 */
public class ControlRemoto extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 250820112113L;
	/** Creates new form ControlRemoto */
    public ControlRemoto() {
        initComponents();
        jTA_Log.setEditable(true);
        jcSensar.setSelected(true);

        jP_Joystick.addMouseListener(this);
        jP_Joystick.addMouseMotionListener(this);

        Thread t = new Thread(new Runnable() {
            public void run() {
                while (true){
                    if ((clik) && ((PanelConector.estaConectado()) || (!hayRobot))){
//                    	imprimir();
                        if (mejorado){
                        	moverMotoresMejorado();
                        } else {
                        	moverMotores();
                        }
                    	if (jcSensar.isSelected() && hayRobot){
                                actualizarSensores();
                        }
                    } 
                    try {
                        if (mejorado){
                                Thread.sleep(500); //Opcion 2;
                        } else {
                                Thread.sleep(100); // Opcion 1;
                        }
                    } catch (InterruptedException e) {
                            e.printStackTrace();
                    }
                }
            }
        }, "Thread para capturar el clik del mouse en el joystick y mover los motores");
        t.start(); 
    }

    public void mouseClicked(MouseEvent e){	}

    public void mouseReleased(MouseEvent e){
    	clik = false;
    	posX = 0;
    	posY = 0;
    	if (PanelConector.estaConectado()){
    		try {
	    		Motor.A.stop();
	    		Motor.B.stop();
	    	} catch (Exception ex){
	    		ex.printStackTrace();
	    	}
    	}
    }

    public void mousePressed(MouseEvent e){
    	clik = true;
    	posXold = posX;
    	posYold = posY;
    	if (mejorado){
    		posX = (e.getX() - 100);
    		posY = -(e.getY() - 100);

    		posX = (posX > 100) ? 100 : posX; 
    		posY = (posY > 100) ? 100 : posY;
    		posX = (posX < -100) ? -100 : posX; 
    		posY = (posY < -100) ? -100 : posY;
    	} else {
    		posX = (e.getX()-100)-((e.getX()-100) % 10);
    		posY = (-(e.getY()-100))-( -(e.getY()-100) % 10);
    	}
    	
    }

    public void mouseDragged(MouseEvent e){
    	posXold = posX;
    	posYold = posY;
    	if (mejorado){
    		posX = (e.getX() - 100);
    		posY = -(e.getY() - 100);

    		posX = (posX > 100) ? 100 : posX; 
    		posY = (posY > 100) ? 100 : posY;
    		posX = (posX < -100) ? -100 : posX; 
    		posY = (posY < -100) ? -100 : posY;
    	} else {
    		posX = (e.getX()-100)-((e.getX()-100) % 10);
    		posY = (-(e.getY()-100))-( -(e.getY()-100)%10);
    	}
    }

    public void mouseExited(MouseEvent e){ }
    public void mouseEntered(MouseEvent e){ }

    public void mouseMoved(MouseEvent e) { }

    public void imprimir(){
        if (clik){
            System.out.println("X: "+posX);
            System.out.println("Y: "+posY);
        }
    }

    private void moverMotores(){
    	try {
            int gradosA=0;
            int gradosB=0;
            if ((posX == 0) && (posY > 0)){ // avanza hacia adelante, A = B
                    gradosA = (int)(posY );/// 10);
                    gradosB = gradosA;
            }
            if ((posX == 0) && (posY < 0)){ // retrocede derecho -A = -B
                    gradosA = (int)(posY );/// 10);
                    gradosB = gradosA;
            }
            if ((posX > 0) && (posY == 0)){ // gira a la derecha A = -B
                    gradosA = (int)(posX );/// 10);
                    gradosB = -gradosA;
            }
            if ((posX < 0) && (posY == 0)){ // gira a la izquierda -A = B
                    gradosA = (int)(posX );/// 10);
                    gradosB = -gradosA;
            }
            if ((posX > 0) && (posY > 0)){ // adelante y derecha A > B
                    gradosA = ((int)(posX))+((int)(posY));//((int)(posX / 10))+((int)(posY / 10));
                    gradosB = (int)(posY );/// 10);
            }
            if ((posX < 0) && (posY > 0)){ // adelante e izquierda A < B
                    gradosA = (int)(posY );/// 10);
                    gradosB = -((int)(posX))+((int)(posY));//-((int)(posX / 10))+((int)(posY / 10));
            }
            if ((posX > 0) && (posY < 0)){ // retrocede derecha -A > -B
                    gradosA = -((int)(posX))+((int)(posY));//-((int)(posX / 10))+((int)(posY / 10));
                    gradosB = (int)(posY );/// 10);
            }
            if ((posX < 0) && (posY < 0)){ // retrocede izuqierda -A < -B
                    gradosA = (int)(posY );/// 10);
                    gradosB = ((int)(posX))+((int)(posY));//((int)(posX / 10))+((int)(posY / 10));
            }
            if ((posX == 0) && (posY == 0)){ // no se mueve
                    gradosA = 0;
                    gradosB = 0;
            }
            Motor.A.rotate(gradosA, true);
            Motor.B.rotate(gradosB, true);
            logea("Mover Motor A "+gradosA+"°\n");
            logea("Mover Motor B "+gradosB+"°\n");
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
    
    private void moverMotoresMejorado(){
    	try {
            int velocidadA=0;
            int velocidadB=0;
            if ((posX == 0) && (posY > 0)){ // avanza hacia adelante, A = B
                    velocidadA = posY;
                    velocidadB = velocidadA;
            }
            if ((posX == 0) && (posY < 0)){ // retrocede derecho -A = -B
                    velocidadA = posY;
                    velocidadB = velocidadA;
            }
            if ((posX > 0) && ((posY > -5) && (posY < 5))){ // gira a la derecha A = -B
                    velocidadA = posX;
                    velocidadB = -velocidadA;
            }
            if ((posX < 0) && ((posY > -5) && (posY < 5))){ // gira a la izquierda -A = B
                    velocidadA = posX;
                    velocidadB = -velocidadA;
            }
            if ((posX > 0) && (posY > 0)){ // adelante y derecha A > B
                    velocidadA = posX+posY;
                    velocidadB = posY;
            }
            if ((posX < 0) && (posY > 0)){ // adelante e izquierda A < B
                    velocidadA = posY;
                    velocidadB = (-posX)+posY;
            }
            if ((posX > 0) && (posY < 0)){ // retrocede derecha -A > -B
                    velocidadA = (-posX)+posY;
                    velocidadB = posY;
            }
            if ((posX < 0) && (posY < 0)){ // retrocede izuqierda -A < -B
                    velocidadA = posY;
                    velocidadB = posX+posY;
            }
            if ((posX == 0) && (posY == 0)){ // no se mueve
                    velocidadA = 0;
                    velocidadB = 0;
            }
            
            
//            if (Motor.A.isMoving() && Motor.B.isMoving()){
//                    if (gradosA < 0) {
//                            Motor.A.backward();
//                            
//                    }
//                    if (gradosB < 0){
//                            Motor.B.backward();
//                    }
//            } else {
           velocidadA = velocidadA*5;
           velocidadB = velocidadB *5;
           Motor.A.smoothAcceleration(true);
           Motor.B.smoothAcceleration(true);
            if (hayRobot){
	            if (formaAvanzarNueva){
	            	if ((posXold > 0) && (velocidadA < 0)){//Motor.A.isForward()
	                	Motor.A.stop();
	                	Motor.A.backward();
	                }
	                if ((posYold > 0) && (velocidadB < 0)){//Motor.B.isForward()
	                	Motor.B.stop();
	                	Motor.B.backward();
	                }
	                
	                if ((posXold < 0) && (velocidadA > 0)){//Motor.A.isBackward()
	                	Motor.A.stop();
	                	Motor.A.forward();
	                }
	                if ((posYold < 0) && (velocidadB > 0)){//Motor.B.isBackward()
	                	Motor.B.stop();
	                	Motor.B.forward();
	                }
	                if ((!Motor.A.isMoving()) && (velocidadA > 0)){
	                	Motor.A.forward();
	                }
	                if ((!Motor.B.isMoving()) && (velocidadB > 0)){
	                	Motor.B.forward();
	                }
	                if ((!Motor.A.isMoving()) && (velocidadA < 0)){
	                	Motor.A.backward();
	                }
	                if ((!Motor.B.isMoving()) && (velocidadB < 0)){
	                	Motor.B.backward();
	                }
	            } else {
	            	if (velocidadA < 0) {
	                    Motor.A.backward();
		            } else {
		                    if (velocidadA > 0){
		                            Motor.A.forward();
		                    } else {
		                            Motor.A.stop();
		                    }
		            }
		            if (velocidadB < 0){
		                    Motor.B.backward();
		            } else {
		                    if (velocidadB > 0) {
		                            Motor.B.forward();
		                    } else {
		                            Motor.B.stop();
		                    }
		            }
	            }
	
	            Motor.A.setSpeed(Math.abs(velocidadA));
	            Motor.B.setSpeed(Math.abs(velocidadB));
            } else {
            	if (formaAvanzarNueva){
	            	if ((posXold > 0) && (velocidadA < 0)){//Motor.A.isForward()
	                	System.out.println("Motor.A.stop();");
	                	System.out.println("Motor.A.backward();");
	                }
	                if ((posYold > 0) && (velocidadB < 0)){//Motor.B.isForward()
	                	System.out.println("Motor.B.stop();");
	                	System.out.println("Motor.B.backward();");
	                }
	                
	                if ((posXold < 0) && (velocidadA > 0)){//Motor.A.isBackward()
	                	System.out.println("Motor.A.stop();");
	                	System.out.println("Motor.A.forward();");
	                }
	                if ((posYold < 0) && (velocidadB > 0)){//Motor.B.isBackward()
	                	System.out.println("Motor.B.stop();");
	                	System.out.println("Motor.B.forward();");
	                }
	                if ((!Motor.A.isMoving()) && (velocidadA > 0)){
	                	System.out.println("Motor.A.forward();");
	                }
	                if ((!Motor.B.isMoving()) && (velocidadB > 0)){
	                	System.out.println("Motor.B.forward();");
	                }
	                if ((!Motor.A.isMoving()) && (velocidadA < 0)){
	                	System.out.println("Motor.A.backward();");
	                }
	                if ((!Motor.B.isMoving()) && (velocidadB < 0)){
	                	System.out.println("Motor.B.backward();");
	                }
	            } else {
	            	if (velocidadA < 0) {
	            		System.out.println("Motor.A.backward();");
		            } else {
		                    if (velocidadA > 0){
		                    	System.out.println("Motor.A.forward();");
		                    } else {
		                    	System.out.println("Motor.A.stop();");
		                    }
		            }
		            if (velocidadB < 0){
		            	System.out.println("Motor.B.backward();");
		            } else {
		                    if (velocidadB > 0) {
		                    	System.out.println("Motor.B.forward();");
		                    } else {
		                    	System.out.println("Motor.B.stop();");
		                    }
		            }
	            }
	
            	System.out.println("Motor.A.setSpeed("+Math.abs(velocidadA)+");");
            	System.out.println("Motor.B.setSpeed("+Math.abs(velocidadB)+");");
            }
            System.out.println("velocidad A: "+velocidadA);
            System.out.println("velocidad B: "+velocidadB);
//            }
            logea("Mover Motor A: " + Math.abs(velocidadA)+"\n");
            logea("Mover Motor B: " + Math.abs(velocidadB)+ "\n");
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }

    private void logea(String linea){
            jTA_Log.append(linea);
            jTA_Log.getCaret().setDot( jTA_Log.getText().length() );
            jSPBitacora.scrollRectToVisible(jTA_Log.getVisibleRect() );
    }

    private void actualizarSensores(){
        jtMotorA.setText(String.valueOf(Motor.A.getTachoCount()));
        jtMotorB.setText(String.valueOf(Motor.B.getTachoCount()));
        jtMotorC.setText(String.valueOf(Motor.C.getTachoCount()));
        jtTacto.setText(String.valueOf(PanelConector.getSensorTacto().isPressed()));
        jtDistancia.setText(String.valueOf(PanelConector.getSensorUltrasonico().getDistance()));
        jtColor.setText(String.valueOf(PanelConector.getSensorLuz().readNormalizedValue()));//.readColor().name()));
    }

    



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP_Joystick = new javax.swing.JPanel(){
            private static final long serialVersionUID = 1L;
            public void paint (Graphics g) {
                //super.paint (g);
                try {
                    imagen = ImageIO.read(getClass().getResource("fondo.png"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(),this);
                if (imagen != null){
                    setOpaque(false);
                } else {
                    setOpaque(true);
                }

                super.paint (g);
            }
        }
        ;
        jL_MotorA = new javax.swing.JLabel();
        jL_MotorB = new javax.swing.JLabel();
        jL_MotorC = new javax.swing.JLabel();
        jL_Distancia = new javax.swing.JLabel();
        jL_Color = new javax.swing.JLabel();
        jL_Tacto = new javax.swing.JLabel();
        jcSensar = new javax.swing.JCheckBox();
        jSPBitacora = new javax.swing.JScrollPane();
        jTA_Log = new javax.swing.JTextArea();
        jtMotorA = new javax.swing.JTextField();
        jtMotorB = new javax.swing.JTextField();
        jtMotorC = new javax.swing.JTextField();
        jtTacto = new javax.swing.JTextField();
        jtColor = new javax.swing.JTextField();
        jtDistancia = new javax.swing.JTextField();
        jlLogo = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(701, 376));
        setMinimumSize(new java.awt.Dimension(701, 376));

        jP_Joystick.setBackground(new java.awt.Color(153, 153, 153));
        jP_Joystick.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jP_Joystick.setMaximumSize(new java.awt.Dimension(200, 200));
        jP_Joystick.setMinimumSize(new java.awt.Dimension(200, 200));

        javax.swing.GroupLayout jP_JoystickLayout = new javax.swing.GroupLayout(jP_Joystick);
        jP_Joystick.setLayout(jP_JoystickLayout);
        jP_JoystickLayout.setHorizontalGroup(
            jP_JoystickLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );
        jP_JoystickLayout.setVerticalGroup(
            jP_JoystickLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );

        jL_MotorA.setText("Motor A:");

        jL_MotorB.setText("Motor B:");

        jL_MotorC.setText("Motor C:");

        jL_Distancia.setText("Distancia:");

        jL_Color.setText("Color:");

        jL_Tacto.setText("Tacto:");

        jcSensar.setText("Sensar?");

        jTA_Log.setColumns(20);
        jTA_Log.setEditable(false);
        jTA_Log.setRows(5);
        jTA_Log.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSPBitacora.setViewportView(jTA_Log);

        jtMotorA.setEnabled(false);

        jtMotorB.setEnabled(false);

        jtMotorC.setEnabled(false);

        jtTacto.setEnabled(false);

        jtColor.setEnabled(false);

        jtDistancia.setEnabled(false);

        jlLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/irobot.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jP_Joystick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jL_MotorB, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jL_MotorA, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jL_MotorC, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jL_Tacto)
                                    .addComponent(jL_Color, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jL_Distancia, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtDistancia)
                                    .addComponent(jtColor)
                                    .addComponent(jtTacto)
                                    .addComponent(jtMotorC)
                                    .addComponent(jtMotorB)
                                    .addComponent(jtMotorA, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)))
                            .addComponent(jcSensar)))
                    .addComponent(jlLogo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSPBitacora, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSPBitacora, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                    .addComponent(jP_Joystick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jL_MotorA)
                            .addComponent(jtMotorA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jL_MotorB)
                            .addComponent(jtMotorB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jL_MotorC)
                            .addComponent(jtMotorC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jL_Tacto)
                            .addComponent(jtTacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jL_Color)
                            .addComponent(jtColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jL_Distancia)
                            .addComponent(jtDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcSensar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(jlLogo)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jL_Color;
    private javax.swing.JLabel jL_Distancia;
    private javax.swing.JLabel jL_MotorA;
    private javax.swing.JLabel jL_MotorB;
    private javax.swing.JLabel jL_MotorC;
    private javax.swing.JLabel jL_Tacto;
    private javax.swing.JPanel jP_Joystick;
    private javax.swing.JScrollPane jSPBitacora;
    private javax.swing.JTextArea jTA_Log;
    private javax.swing.JCheckBox jcSensar;
    private javax.swing.JLabel jlLogo;
    private javax.swing.JTextField jtColor;
    private javax.swing.JTextField jtDistancia;
    private javax.swing.JTextField jtMotorA;
    private javax.swing.JTextField jtMotorB;
    private javax.swing.JTextField jtMotorC;
    private javax.swing.JTextField jtTacto;
    // End of variables declaration//GEN-END:variables

    private boolean clik=false;
    private int posX =0;
    private int posY =0;
    private int posXold =0;
    private int posYold =0;
    private boolean mejorado = true;
    Image imagen = null;
    private boolean formaAvanzarNueva = true;//false;//
    private boolean hayRobot=true;//false; //
}
