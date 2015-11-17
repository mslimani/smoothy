package smoothy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by mehdi on 20/10/2015.
 */
@Target(ElementType.FIELD)
public @interface BindExtra {

    /**
     * @return true field in constructor builder else in setter name
     */
    boolean optional() default false;

    /**
     * <blockquote><pre>
     * {@code
     *     class MyActivity extends Activity {
     *         @BindExtra(value = "intValue", optional = true) public int mInt;
     *     }
     *
     *     Intent intent = MyActivityBuilder()
     *                          .intValue(2)
     *                          .build();
     * }
     * </pre></blockquote>
     * @return default name for field in builder and setter name
     */
    String value() default "";
}
