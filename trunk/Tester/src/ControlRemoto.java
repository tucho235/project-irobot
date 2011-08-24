
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

    /** Creates new form ControlRemoto */
    public ControlRemoto() {
        initComponents();
        jcheck.setSelected(true);

        jP_Joystick.addMouseListener(this);
        jP_Joystick.addMouseMotionListener(this);

        Thread t = new Thread(new Runnable() {
            public void run() {
                    while (true){
                            if ((clik) && (PanelConector.estaConectado())){
                                    imprimir();
                                    moverMotores();
                                    if (jcheck.isSelected()){
                                            actualizarSensores();
                                    }
                            }
                            try {
                                    if (mejorado){
                                            Thread.sleep(400); //Opcion 2;
                                    } else {
                                            Thread.sleep(1); // Opcion 1;
                                    }

                                    } catch (InterruptedException e) {
                                            e.printStackTrace();
                                    }
                    }
            }
        }, "Thread para capturar el clik del mouse en el joystick y mover los motores");
        t.start();

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                    while (true){
                            if (PanelConector.estaConectado()){
                                    keepAlive();
                            }
                            try {
                                    Thread.sleep(2500);
                            } catch (InterruptedException e) {
                                    e.printStackTrace();
                            }
                    }
            }
        }, "Thread para mantener despierta la conexi�n");
	t2.start();


    }

    public void mouseClicked(MouseEvent e){	}

    public void mouseReleased(MouseEvent e){
    	clik = false;
    	posX = 0;
    	posY = 0;
    	Motor.A.stop();
    	Motor.B.stop();
    }

    public void mousePressed(MouseEvent e){
    	clik = true;
    	posX = (e.getX()-100)-((e.getX()-100) % 10);
	posY = (-(e.getY()-100))-( -(e.getY()-100)%10);
    }

    public void mouseDragged(MouseEvent e){
    	posX = (e.getX()-100)-((e.getX()-100) % 10);
		posY = (-(e.getY()-100))-( -(e.getY()-100)%10);
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

            if (!mejorado){
                    // Opcion 1 // Para esta opcion hay que poner el sleep en 1 en el Thread t
                    Motor.A.rotate(gradosA, true);
                    Motor.B.rotate(gradosB, true);
            } else {
                    // Opcion 2 // Para esta opcion hay que poner el sleep en 400 en el Thread t
                    Motor.A.setSpeed(Math.abs(gradosA));
                    Motor.A.setSpeed(Math.abs(gradosB));
                    if (Motor.A.isMoving() && Motor.B.isMoving()){
                            if (gradosA < 0) {
                                    Motor.A.backward();
                            }
                            if (gradosB < 0){
                                    Motor.B.backward();
                            }
                    } else {
                            if (gradosA < 0) {
                                    Motor.A.backward();
                            } else {
                                    if (gradosA > 0){
                                            Motor.A.forward();
                                    } else {
                                            Motor.A.stop();
                                    }
                            }
                            if (gradosB < 0){
                                    Motor.B.backward();
                            } else {
                                    if (gradosB > 0) {
                                            Motor.B.forward();
                                    } else {
                                            Motor.B.stop();
                                    }
                            }
                    }
            }

            logea("Mover Motor A "+gradosA+"°\n");
            logea("Mover Motor B "+gradosB+"°\n");

    }

    private void logea(String linea){
            jTA_Log.append(linea);
            jTA_Log.getCaret().setDot( jTA_Log.getText().length() );
    jScrollPane1.scrollRectToVisible(jTA_Log.getVisibleRect() );
    }

    private void actualizarSensores(){
        jtMotorA.setText(String.valueOf(Motor.A.getTachoCount()));
        jtMotorB.setText(String.valueOf(Motor.B.getTachoCount()));
        jtMotorC.setText(String.valueOf(Motor.C.getTachoCount()));
        jtTacto.setText(String.valueOf(PanelConector.getSensorTacto().isPressed()));
        jtDistancia.setText(String.valueOf(PanelConector.getSensorUltrasonico().getDistance()));
        jtColor.setText(String.valueOf(PanelConector.getSensorLuz().readNormalizedValue()));//.readColor().name()));
    }

    public void keepAlive(){
            Motor.A.getTachoCount();
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP_Joystick = new javax.swing.JPanel(){
        	/**
			 * 
			 */
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
			
        };
        jL_MotorA = new javax.swing.JLabel();
        jL_MotorB = new javax.swing.JLabel();
        jL_MotorC = new javax.swing.JLabel();
        jL_Distancia = new javax.swing.JLabel();
        jL_Color = new javax.swing.JLabel();
        jL_Tacto = new javax.swing.JLabel();
        jcheck = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTA_Log = new javax.swing.JTextArea();
        jtMotorA = new javax.swing.JTextField();
        jtMotorB = new javax.swing.JTextField();
        jtMotorC = new javax.swing.JTextField();
        jtTacto = new javax.swing.JTextField();
        jtColor = new javax.swing.JTextField();
        jtDistancia = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

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

        jcheck.setText("Sensar?");

        jTA_Log.setColumns(20);
        jTA_Log.setEditable(false);
        jTA_Log.setRows(5);
        jTA_Log.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTA_Log);

        jtMotorA.setEnabled(false);

        jtMotorB.setEnabled(false);

        jtMotorC.setEnabled(false);

        jtTacto.setEnabled(false);

        jtColor.setEnabled(false);

        jtDistancia.setEnabled(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/irobot.png"))); // NOI18N

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
                            .addComponent(jcheck)))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
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
                        .addComponent(jcheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jP_Joystick;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTA_Log;
    private javax.swing.JCheckBox jcheck;
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
    private boolean mejorado = true;
    Image imagen = null;
}
