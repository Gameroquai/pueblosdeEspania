/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pueblosdeespaña78;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Gamero
 */
@Entity
@Table(name = "PROVINCIAS")
@NamedQueries({
    @NamedQuery(name = "Provincias.findAll", query = "SELECT p FROM Provincias p")
    , @NamedQuery(name = "Provincias.findByIdprovincia", query = "SELECT p FROM Provincias p WHERE p.idprovincia = :idprovincia")
    , @NamedQuery(name = "Provincias.findByProvincia", query = "SELECT p FROM Provincias p WHERE p.provincia = :provincia")
    , @NamedQuery(name = "Provincias.findByProvinciaseo", query = "SELECT p FROM Provincias p WHERE p.provinciaseo = :provinciaseo")
    , @NamedQuery(name = "Provincias.findByApreviatura", query = "SELECT p FROM Provincias p WHERE p.apreviatura = :apreviatura")
    , @NamedQuery(name = "Provincias.findByCp", query = "SELECT p FROM Provincias p WHERE p.cp = :cp")})
public class Provincias implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDPROVINCIA")
    private BigDecimal idprovincia;
    @Column(name = "PROVINCIA")
    private String provincia;
    @Column(name = "PROVINCIASEO")
    private String provinciaseo;
    @Column(name = "APREVIATURA")
    private String apreviatura;
    @Column(name = "CP")
    private BigInteger cp;

    public Provincias() {
    }

    public Provincias(BigDecimal idprovincia) {
        this.idprovincia = idprovincia;
    }

    public BigDecimal getIdprovincia() {
        return idprovincia;
    }

    public void setIdprovincia(BigDecimal idprovincia) {
        this.idprovincia = idprovincia;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getProvinciaseo() {
        return provinciaseo;
    }

    public void setProvinciaseo(String provinciaseo) {
        this.provinciaseo = provinciaseo;
    }

    public String getApreviatura() {
        return apreviatura;
    }

    public void setApreviatura(String apreviatura) {
        this.apreviatura = apreviatura;
    }

    public BigInteger getCp() {
        return cp;
    }

    public void setCp(BigInteger cp) {
        this.cp = cp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprovincia != null ? idprovincia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provincias)) {
            return false;
        }
        Provincias other = (Provincias) object;
        if ((this.idprovincia == null && other.idprovincia != null) || (this.idprovincia != null && !this.idprovincia.equals(other.idprovincia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pueblosdeespa\u00f1a78.Provincias[ idprovincia=" + idprovincia + " ]";
    }
    
}
