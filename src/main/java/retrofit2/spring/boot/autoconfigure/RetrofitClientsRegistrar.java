package retrofit2.spring.boot.autoconfigure;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * retrofit client register
 *
 * @author super
 * @date 2018/11/6
 */
public class RetrofitClientsRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathRetrofitScanner scanner = new ClassPathRetrofitScanner(registry);
        scanner.setResourceLoader(this.resourceLoader);
        scanner.setAnnotationClass(RetrofitClient.class);
        scanner.registerFilters();
        scanner.doScan(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
    }

}
