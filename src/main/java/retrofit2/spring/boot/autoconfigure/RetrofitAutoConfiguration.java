package retrofit2.spring.boot.autoconfigure;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * retrofit auto configuration
 *
 * @author super
 * @date 2018/11/6
 */
@Configuration
@ConditionalOnClass({Retrofit.class, GsonConverterFactory.class, OkHttpClient.class})
public class RetrofitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor).build();
    }

}
