/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Ventana2.java
 *
 * Created on 07/04/2011, 15:00:38
 */

import lejos.nxt.*;
import lejos.nxt.remote.NXTCommand;
import lejos.pc.comm.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;


/**
 *
 * @author Victor
 */
public class Ventana2_2 extends javax.swing.JFrame implements ActionListener, MouseListener, MouseMotionListener {

	private boolean conectado = false;
    /**
	 * 
	 */
	private static final long serialVersionUID = 201108220307L;
	/** Creates new form Ventana2 */
    public Ventana2_2() {
        initComponents();
        jcheck.setSelected(true);
        
        WindowAdapter oyenteCierraVentana = new java.awt.event.WindowAdapter() {
			public void windowClosing (WindowEvent e) {
				this_windowClosing(e);
			}
		};
		this.addWindowListener(oyenteCierraVentana);
		
		 jP_Joystick.addMouseListener(this);
		 jP_Joystick.addMouseMotionListener(this);
		 
		 Thread t = new Thread(new Runnable() {
	    	public void run() {
    			while (true){
    				if ((clik) && (conectado)){
    					System.out.println("conectado: "+conectado+", clik: "+clik);
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
    				if (conectado){
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
    public void mouseExited(MouseEvent e){ }
    public void mouseEntered(MouseEvent e){ }

	public void actionPerformed(ActionEvent arg0) { }
	
	public void mouseDragged(MouseEvent e){
    	posX = (e.getX()-100)-((e.getX()-100) % 10);
		posY = (-(e.getY()-100))-( -(e.getY()-100)%10);
	}

	public void keepAlive(){
//		if ( lcd > LCD.SCREEN_WIDTH){
//			lcd++;
//			LCD.drawPixels( punto, lcd, LCD.SCREEN_WIDTH-1, true);
//			LCD.refresh();
//		} else {
//			LCD.clear();
//		}
		Motor.A.getTachoCount();
	}
	
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
		
		logea("Mover Motor A "+gradosA+"�\n");
		logea("Mover Motor B "+gradosB+"�\n");

	}
	
	private void logea(String linea){
		jTA_Log.append(linea);
		jTA_Log.getCaret().setDot( jTA_Log.getText().length() );
        jScrollPane1.scrollRectToVisible(jTA_Log.getVisibleRect() );
	}
	
	private void actualizarSensores(){
	    jTF_MotorA.setText(String.valueOf(Motor.A.getTachoCount()));
	    jTF_MotorB.setText(String.valueOf(Motor.B.getTachoCount()));
	    jTF_MotorC.setText(String.valueOf(Motor.C.getTachoCount()));
	    jTF_Tacto.setText(String.valueOf(sensorTacto.isPressed()));
	    jTF_Distancia.setText(String.valueOf(sensorUltrasonico.getDistance()));
	    jTF_Color.setText(String.valueOf(sensorLuz.readNormalizedValue()));//.readColor().name()));
	}
	
	public void botonConectar_actionPerformed(ActionEvent e) {
		conectado = true;
		jB_Conectar.setEnabled(!conectado);
		jB_Desconectar.setEnabled(conectado);
		
		
		
		try {
			conn = new NXTConnector();
			if (!conn.connectTo("NXT", NXTComm.LCP)) {
		    	System.err.println("Conexi�n Fallida");
		      	//System.exit(1);
		      	conectado = false;
		      	jB_Desconectar.setEnabled(conectado);
				jB_Conectar.setEnabled(!conectado);
		    } else {
		    	NXTCommand.getSingleton().setNXTComm(conn.getNXTComm());
			    Motor.A.resetTachoCount();
			    Motor.B.resetTachoCount();
			    sensorUltrasonico = new UltrasonicSensor(SensorPort.S1);
			    sensorLuz = new LightSensor(SensorPort.S2);
			    sensorTacto = new TouchSensor(SensorPort.S3);
			    jTA_Log.setText("Conectado al NXT "+ Calendar.HOUR_OF_DAY + ":" + 
			    		Calendar.MINUTE + ":" + Calendar.SECOND + "\n");
			    jTA_Log.append("----------------\n");
			    }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void botonDesconectar_actionPerformed(ActionEvent e) {	
		conectado = false;
		jB_Desconectar.setEnabled(conectado);
		jB_Conectar.setEnabled(!conectado);
		try {
			conn.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			conectado = true;
			jB_Conectar.setEnabled(!conectado);
			jB_Desconectar.setEnabled(conectado);
		}
		jTA_Log.append("----------------\n");
		jTA_Log.append("Desconectado al NXT "+ Calendar.HOUR_OF_DAY + ":" + 
				Calendar.MINUTE + ":" + Calendar.SECOND + "\n");
	    jTA_Log.append("================\n\n");
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTA_Log = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
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
        jTF_MotorA = new javax.swing.JTextField();
        jTF_MotorB = new javax.swing.JTextField();
        jTF_MotorC = new javax.swing.JTextField();
        jB_Salir = new javax.swing.JButton();
        jTF_Distancia = new javax.swing.JTextField();
        jL_Distancia = new javax.swing.JLabel();
        jTF_Color = new javax.swing.JTextField();
        jL_Color = new javax.swing.JLabel();
        jTF_Tacto = new javax.swing.JTextField();
        jL_Tacto = new javax.swing.JLabel();
        jcheck = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jB_Conectar = new javax.swing.JButton();
        jB_Desconectar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(".:: iRobot ::. - v 0.1.1 ("+serialVersionUID + ")");
        setResizable(false);

        jPanel1.setMaximumSize(new java.awt.Dimension(200, 400));
        jPanel1.setMinimumSize(new java.awt.Dimension(200, 400));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 400));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(180, 32767));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(180, 23));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(180, 96));

        jTA_Log.setColumns(16);
        jTA_Log.setEditable(false);
        jTA_Log.setRows(5);
        jTA_Log.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTA_Log);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setMaximumSize(new java.awt.Dimension(220, 400));
        jPanel2.setMinimumSize(new java.awt.Dimension(220, 400));
        jPanel2.setPreferredSize(new java.awt.Dimension(220, 400));

        jP_Joystick.setBackground(new java.awt.Color(153, 153, 153));
        jP_Joystick.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jP_Joystick.setMaximumSize(new java.awt.Dimension(200, 200));
        jP_Joystick.setMinimumSize(new java.awt.Dimension(200, 200));
        jP_Joystick.setPreferredSize(new java.awt.Dimension(200, 200));

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

        jTF_MotorA.setEditable(false);

        jTF_MotorB.setEditable(false);

        jTF_MotorC.setEditable(false);

        jB_Salir.setText("Salir");

        jTF_Distancia.setEditable(false);

        jL_Distancia.setText("Distancia:");

        jTF_Color.setEditable(false);

        jL_Color.setText("Color:");

        jTF_Tacto.setEditable(false);

        jL_Tacto.setText("Tacto:");

        jcheck.setText("Sensar?");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jL_MotorB, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_MotorB, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jL_MotorA, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_MotorA, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jP_Joystick, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jcheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addComponent(jB_Salir))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jL_Distancia, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                            .addComponent(jL_MotorC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                            .addComponent(jL_Color, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                            .addComponent(jL_Tacto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTF_Tacto, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTF_Color, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTF_Distancia, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTF_MotorC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jP_Joystick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_MotorA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jL_MotorA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_MotorB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jL_MotorB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_MotorC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jL_MotorC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_Distancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jL_Distancia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_Color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jL_Color))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_Tacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jL_Tacto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_Salir)
                    .addComponent(jcheck))
                .addContainerGap())
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(426, 41));

        jB_Conectar.setText("Conectar");
        jB_Conectar.setMaximumSize(new java.awt.Dimension(93, 23));
        jB_Conectar.setMinimumSize(new java.awt.Dimension(93, 23));
        jB_Conectar.setPreferredSize(new java.awt.Dimension(93, 23));

        jB_Desconectar.setText("Desconectar");
        jB_Desconectar.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jB_Conectar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jB_Desconectar, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_Conectar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_Desconectar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jB_Salir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
        
        jB_Conectar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonConectar_actionPerformed(e);
			}
		});
        
        jB_Desconectar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonDesconectar_actionPerformed(e);
			}
		});
        pack();
    }// </editor-fold>//GEN-END:initComponents

	public void this_windowClosing(WindowEvent e) {
		System.exit(0);
	}
	
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Ventana2_2 ventana = new Ventana2_2();
        		ventana.pack();
        		ventana.setVisible(true);
        		ventana.setLocationRelativeTo(null);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB_Conectar;
    private javax.swing.JButton jB_Desconectar;
    private javax.swing.JButton jB_Salir;
    private javax.swing.JLabel jL_Color;
    private javax.swing.JLabel jL_Distancia;
    private javax.swing.JLabel jL_MotorA;
    private javax.swing.JLabel jL_MotorB;
    private javax.swing.JLabel jL_MotorC;
    private javax.swing.JLabel jL_Tacto;
    private javax.swing.JPanel jP_Joystick;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTA_Log;
    private javax.swing.JTextField jTF_Color;
    private javax.swing.JTextField jTF_Distancia;
    private javax.swing.JTextField jTF_MotorA;
    private javax.swing.JTextField jTF_MotorB;
    private javax.swing.JTextField jTF_MotorC;
    private javax.swing.JTextField jTF_Tacto;
    private javax.swing.JCheckBox jcheck;
    // End of variables declaration//GEN-END:variables

    private NXTConnector conn;
    
   private boolean clik=false;
   private int posX =0;
   private int posY =0;
   UltrasonicSensor sensorUltrasonico;
   LightSensor sensorLuz;
   TouchSensor sensorTacto;
   Image imagen = null;;
   private boolean mejorado = true; 


}
