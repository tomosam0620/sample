package jp.co.orangearch.workmanage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import jp.co.orangearch.workmanage.interceptor.LoggingInterceptor;
import jp.co.orangearch.workmanage.interceptor.TokenCheckInterceptor;

/**
 * オンラインの設定クラスです。
 *
 * @author t-otsuka
 *
 */
@Configuration
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

//		@Bean
//		public HandlerExceptionResolver handlerExceptionResolver(){
//			return new GlobalExceptionResolver();
//		}

}
