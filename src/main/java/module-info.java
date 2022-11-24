module com.mycompany.gestorpracticasgrupal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires lombok;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires java.base;
    
    opens com.mycompany.gestorpracticasgrupal to javafx.fxml;
    opens models;
    exports com.mycompany.gestorpracticasgrupal;
}
