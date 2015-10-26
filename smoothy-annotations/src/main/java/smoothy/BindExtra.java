package smoothy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by mehdi on 20/10/2015.
 */
@Target(ElementType.FIELD)
public @interface BindExtra {

    boolean optional() default false;
    String value() default "";
    //String defaultValue() default ""; // not implemented

}
