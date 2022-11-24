package models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Formula;

@Entity
@Table
public class Alumno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String apellidos;
    private String password;
    private String dni;
    private Date nacimiento;
    private String correo;
    private String telefono;
    @Column(name="totaldual")
    private Integer totalDual;
    @Column(name="totalfct")
    private Integer totalFCT;
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "profesor")
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "empresa")
    private Empresa empresa;

    @OneToMany(mappedBy = "alumno", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Actividad> actividades;

    public Alumno() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getTotalDual() {
        return totalDual;
    }

    public void setTotalDual(Integer totalDual) {
        this.totalDual = totalDual;
    }

    public Integer getTotalFCT() {
        return totalFCT;
    }

    public void setTotalFCT(Integer totalFCT) {
        this.totalFCT = totalFCT;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", password='" + password + '\'' +
                ", dni='" + dni + '\'' +
                ", nacimiento=" + nacimiento +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", totalDual=" + totalDual +
                ", totalFCT=" + totalFCT +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
