
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import lejos.nxt.Motor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Performance.java
 * Created on 15/07/2011, 21:20:57
 */



/**
 * @author Victor
 */
public class Performance extends javax.swing.JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7713734690619344373L;
	/** Creates new form performance */
    public Performance() {
        tests = new ArrayList<Test>();
        initComponents();
        jsGrados.setValue(10);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btNuevoTest = new javax.swing.JButton();
        btFinalizar = new javax.swing.JButton();
        btExportar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btAvanzar = new javax.swing.JButton();
        btSensar = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jsGrados = new javax.swing.JSpinner();

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btNuevoTest.setText("Nuevo Test");
        btNuevoTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNuevoTestActionPerformed(evt);
            }
        });

        btFinalizar.setText("Finalizar Test");
        btFinalizar.setEnabled(false);
        btFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFinalizarActionPerformed(evt);
            }
        });

        btExportar.setText("Exportar");
        btExportar.setEnabled(false);
        btExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
				btExportarActionPerformed(evt);
            }
        });

        jTable1.setModel(modeloTabla);
        jTable1.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 248));

        btAvanzar.setText("Avanzar");
        btAvanzar.setEnabled(false);
        btAvanzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAvanzarActionPerformed(evt);
            }
        });

        btSensar.setText("Sensar");
        btSensar.setEnabled(false);
        btSensar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSensarActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/irobot.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jsGrados, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btAvanzar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btSensar, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsGrados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btAvanzar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btSensar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btNuevoTest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btFinalizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btExportar)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btNuevoTest)
                    .addComponent(btFinalizar)
                    .addComponent(btExportar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nuevoTest(){
        itTest = 1;
        btFinalizar.setEnabled(true);
        btSensar.setEnabled(true);
        btExportar.setEnabled(false);
        btNuevoTest.setEnabled(false);
        delta = (Integer)jsGrados.getValue();
        /* Despu�s de medir cuantos � equivalen 10 cm volver a descomentar */
//        jsGrados.setEnabled(false); 
        currentTest = new Test(idTest,itTest,delta);
        tests.add(currentTest);
        ((ModeloTablaTest)jTable1.getModel()).agregarTest(currentTest);
    }
    private void btNuevoTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNuevoTestActionPerformed

        if ((Integer)jsGrados.getValue() != 0){
            nuevoTest();
        } else {
           JOptionPane.showMessageDialog(null, "No puede generar Test con \"Delta Grados\" en 0 (cero).\nPor favor modifique el valor!"
            ,"Importante",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btNuevoTestActionPerformed

    private void btSensarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSensarActionPerformed
        currentTest.setTiempoLuz(idTest * itTest);
        currentTest.setTiempoTacto(2 * idTest * itTest);
        currentTest.setTiempoUltrasonido(3 * idTest * itTest);
        ((ModeloTablaTest)jTable1.getModel()).modificarTest(currentTest, jTable1.getModel().getRowCount()-1);
//        Thread t = new Thread(new Runnable() {
//	    	public void run() {
//    			while (true){
//    				if ((clik) && (conectado)){
//    					System.out.println("conectado: "+conectado+", clik: "+clik);
//    					imprimir();
//    					moverMotores();
//    					if (jcheck.isSelected()){
//    						actualizarSensores();
//    					}
//    				}
//    				try {
//    					if (mejorado){
//    						Thread.sleep(400); //Opcion 2;
//    					} else {
//    						Thread.sleep(1); // Opcion 1;
//    					}
//						
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//    			}
//    		}
//	    }, "Thread para capturar el clik del mouse en el joystick y mover los motores");
//	    t.start();
        btAvanzar.setEnabled(true);
        btSensar.setEnabled(false);
        btFinalizar.setEnabled(true);
    }//GEN-LAST:event_btSensarActionPerformed

    private void btAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAvanzarActionPerformed
        itTest++;
        btAvanzar.setEnabled(false);
        btSensar.setEnabled(true);
//        Motor.A.rotate(delta);
//        Motor.B.rotate(delta);
        currentTest = new Test(idTest,itTest,delta);
        tests.add(currentTest);
        ((ModeloTablaTest)jTable1.getModel()).agregarTest(currentTest);
    }//GEN-LAST:event_btAvanzarActionPerformed

    private void btFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFinalizarActionPerformed
        idTest++;
        btNuevoTest.setEnabled(true);
        btFinalizar.setEnabled(false);
        btAvanzar.setEnabled(false);
        btSensar.setEnabled(false);
        btExportar.setEnabled(true);
    }//GEN-LAST:event_btFinalizarActionPerformed

    private void btExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExportarActionPerformed
        Iterator iter = tests.iterator();
        while (iter.hasNext()){
          System.out.println(iter.next());
        }
        JSONArray jsonArray = new JSONArray(tests);
        System.out.println("====Array : "+jsonArray);

        Map map = new HashMap();
        map.put("testPerformance", jsonArray);

        JSONObject jsonObject = new JSONObject(map);
        System.out.println("==== json :"+jsonObject);
        
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH) +1;
        int anio = calendario.get(Calendar.YEAR);
        int hora =calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        int segundos = calendario.get(Calendar.SECOND);
        
        BufferedWriter f;
		try {
			f = new BufferedWriter(new FileWriter("c:/resultados.txt"));
			f.write("Test Generado el "+ dia + "/" + mes + "/" + anio + " a las: " + hora + ":" +minutos + ":" +segundos);
	        f.newLine();
	        f.write(jsonObject.toString());
	        f.flush();
	        f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }//GEN-LAST:event_btExportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAvanzar;
    private javax.swing.JButton btExportar;
    private javax.swing.JButton btFinalizar;
    private javax.swing.JButton btNuevoTest;
    private javax.swing.JButton btSensar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JSpinner jsGrados;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables

    private List<Test> tests;
    private Test currentTest;
    private int idTest=1;
    private int itTest=1;
    private int delta=0;
    
    private ModeloTablaTest modeloTabla = new ModeloTablaTest();
}
