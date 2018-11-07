package retrofit2.spring.boot.autoconfigure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableRetrofitClients  annotation
 *
 * @author super
 * @date 2018/11/6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RetrofitClientsRegistrar.class)
public @interface EnableRetrofitClients {

}
