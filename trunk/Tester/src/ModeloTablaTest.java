import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ModeloTablaTest implements TableModel
    {
            final String[] columnNames = { "Test", "It", "Delta Grados", "Motores", "Tacto", "Ultrasonido", "Luz" };

            /** Lista con los datos. Cada elemento de la lista es una instancia de
             * Test */
            private List<Test> datos;

            /** Lista de suscriptores. El JTable será un suscriptor de este modelo de
             * datos */
            private Vector<TableModelListener> listeners;


            public ModeloTablaTest ()
            {
                this.datos = new ArrayList<Test>();
                this.listeners = new Vector<TableModelListener>();
            }


            public int getColumnCount() {
                    // Devuelve el número de columnas del modelo, que coincide con el
                    // número de datos que tenemos de cada Test.
                    return 7;
            }

            /** Returns the number of rows in the model. A
             * <code>JTable</code> uses this method to determine how many rows it
             * should display.  This method should be quick, as it
             * is called frequently during rendering.
             *
             * @return the number of rows in the model
             * @see #getColumnCount
             *
             */
            public int getRowCount() {
                    // Devuelve el número de personas en el modelo, es decir, el número
                    // de filas en la tabla.
                    if (datos == null)
                    {
                            return 0;
                    }
                    return datos.size();
            }

            /** Returns the value for the cell at <code>columnIndex</code> and
             * <code>rowIndex</code>.
             *
             * @param	rowIndex	the row whose value is to be queried
             * @param	columnIndex 	the column whose value is to be queried
             * @return	the value Object at the specified cell
             *
             */
            public Object getValueAt(int rowIndex, int columnIndex) {
                    Test unTest;

                    // Se obtiene el test de la fila indicada
                    unTest = (Test)(datos.get(rowIndex));

                    // Se obtiene el campo apropiado según el valor de columnIndex
                    switch (columnIndex)
                    {
                            case 0:
                                    return unTest.getIdTest();
                            case 1:
                                    return unTest.getIt();
                            case 2:
                                    return unTest.getDeltaGrados();
                            case 3:
                                    return unTest.getGradosMotores();
                            case 4: 
                                return (unTest.getTiempoTacto());
                            case 5:
                                return (unTest.getTiempoUltrasonido());
                            case 6:
                                return (unTest.getTiempoLuz());
                            default:
                                    return null;
                    }
            }

            /**
             * Borra del modelo el test en la fila indicada 
             */
            public void borraTest (int fila)
            {
                    // Se borra la fila 
                    datos.remove(fila);

                    // Y se avisa a los suscriptores, creando un TableModelEvent...
                    TableModelEvent evento = new TableModelEvent (this, fila, fila, 
                            TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);

                    // ... y pasándoselo a los suscriptores
                    avisaSuscriptores (evento);
            }

            /**
             * Añade un test al final de la tabla
             */
            public void agregarTest ()
            {
                    // Avisa a los suscriptores creando un TableModelEvent...
                    if (datos==null){
                            datos = new ArrayList<Test>();
                    }
                    TableModelEvent evento;
                    evento = new TableModelEvent (this, this.getRowCount()-1,
                            this.getRowCount()-1, TableModelEvent.ALL_COLUMNS,
                            TableModelEvent.INSERT);

                    // ... y avisando a los suscriptores
                    avisaSuscriptores (evento);
            }

            public void agregarTest (Test nuevoTest)
            {
                    // Añade el test al modelo 
                    datos.add (nuevoTest);

                    // Avisa a los suscriptores creando un TableModelEvent...
                    TableModelEvent evento;
                    evento = new TableModelEvent (this, this.getRowCount()-1,
                            this.getRowCount()-1, TableModelEvent.ALL_COLUMNS,
                            TableModelEvent.INSERT);

                    // ... y avisando a los suscriptores
                    avisaSuscriptores (evento);
            }

            public void borraTest (Test unTest)
            {
                    // Elimina el Test del modelo 
                    datos.remove(unTest);

                    // Avisa a los suscriptores creando un TableModelEvent...
                    TableModelEvent evento;
                    /*
                    evento = new TableModelEvent (this, this.getRowCount()+1,
                            this.getRowCount()+1, TableModelEvent.ALL_COLUMNS,
                            TableModelEvent.DELETE);
                    */		
                    evento = new TableModelEvent (this, this.getRowCount()-1,
                            this.getRowCount()-1, TableModelEvent.ALL_COLUMNS,
                            TableModelEvent.DELETE);

                    // ... y avisando a los suscriptores
                    avisaSuscriptores (evento);
            }

            public void borraTest ()
            {

                    // Avisa a los suscriptores creando un TableModelEvent...
                    TableModelEvent evento;
                    /*
                    evento = new TableModelEvent (this, this.getRowCount()+1,
                            this.getRowCount()+1, TableModelEvent.ALL_COLUMNS,
                            TableModelEvent.DELETE);
                    */		
                    evento = new TableModelEvent (this, this.getRowCount()-1,
                            this.getRowCount()-1, TableModelEvent.ALL_COLUMNS,
                            TableModelEvent.DELETE);

                    // ... y avisando a los suscriptores
                    avisaSuscriptores (evento);
            }

        public void modificarTest(Test unTest, int fila){
            datos.set(fila, unTest);//add(fila,unTest);

            //Avisa a los suscriptores creando un TableModelEvent...
             TableModelEvent evento;
             evento = new TableModelEvent (this, this.getRowCount()-1,
                     this.getRowCount()-1,TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE);

             // ... y avisando a los suscriptores
             avisaSuscriptores (evento);
        }

//        public void modificarTest(){
//
//                //Avisa a los suscriptores creando un TableModelEvent...
//                datos = new ArrayList<Test>();
//                 TableModelEvent evento;
//                 evento = new TableModelEvent (this, this.getRowCount()-1,
//                         this.getRowCount()-1, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE);
//
//                 // ... y avisando a los suscriptores
//                 avisaSuscriptores (evento);
//        }
        
        /** Adds a listener to the list that is notified each time a change
         * to the data model occurs.
         * @param	l		the TableModelListener
         */
        public void addTableModelListener(TableModelListener l) {
                // Añade el suscriptor a la lista de suscriptores
                listeners.add (l);
        }

        /** Returns the most specific superclass for all the cell values
         * in the column.  This is used by the <code>JTable</code> to set up a
         * default renderer and editor for the column.
         * @param columnIndex  the index of the column
         * @return the common ancestor class of the object values in the model.
         */
        public Class getColumnClass(int columnIndex) {
                // Devuelve la clase que hay en cada columna.
                switch (columnIndex)
                {
                        case 0:
                                return Integer.class;
                        case 1: 
                                return Integer.class;
                        case 2: 
                                return Integer.class;
                        case 3:
                                return Float.class;
                        case 4:
                                return Float.class;
                        case 5:
                                return Float.class;
                        case 6:
                                return Float.class;
                        default:
                                return Object.class;
                }
        }

        /** Returns the name of the column at <code>columnIndex</code>.  This is used
         * to initialize the table's column header name.  Note: this name does
         * not need to be unique; two columns in a table can have the same name.
         * @param	columnIndex	the index of the column
         * @return  the name of the column
         */
        public String getColumnName(int columnIndex) 
        {
                // Devuelve el nombre de cada columna. Este texto aparecerá en la
                // cabecera de la tabla.
                switch (columnIndex)
                {
                        case 0:
                                return "Test";
                        case 1:
                                return "It";
                        case 2:
                                return "Delta Gerados";
                        case 3:
                                return "Motores";
                        case 4:
                                return "Tacto";
                        case 5:
                                return "Ultrasonido";
                        case 6:
                                return "Luz";
                        default:
                                return null;
                }
        }

        /** Returns true if the cell at <code>rowIndex</code> and
         * <code>columnIndex</code>
         * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
         * change the value of that cell.
         * @param	rowIndex	the row whose value to be queried
         * @param	columnIndex	the column whose value to be queried
         * @return	true if the cell is editable
         * @see #setValueAt
         */
        public boolean isCellEditable(int rowIndex, int columnIndex) {
                // Permite que la celda sea editable.
                return false;
        }

        /** Removes a listener from the list that is notified each time a
         * change to the data model occurs.
         *
         * @param	l		the TableModelListener
         *
         */
        public void removeTableModelListener(TableModelListener l) {
                // Elimina los suscriptores.
                listeners.remove(l);
        }

        /** Sets the value in the cell at <code>columnIndex</code> and
         * <code>rowIndex</code> to <code>aValue</code>.
         * @param	aValue		 the new value
         * @param	rowIndex	 the row whose value is to be changed
         * @param	columnIndex 	 the column whose value is to be changed
         * @see #getValueAt
         * @see #isCellEditable
         */
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
        {
                // Obtiene el Test de la fila indicada
                Test unTest;
                unTest = (Test)(datos.get(rowIndex));

                // Cambia el campo de Test que indica columnIndex, poniendole el 
                // aValue que se nos pasa.
                switch (columnIndex)
                {
                        case 0:
                                unTest.setIdTest((Integer)aValue);
                                break;
                        case 1:
                                unTest.setIt((Integer)aValue);//(Integer.parseInt(String.valueOf((Integer)aValue)));
                                break;
                        case 2:
                                unTest.setDeltaGrados((Integer)aValue);
                                break;
                        case 3:
                                unTest.setGradosMotores((Float)aValue);
                                break;
                        case 4:
                                unTest.setTiempoTacto((Float)aValue);
                                break;
                        case 5:
                                unTest.setTiempoUltrasonido((Float)aValue);
                                break;
                        case 6:
                                unTest.setTiempoLuz((Float)aValue);
                                break;
                        default:
                                break;
                }

                // Avisa a los suscriptores del cambio, creando un TableModelEvent ...
                TableModelEvent evento = new TableModelEvent (this, rowIndex, rowIndex, 
                        columnIndex);

                // ... y pasándoselo a los suscriptores.
                avisaSuscriptores (evento);
        }

        /**
         * Pasa a los suscriptores el evento.
         */
        private void avisaSuscriptores (TableModelEvent evento)
        {
                int i;

                // Bucle para todos los suscriptores en la lista, se llama al metodo
                // tableChanged() de los mismos, pasándole el evento.
                for (i=0; i<listeners.size(); i++)
                        ((TableModelListener)listeners.get(i)).tableChanged(evento);
        }

        public Test darTest(int fila){
                return (Test)this.datos.get(fila);
        }

//        //public Object
//        public void actualizar(){
//                datos=this.sistema.listarAlumnos(turno);
//                //Avisa a los suscriptores creando un TableModelEvent...
//                         TableModelEvent evento;
//                         evento = new TableModelEvent (this, this.getRowCount()-1,
//                                 this.getRowCount()-1,TableModelEvent.ALL_COLUMNS,
//                                 TableModelEvent.UPDATE);
//                         // ... y avisando a los suscriptores
//                         avisaSuscriptores (evento);
//        }

    }