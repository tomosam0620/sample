package jp.co.orangearch.workmanage.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.orangearch.workmanage.entity.User;


/**
 * USERテーブルのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 *
 *
 * @author t-otsuka
 *
 */
@ConfigAutowireable
@Dao
public interface UserDao {

	@Select
	List<User> selectAll();

	@Select
	User selectById(String userId);

}
