/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package igu;

import Control.Censador.CensadorControladora;
import Control.Persona.PersonaControladora;
import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logica.Censador;
import logica.PersonaCensada;
import controlador.DAO.DaoImplement;
import controlador.Persona.PersonaControl;

import vista.lista.tablas.ModeloTablaPersonaCensada;

/**
 *
 * @author jsbal
 */
public class FrmRegistroPersonaCensada extends javax.swing.JFrame {

    private PersonaControladora personaControladora = new PersonaControladora();
    private PersonaControl control = new PersonaControl();
    
    private ModeloTablaPersonaCensada modelotabla = new ModeloTablaPersonaCensada();
    private int criterioSeleccionado = 0; // 0: Nombre, 1: Apellido, 2: EstadoCivil, 3: Edad
    private boolean ordenAscendente = true; // Indicador de orden: true para ascendente, false para descendente

    private void cargarVista() {
        int fila = tablaPersonas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoja un registro de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                personaControladora.setPersona(modelotabla.getPersonas().getInfo(fila));
                txtNombre.setText(personaControladora.getPersona().getNombre());
                txtDni.setText(personaControladora.getPersona().getDni());
                txtApellido.setText(personaControladora.getPersona().getApellido());
                cbxEstadoCivil.setSelectedItem(personaControladora.getPersona().getEstadoCivil());
                cbxEstadoEmocional.setSelectedItem(personaControladora.getPersona().getEstadoEmocional());
                txtApellido.setText(personaControladora.getPersona().getApellido());

            } catch (EmptyException ex) {
                Logger.getLogger(FrmRegistroPersonaCensada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cargarPersonas(DynamicList<PersonaCensada> personas) {
        modelotabla.setPersonas(personas);
    }

    public Boolean verificar() {
        return (!txtNombre.getText().trim().isEmpty()
                && !txtDni.getText().trim().isEmpty()
                && !txtMotivo.getText().trim().isEmpty()
                && !txtApellido.getText().trim().isEmpty());
    }

    private void cargarTabla() {
        modelotabla.setPersonas(control.all());
        tablaPersonas.setModel(modelotabla);
        tablaPersonas.updateUI();
    }
    private void quickSort(DynamicList<PersonaCensada> personas, int low, int high) throws EmptyException {
    if (low < high) {
        int pi = partition(personas, low, high);

        quickSort(personas, low, pi - 1);
        quickSort(personas, pi + 1, high);
    }
}

private int partition(DynamicList<PersonaCensada> personas, int low, int high) throws EmptyException {
    String pivot = obtenerValor(personas.getInfo(high)); // Obtener el valor pivote según el criterio seleccionado
    int i = (low - 1);

    for (int j = low; j < high; j++) {
        String currentValue = obtenerValor(personas.getInfo(j)); // Obtener el valor actual del elemento

        // Comparar según el criterio y orden ascendente/descendente
        if ((ordenAscendente && currentValue.compareTo(pivot) < 0) || (!ordenAscendente && currentValue.compareTo(pivot) > 0)) {
            i++;

            PersonaCensada temp = personas.getInfo(i);
            personas.setInfo(i, personas.getInfo(j));
            personas.setInfo(j, temp);
        }
    }

    PersonaCensada temp = personas.getInfo(i + 1);
    personas.setInfo(i + 1, personas.getInfo(high));
    personas.setInfo(high, temp);

    return i + 1;
}
private void ordenarTabla(boolean ascendente) throws EmptyException {
    criterioSeleccionado = cbxCriterio.getSelectedIndex();
    ordenAscendente = ascendente;

    DynamicList<PersonaCensada> personas = modelotabla.getPersonas();
    quickSort(personas, 0, personas.getLenght() - 1);

    // Actualizar la tabla con los datos ordenados
    modelotabla.setPersonas(personas);
    tablaPersonas.setModel(modelotabla);
    tablaPersonas.updateUI();
}

private String obtenerValor(PersonaCensada persona) {
    switch (criterioSeleccionado) {
        case 0:
            return persona.getNombre();
        case 1:
            return persona.getApellido();
        case 2:
            return persona.getEstadoCivil();
        case 3:
            return String.valueOf(persona.getEdad());
        default:
            return "";
    }
}

    
   

    private void modificar() {
        if (verificar()) {
            {
                personaControladora.getPersona().setNombre(txtNombre.getText());
                personaControladora.getPersona().setApellido(txtApellido.getText());
                try {
                    // Obtener el texto del JTextField
                    String textoEdad = txtEdad.getText();

                    // Convertir el texto a un entero
                    int nuevaEdad = Integer.parseInt(textoEdad);

                    // Establecer la nueva edad en el objeto Persona a través de la personaControladora
                    personaControladora.getPersona().setEdad(nuevaEdad);

                    // Otros procedimientos después de establecer la edad
                } catch (NumberFormatException e) {
                    // Manejar el caso en el que el texto no sea un número válido
                    // Por ejemplo, mostrar un mensaje de error o realizar alguna acción adecuada
                    System.err.println("Error: Ingrese un número válido para la edad");
                }

                personaControladora.getPersona().setDni(txtDni.getText());
                personaControladora.getPersona().setEstadoCivil((String) cbxEstadoCivil.getSelectedItem());
                personaControladora.getPersona().setCensador((Censador) cbxCensador.getSelectedItem());
                personaControladora.getPersona().setFecha(txtFecha.getDateFormatString()) ;

                if (personaControladora.guardar()) {
                    control.merge(personaControladora.getPersona(), tablaPersonas.getSelectedRow());
                    JOptionPane.showMessageDialog(null, "Datos guardados");
                    cargarTabla();
                    limpiar();
                    personaControladora.setPersona(null);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar, hubo un error");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Falta llenar campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardar() throws EmptyException {
        if (verificar()) {
            personaControladora.getPersona().setNombre(txtNombre.getText());
            personaControladora.getPersona().setApellido(txtApellido.getText());
            personaControladora.getPersona().setDni(txtDni.getText());
            personaControladora.getPersona().setEstadoCivil((String) cbxEstadoCivil.getSelectedItem());
            personaControladora.getPersona().setEstadoEmocional((String) cbxEstadoEmocional.getSelectedItem());
            personaControladora.getPersona().setCensador((Censador) cbxCensador.getSelectedItem());
            personaControladora.getPersona().setFecha(txtFecha.getDateFormatString());
            try {
                // Obtener el texto del JTextField
                String textoEdad = txtEdad.getText();

                // Convertir el texto a un entero
                int nuevaEdad = Integer.parseInt(textoEdad);

                // Establecer la nueva edad en el objeto Persona a través de la personaControladora
                personaControladora.getPersona().setEdad(nuevaEdad);

                // Otros procedimientos después de establecer la edad
            } catch (NumberFormatException e) {
                // Manejar el caso en el que el texto no sea un número válido
                // Por ejemplo, mostrar un mensaje de error o realizar alguna acción adecuada
                System.err.println("Error: Ingrese un número válido para la edad");
            }

            if (personaControladora.guardar()) {
                control.persist(personaControladora.getPersona());
                JOptionPane.showMessageDialog(null, "Datos guardados");
                cargarTabla();
                limpiar();

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar, hubo un error");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Falta llenar campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {

        tablaPersonas.clearSelection();
        txtNombre.setText("");
        txtDni.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtMotivo.setText("");

        cargarTabla();
        personaControladora.setPersona(null);

    }

    public void cargarCensadoresEnComboBox(JComboBox cbx) {
        DaoImplement<Censador> censadorDao = new DaoImplement<>(Censador.class);

        cbx.removeAllItems();
        try {
            DynamicList<Censador> censadoresList = censadorDao.all();

            if (censadoresList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lista de censadores vacía");
            } else {
                for (int i = 0; i < censadoresList.getLenght(); i++) {
                    Censador censador = censadoresList.getInfo(i);
                    cbx.addItem(censador);
                }
            }
        } catch (EmptyException ex) {
            Logger.getLogger(FrmRegistroPersonaCensada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FrmRegistroPersonaCensada() throws EmptyException {
        initComponents();
        limpiar();
        cargarCensadoresEnComboBox(cbxCensador);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtDni = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnSeleccionar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPersonas = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbxEstadoCivil = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbxEstadoEmocional = new javax.swing.JComboBox<>();
        txtFecha = new com.toedter.calendar.JDateChooser();
        txtMotivo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbxCensador = new javax.swing.JComboBox<>();
        cbxCriterio = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        btnAscendente = new javax.swing.JButton();
        btnDescendente = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("REGISTRO PERSONA");

        jLabel2.setText("NOMBRE:");

        jLabel3.setText("APELLIDO:");

        jLabel4.setText("DNI:");

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnSeleccionar.setText("SELECCIONAR");
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });

        tablaPersonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaPersonas);

        jLabel5.setText("PERSONAS CENSADAS");

        jLabel10.setText("EDAD:");

        jLabel9.setText("ESTADO CIVIL:");

        cbxEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Soltero", "Casado", "Divorciado", " " }));

        jLabel6.setText("MOTIVO:");

        jLabel7.setText("FECHA:");

        jLabel8.setText("ESTADO EMOCIONAL:");

        cbxEstadoEmocional.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Felicidad", "Tristeza", "Ira", "Miedo", "Ansiedad" }));

        jLabel11.setText("CENSADOR A CARGO:");

        cbxCensador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxCriterio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Apellido", "Estado Civil", "Edad" }));
        cbxCriterio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCriterioActionPerformed(evt);
            }
        });

        jLabel12.setText("CRITERIO");

        btnAscendente.setText("ASCENDENTE");
        btnAscendente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAscendenteActionPerformed(evt);
            }
        });

        btnDescendente.setText("DESCENDENTE");
        btnDescendente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescendenteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel3)
                                                .addComponent(jLabel4))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtDni, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                                .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                                .addComponent(txtEdad))))
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbxEstadoCivil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtMotivo)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbxEstadoEmocional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(cbxCensador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnGuardar)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addComponent(btnModificar)
                                .addGap(18, 18, 18)
                                .addComponent(btnSeleccionar))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jLabel12)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel13))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAscendente)
                                .addGap(18, 18, 18)
                                .addComponent(btnDescendente))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9)
                    .addComponent(cbxEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(cbxCensador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(cbxEstadoEmocional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar)
                    .addComponent(btnSeleccionar))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(cbxCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAscendente)
                    .addComponent(btnDescendente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            guardar();
        } catch (EmptyException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
        cargarVista();
    }//GEN-LAST:event_btnSeleccionarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificar();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void cbxCriterioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCriterioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxCriterioActionPerformed

    private void btnAscendenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAscendenteActionPerformed
        try {
            ordenarTabla(false);
        } catch (EmptyException ex) {
            Logger.getLogger(FrmRegistroPersonaCensada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAscendenteActionPerformed

    private void btnDescendenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescendenteActionPerformed

        try {
            ordenarTabla(true);
        } catch (EmptyException ex) {
            Logger.getLogger(FrmRegistroPersonaCensada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDescendenteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroPersonaCensada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroPersonaCensada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroPersonaCensada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroPersonaCensada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FrmRegistroPersonaCensada().setVisible(true);
                } catch (EmptyException ex) {
                    Logger.getLogger(FrmRegistroPersonaCensada.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAscendente;
    private javax.swing.JButton btnDescendente;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JComboBox<String> cbxCensador;
    private javax.swing.JComboBox<String> cbxCriterio;
    private javax.swing.JComboBox<String> cbxEstadoCivil;
    private javax.swing.JComboBox<String> cbxEstadoEmocional;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaPersonas;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtEdad;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtMotivo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
