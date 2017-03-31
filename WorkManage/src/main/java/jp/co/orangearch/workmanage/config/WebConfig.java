package jp.co.orangearch.workmanage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.mygreen.supercsv.localization.SpringMessageResolver;

import jp.co.orangearch.workmanage.interceptor.LoggingInterceptor;
import jp.co.orangearch.workmanage.interceptor.TokenCheckInterceptor;

/**
 * オンラインの設定クラスです。
 *
 * @author t-otsuka
 *
 */
@Configuration
@ComponentScan(basePackages = {"jp.co.orangearch.workmanage"}, excludeFilters={
		  @ComponentScan.Filter(type=FilterType.REGEX, pattern=".*Test")})
public class WebConfig extends WebMvcConfigurerAdapter{

	@Autowired
	MessageSource messageSource;

	@Autowired
	TokenCheckInterceptor tokenCheckInterceptor;

	@Autowired
	LoggingInterceptor loggingInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(loggingInterceptor);
		 registry.addInterceptor(tokenCheckInterceptor);
	}

	@Override
	public Validator getValidator() {
		return validator();
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource);
		return localValidatorFactoryBean;
	}

	@Bean
	@Description("Spring標準のメッセージソースの定義")
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.addBasenames(
				"i18n/messages",
				"com.github.mygreen.supercsv.localization.SuperCsvMessages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	@Description("本ライブラリのSpring用のMessgeResolverの定義")
	public SpringMessageResolver springMessageResolver() {
		return new SpringMessageResolver(messageSource());
	}

//		@Bean
//		public HandlerExceptionResolver handlerExceptionResolver(){
//			return new GlobalExceptionResolver();
//		}

	@Bean //this could be provided via auto-configuration
    MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
