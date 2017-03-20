package jp.co.orangearch.workmanage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    UserDetailsService userDetailsService;

	@Override
    public void configure(WebSecurity web) throws Exception {
        // セキュリティ設定を無視するリクエスト設定
        // 静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視する
        web.ignoring().antMatchers(
                            "/images/**",
                            "/css/**",
                            "/js/**");
    }

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        // 認可の設定
        http.authorizeRequests()
            .antMatchers("/","/login").permitAll() // indexは全ユーザーアクセス許可
            .anyRequest().authenticated();  // それ以外は全て認証無しの場合アクセス不許可

        // ログイン設定
        http.formLogin()
//            .loginProcessingUrl("/login")   // 認証処理のパス
            .loginPage("/login")            // ログインフォームのパス
            .defaultSuccessUrl("/workTime/")     // 認証成功時の遷移先
            .usernameParameter("username")
            .passwordParameter("password")  // ユーザー名、パスワードのパラメータ名
            .permitAll();

        // ログアウト設定
        http.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))       // ログアウト処理のパス
            .logoutSuccessUrl("/login")                                        // ログアウト完了時のパス
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .permitAll();
    }

	/**
	 * 認証処理を拡張するためにAuthenticationManagerBuilderに認証プロバイダーを登録します。<br>
	 * DB認証用の認証プロバイダーに、認証時のユーザー情報取得サービスを拡張した独自サービスを登録し、<br>
	 * パスワード暗号化方法を設定します。
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		//認証種別=DB認証
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		//認証時のユーザー情報取得サービス設定
		provider.setUserDetailsService(userDetailsService);
		//パスワードエンコーダー設定
		provider.setPasswordEncoder(passwordEncoder());
		//salt=ユーザID(spring securityではログインフォームのIDはusernameという変数になる)
		ReflectionSaltSource salt = new ReflectionSaltSource();
		salt.setUserPropertyToUse("username");
		provider.setSaltSource(salt);
		authenticationManagerBuilder.authenticationProvider(provider);
	}

	/**
	 * パスワードエンコーダーをDIコンテナに登録します。
	 * <br>
	 * 暗号化ロジックをSHA256とします。
	 * @return パスワードエンコーダー
	 */
	@Bean
	ShaPasswordEncoder passwordEncoder(){
		return new ShaPasswordEncoder(256);
	}

}
