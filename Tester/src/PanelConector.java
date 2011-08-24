
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Calendar;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.remote.NXTCommand;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTConnector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * panelConector.java
 *
 * Created on 22/07/2011, 17:44:06
 */



/**
 *
 * @author Victor
 */
public class PanelConector extends javax.swing.JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4318528383177385386L;
	/** Creates new form panelConector */
    public PanelConector() {
        initComponents();
        botonConectar.setEnabled(true);
        botonDesconectar.setEnabled(false);
    }

    static public boolean estaConectado(){
        return PanelConector.conectado;
    }
    
    static public UltrasonicSensor getSensorUltrasonico(){
        return PanelConector.sensorUltrasonico;
    };
    
    static public LightSensor getSensorLuz(){
        return PanelConector.sensorLuz;
    };
    
    static public TouchSensor getSensorTacto(){
        return PanelConector.sensorTacto;
    };

    public void botonConectar_actionPerformed(ActionEvent e) {
            PanelConector.conectado = true;
            botonConectar.setEnabled(!PanelConector.conectado);
            botonDesconectar.setEnabled(PanelConector.conectado);

            conn = new NXTConnector();

        if (!conn.connectTo("NXT", NXTComm.LCP)) {
            System.err.println("Conexion Fallida");
            jlEstado.setText("Conexion Fallida");
            //System.exit(1);
            PanelConector.conectado = false;
            botonDesconectar.setEnabled(PanelConector.conectado);
            botonConectar.setEnabled(!PanelConector.conectado);
        }
        NXTCommand.getSingleton().setNXTComm(conn.getNXTComm());
        Motor.A.resetTachoCount();
        Motor.B.resetTachoCount();
        sensorUltrasonico   = new UltrasonicSensor(SensorPort.S1);
        sensorLuz           = new LightSensor(SensorPort.S2);
        sensorTacto         = new TouchSensor(SensorPort.S3);
        jlEstado.setText("Conectado: "+Calendar.HOUR_OF_DAY + ":" +
                    Calendar.MINUTE + ":" + Calendar.SECOND);

//        jTA_Log.setText("Conectado al NXT "+ Calendar.HOUR_OF_DAY + ":" +
//                    Calendar.MINUTE + ":" + Calendar.SECOND + "\n");
//        jTA_Log.append("----------------\n");
    }

    public void botonDesconectar_actionPerformed(ActionEvent e) {
            PanelConector.conectado = false;
            botonDesconectar.setEnabled(PanelConector.conectado);
            botonConectar.setEnabled(!PanelConector.conectado);
            try {
                    conn.close();
            } catch (IOException e1) {
                    e1.printStackTrace();
                    PanelConector.conectado = true;
                    botonConectar.setEnabled(!PanelConector.conectado);
                    botonDesconectar.setEnabled(PanelConector.conectado);
            }
            jlEstado.setText("Desconectado: "+Calendar.HOUR_OF_DAY + ":" +
                    Calendar.MINUTE + ":" + Calendar.SECOND);

//            jTA_Log.append("----------------\n");
//            jTA_Log.append("Desconectado al NXT "+ Calendar.HOUR_OF_DAY + ":" +
//                            Calendar.MINUTE + ":" + Calendar.SECOND + "\n");
//        jTA_Log.append("================\n\n");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonDesconectar = new javax.swing.JButton();
        botonConectar = new javax.swing.JButton();
        jlEstado = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setPreferredSize(new java.awt.Dimension(681, 47));

        botonDesconectar.setText("Desconectar");

        botonConectar.setText("Conectar");

        jlEstado.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jlEstado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlEstado.setText("Estado");
        jlEstado.setToolTipText("Estado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonConectar)
                .addGap(18, 18, 18)
                .addComponent(botonDesconectar)
                .addGap(18, 18, 18)
                .addComponent(jlEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonConectar)
                    .addComponent(botonDesconectar)
                    .addComponent(jlEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        botonConectar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonConectar_actionPerformed(e);
			}
		});
        
        botonDesconectar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonDesconectar_actionPerformed(e);
			}
		});
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonConectar;
    private javax.swing.JButton botonDesconectar;
    private javax.swing.JLabel jlEstado;
    // End of variables declaration//GEN-END:variables

    static private boolean conectado = false;
    private NXTConnector conn;

    static private UltrasonicSensor sensorUltrasonico;
    static private LightSensor sensorLuz;
    static private TouchSensor sensorTacto;
}