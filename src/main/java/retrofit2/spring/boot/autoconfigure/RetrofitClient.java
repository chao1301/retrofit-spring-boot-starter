package retrofit2.spring.boot.autoconfigure;

import java.lang.annotation.*;

/**
 * retrofit client
 *
 * @author super
 * @date 2018/11/7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RetrofitClient {
    /**
     * An absolute URL or resolvable hostname.
     */
    String url() default "";
}


