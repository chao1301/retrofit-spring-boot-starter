package retrofit2.spring.boot.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.util.List;

/**
 * retrofit client factory bean
 *
 * @author super
 * @date 2018/11/6
 */
@Slf4j
public class RetrofitClientFactoryBean<T> implements FactoryBean<T>, EnvironmentAware {

    private Class<T> retrofitInterface;
    private Environment environment;

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    List<Converter.Factory> factories;

    public RetrofitClientFactoryBean() {
    }

    public RetrofitClientFactoryBean(Class<T> retrofitInterface) {
        this.retrofitInterface = retrofitInterface;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setRetrofitInterface(Class<T> retrofitInterface) {
        this.retrofitInterface = retrofitInterface;
    }

    public Class<T> getRetrofitInterface() {
        return retrofitInterface;
    }

    @Override
    public T getObject() throws Exception {
        RetrofitClient annotation = this.getRetrofitInterface().getAnnotation(RetrofitClient.class);
        // the default value of url is an empty string
        StringBuilder sb = new StringBuilder(annotation.url());
        if (sb.length() == 0) {
            sb.append("${retrofit.").append(forCamelCase(this.getRetrofitInterface().getSimpleName())).append(".base-url}");
        }
        String url = environment.resolvePlaceholders(sb.toString());
        log.debug("{} base url is {}", retrofitInterface.getName(), url);

        Retrofit.Builder builder = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url);

        for (Converter.Factory factory : factories) {
            builder.addConverterFactory(factory);
        }

        Retrofit retrofit = builder.build();
        return retrofit.create(retrofitInterface);

    }

    @Override
    public Class<T> getObjectType() {
        return this.retrofitInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private String forCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        for (char c : name.toCharArray()) {
            result.append(Character.isUpperCase(c) && result.length() > 0
                    && result.charAt(result.length() - 1) != '-'
                    ? "-" + Character.toLowerCase(c) : Character.toLowerCase(c));
        }
        return result.toString();
    }

}