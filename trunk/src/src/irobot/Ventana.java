package irobot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Ventana extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 201104071340L;

	private ImageIcon iI_logo;
	private JPanel jP_Ppal = new JPanel();
	private JPanel jP_Conexion;
	private JPanel jP_Joystick;
	private JPanel jP_Motores;
	private JPanel jP_Log;
	
	private JButton jB_Conectar;
	private JButton jB_Desconectar;
	
	private JLabel jL_nombreLego;
	private JLabel jL_motorA;
	private JLabel jL_motorB;
	private JLabel jL_motorC;
	
	private JTextField jTF_nombreLego;
	private JTextField jTF_motorA;
	private JTextField jTF_motorB;
	private JTextField jTF_motorC;
	
	private JTextArea jTA_log;

	
	public Ventana() {
		super(".:: iRobot ::.   v "+serialVersionUID);
		
		//.setLayout(new CardLayout());
		jP_Ppal.setLayout(new BorderLayout());
		//iI_logo = new javax.swing.ImageIcon(getClass().getResource("/irobot.img/favicon.png"));
		//this.setIconImage(iI_logo.getImage()) ;
		//this.setSize(new Dimension(375,300));

		jP_Conexion = new JPanel();
		jP_Joystick = new JPanel();
		jP_Motores 	= new JPanel();
		jP_Log 		= new JPanel();
		
		/*
		 * Creamos Panel superior donde estaran los botones de conexion y el nombre del NXT
		 */
		
		jB_Conectar 	= new JButton("Conectar");
		jB_Desconectar 	= new JButton("Desconectar");
		jL_nombreLego 	= new JLabel("Ingrese el nombre del Lego NXT:");
		jTF_nombreLego 	= new JTextField();
		
		jP_Conexion.setLayout(new FlowLayout());//FlowLayout.LEFT));
		jP_Conexion.add(jB_Conectar);
		jP_Conexion.add(jB_Desconectar);
		jP_Conexion.add(jL_nombreLego);
		jP_Conexion.add(jTF_nombreLego);
		
		
		/*
		 * Creamos el Panel Derecho donde se iran anexando el log de las acciones
		 */
		
		jTA_log = new JTextArea();
		jTA_log.setEditable(false);
		
		jP_Log = new JPanel();
		JScrollPane jSP_log = new JScrollPane();
		
		jP_Log.setPreferredSize(new Dimension(200, 400));
		//jTA_log.setSize(new Dimension(200, 400));
		jSP_log.setPreferredSize(new Dimension(200, 400));
		jSP_log.add(jTA_log);
		
		
		jP_Log.add(jSP_log);
		
		
		/*
		 * Creamos el panel con la informacion de los motores
		 */
		
		jL_motorA = new JLabel("Motor A:");
		jL_motorB = new JLabel("Motor B:");
		jL_motorC = new JLabel("Motor C:");
		
		jTF_motorA = new JTextField();
		jTF_motorA.setEditable(false);
		
		jTF_motorB = new JTextField();
		jTF_motorB.setEditable(false);
		
		jTF_motorC = new JTextField();
		jTF_motorC.setEditable(false);
		
		JPanel jP_MotorA = new JPanel(new FlowLayout());
		JPanel jP_MotorB = new JPanel(new FlowLayout());
		JPanel jP_MotorC = new JPanel(new FlowLayout());
		
		jP_MotorA.add(jL_motorA);
		jP_MotorA.add(jTF_motorA);
		
		jP_MotorB.add(jL_motorB);
		jP_MotorB.add(jTF_motorB);
		
		jP_MotorC.add(jL_motorC);
		jP_MotorC.add(jTF_motorC);
		
		jP_Motores = new JPanel(new GridLayout(3, 1));
		jP_Motores.add(jP_MotorA);
		jP_Motores.add(jP_MotorB);
		jP_Motores.add(jP_MotorC);
		
		
		/*
		 * Creamos el panel del Joystick
		 */
		
		jP_Joystick = new JPanel();
		jP_Joystick.setBackground(Color.GRAY);
		jP_Joystick.setSize(200, 200);
		
		
		/*
		 * 
		 */
		
		JPanel jP_Centro = new JPanel(new GridLayout(1, 2));
		JPanel jP_Centro_Izq = new JPanel(new GridLayout(2,1));
		jP_Centro_Izq.add(jP_Joystick);
		jP_Centro_Izq.add(jP_Motores);
		
		jP_Centro.add(jP_Centro_Izq);
		jP_Centro.add(jP_Log);
		
		
		jP_Ppal.add(jP_Conexion,BorderLayout.NORTH);
		jP_Ppal.add(jP_Centro,BorderLayout.CENTER);
		
		
		
		jP_Ppal.setPreferredSize(new Dimension(400,400));

		this.getContentPane().add(jP_Ppal);
		//this.setSize(new Dimension(200,500));

		
		WindowAdapter oyenteCierraVentana = new java.awt.event.WindowAdapter() {
			public void windowClosing (WindowEvent e) {
				this_windowClosing(e);
			}
		};
		this.addWindowListener(oyenteCierraVentana);
		
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void this_windowClosing(WindowEvent e) {
		System.exit(0);
	}
	
	public void agregarLineaLog(String linea){
		this.jTA_log.append(linea);
	}
	
	public void setMotorA(String arg0){
		this.jTF_motorA.setText(arg0);
	}
	
	public void setMotorB(String arg0){
		this.jTF_motorB.setText(arg0);
	}
	
	public void setMotorC(String arg0){
		this.jTF_motorC.setText(arg0);
	}
	
}
