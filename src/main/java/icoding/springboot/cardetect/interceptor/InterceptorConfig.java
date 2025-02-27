package icoding.springboot.cardetect.interceptor;/* I love coding */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor LoginInterceptor;
    @Autowired
    private CheckInterceptor checkInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         //注册拦截器，并指定拦截路径
//        registry.addInterceptor(LoginInterceptor)
//                .addPathPatterns("/**") // 拦截所有路径
//                .excludePathPatterns("/login", "/register"); // 排除登录和注册路径

        registry.addInterceptor(checkInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register");
    }
}
