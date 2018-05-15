/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pueblosdeespaña78;

import com.sun.imageio.plugins.png.RowFilter;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import utils78.OperaBD;

public class PueblosDeEspaña78 extends JFrame implements ItemListener {

    JComboBox ComboComunidad, Comboprovincia;
    JTabbedPane pestañas;
    JPanel pueblos, municipales, mapa;
    static OperaBD operaBD;
    JScrollPane listaPueblos;
    JTable tablaPueblos;

    public static void main(String[] args) {
        operaBD = new OperaBD("jdbc:oracle:thin:@localhost:1521:XE",
                "oracle.jdbc.driver.OracleDriver",
                "catastro", "catastro");

        new PueblosDeEspaña78("Pueblos de España");
    }

    public PueblosDeEspaña78(String title) throws HeadlessException {
        super(title);

        pestañas = new JTabbedPane();
        pueblos = new JPanel(new BorderLayout());
        municipales = new JPanel(new BorderLayout());
        mapa = new JPanel(new BorderLayout());
        /*JPanel      pinferior   = new JPanel();
        mapa.add(pinferior, BorderLayout.SOUTH);*/

        pestañas.addTab("Pueblos...", pueblos);
        pestañas.addTab("municipales...", municipales);
        pestañas.addTab("mapa...", mapa);

        añadeComponentesPueblos();
        this.setContentPane(pestañas);

        this.setVisible(true);
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

    }// consturctor....

    void añadeComponentesPueblos() {
        //----- Pestaña Pueblos...............................................
        //--- Norte............
        JLabel comunidad = new JLabel("Comunidad->");
        ComboComunidad = new JComboBox();
        JLabel provincia = new JLabel("Provincias->");
        Comboprovincia = new JComboBox();
        ComboComunidad.addItemListener(this);
        cargaCombo(ComboComunidad);
        Comboprovincia.addItemListener(this);
        /*CargaComboOracle (ComboComunidad);
        ComboComunidad.addActionListener(this);*/

        JPanel nortePueblos = new JPanel();
        nortePueblos.add(comunidad);
        nortePueblos.add(ComboComunidad);
        nortePueblos.add(provincia);
        nortePueblos.add(Comboprovincia);

        pueblos.add(nortePueblos, BorderLayout.NORTH);

        //--- Centro Pueblos......................................
        listaPueblos = new JScrollPane();
        pueblos.add(listaPueblos, BorderLayout.CENTER);
        tablaPueblos = cargaPobTab("select * from poblaciones");
        listaPueblos.setViewportView(tablaPueblos);

        //pestañas.addTab("Pueblos...", pueblos);
    }//añadeComponentesPueblos

    void cargaCombo(JComboBox combo) {

        try {
            ResultSet comu = operaBD.lanzaSql("select * from comunidades");

            while (comu.next()) {
                //combo.addItem(comu.getString("nombre"));
                combo.addItem(new Comunidades(comu.getString("nombre"), comu.getString("codigo")));
            }
        } catch (SQLException ex) {
            System.out.println("Error cargando el combo ->" + ex);
        }

    }
//-------------------------------------------------

    void cargaComboProv(JComboBox combo, String abr) {

        try {
            ResultSet prov = operaBD.lanzaSql("select * from provincias "
                    + "where comunidad='" + abr + "'");
            //combo.removeAllItems();
            combo.setModel(new DefaultComboBoxModel());
            while (prov.next()) {
                //combo.addItem(prov.getString("provincia"));
                combo.addItem(new ProvinciasaMano(prov.getBigDecimal("idprovincia"), prov.getString("provincia")));
            }
        } catch (SQLException ex) {
            System.out.println("Error cargando el combo ->" + ex);
        }
    }
//------------------------------------------------------

    JTable cargaPobTab(String consulta) {
        consulta = "select * from poblaciones";
        int numcolum = 0;
        JTable tabla=null;
        try {

            ResultSet rs = operaBD.lanzaSql(consulta);
            ResultSetMetaData rsm = rs.getMetaData();//aqui ya tenemos los metadatos
            numcolum = rsm.getColumnCount();//columnas de la tabla

            Object[] columnNames = new Object[numcolum];

            for (int i = 0; i < numcolum; i++) {
                columnNames[i] = rsm.getColumnLabel(i+1);//tenemos el nombre de la columna
                //aqui metes todos los nombres en un array, por eso no te hace falta ir procesando uno a uno
            }
            DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);

            //añadir las filas
            while (rs.next()) {
                Object[] fila = new Object[numcolum];
                for (int i = 0; i < numcolum; i++) {
                    fila[i] = rs.getObject(i+1);
                }
                dtm.addRow(fila);
            }
            
            tabla = new JTable(dtm);
            //para ordenar
            TableRowSorter ordenar = new TableRowSorter(dtm);
            tabla.setRowSorter(ordenar);

        } catch (SQLException ex) {
            System.out.println("Error al cargar la tabla-> " + ex);
        } finally {
            return tabla;
        }
    }

//------------------------------------------------------
    @Override
    public void itemStateChanged(ItemEvent e) {
        Comunidades comu;
        ProvinciasaMano prov;
        
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getSource() == ComboComunidad) {
                comu = (Comunidades) ComboComunidad.getSelectedItem();
                cargaComboProv(Comboprovincia, comu.getCodigo());
            }
            if (e.getSource() == Comboprovincia) {
                prov = (ProvinciasaMano) Comboprovincia.getSelectedItem();
                //listaPueblos.setViewportView(cargaPobTab());
                TableRowSorter ordenar = (TableRowSorter) tablaPueblos.getRowSorter();
                ordenar.setRowFilter(javax.swing.RowFilter.numberFilter(javax.swing.RowFilter.ComparisonType.EQUAL, prov.getIdprovincia(), 1));
            }
        }
    }//isch...

}
