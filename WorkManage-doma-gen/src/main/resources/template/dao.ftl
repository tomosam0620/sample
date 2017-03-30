<#-- このテンプレートに対応するデータモデルのクラスは org.seasar.doma.extension.gen.DaoDesc です -->
<#import "/lib.ftl" as lib>
<#if lib.copyright??>
${lib.copyright}
</#if>
<#if packageName??>
package ${packageName};
</#if>

<#list importNames as importName>
import ${importName};
</#list>
import org.seasar.doma.boot.ConfigAutowireable;
import java.util.Optional;

/**
 * ${entityDesc.simpleName}のDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
<#if lib.author??>
 * @author t-otsuka
</#if>
 */
@ConfigAutowireable
@Dao<#if configClassSimpleName??>(config = ${configClassSimpleName}.class)</#if>
public interface ${simpleName} {

<#if entityDesc.idEntityPropertyDescs?size gt 0>
    /**
<#list entityDesc.idEntityPropertyDescs as property>
     * @param ${property.name}
</#list>
     * @return the ${entityDesc.simpleName} entity
     */
    @Select
    Optional<${entityDesc.simpleName}> selectById(<#list entityDesc.idEntityPropertyDescs as property>${property.propertyClassSimpleName} ${property.name}<#if property_has_next>, </#if></#list>);

</#if>
<#if entityDesc.idEntityPropertyDescs?size gt 0 && entityDesc.versionEntityPropertyDesc??>
    /**
<#list entityDesc.idEntityPropertyDescs as property>
     * @param ${property.name}
</#list>
     * @param ${entityDesc.versionEntityPropertyDesc.name}
     * @return the ${entityDesc.simpleName} entity
     */
    @Select(ensureResult = true)
    Optional<${entityDesc.simpleName}> selectByIdAndVersion(<#list entityDesc.idEntityPropertyDescs as property>${property.propertyClassSimpleName} ${property.name}, </#list>${entityDesc.versionEntityPropertyDesc.propertyClassSimpleName} ${entityDesc.versionEntityPropertyDesc.name});

</#if>
    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull=true)
    int insert(${entityDesc.simpleName} entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(${entityDesc.simpleName} entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(${entityDesc.simpleName} entity);
}