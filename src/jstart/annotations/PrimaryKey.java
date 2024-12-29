package jstart.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Primary Key Annotation Class
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PrimaryKey {
    /**
     * @return comma-separated list of primary key fields of table
     */
    public String fields();
}
