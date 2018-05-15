/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pueblosdeespaña78;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "COMUNIDADES")
@NamedQueries({
    @NamedQuery(name = "Comunidades.findAll", query = "SELECT c FROM Comunidades c")})
public class Comunidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "NOMBRE")
    private String nombre;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;

    public Comunidades() {
    }

    public Comunidades(String codigo) {
        this.codigo = codigo;
    }

    public Comunidades(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

     
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comunidades)) {
            return false;
        }
        Comunidades other = (Comunidades) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;//+"->"+codigo;
    }
    
}
