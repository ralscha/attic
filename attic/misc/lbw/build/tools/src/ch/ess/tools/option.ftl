package ${package}.web.options;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.web.options.AbstractOptions;
import ch.ess.base.annotation.option.Option;
import ${package}.dao.${className}Dao;
import ${package}.model.${className};

@Option(id = "${className?uncap_first}Options")
public class ${className}Options extends AbstractOptions {

  private ${className}Dao ${className?uncap_first}Dao;

  public void set${className}Dao(final ${className}Dao ${className?uncap_first}Dao) {
    this.${className?uncap_first}Dao = ${className?uncap_first}Dao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<${className}> ${className?uncap_first}List = ${className?uncap_first}Dao.findAll();

    for (${className} ${className?uncap_first} : ${className?uncap_first}List) {
      add(${className?uncap_first}.${readMethod}(), ${className?uncap_first}.getId().toString());
    }

    sort();
  }

}