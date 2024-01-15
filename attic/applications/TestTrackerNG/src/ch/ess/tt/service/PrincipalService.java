package ch.ess.tt.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.granite.context.GraniteContext;
import org.granite.messaging.webapp.HttpGraniteContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.tt.model.Principal;
import ch.ess.tt.model.RoleEnum;

@Service
public class PrincipalService {

  private final static String UPDATE_WO_PASSWORD = "update principal set name=:name, firstName=:firstName, locale=:locale, enabled=:enabled, "
      + "username=:userName, email=:email, oe=:oe where id=:id";

  private final static String UPDATE_FULL = "update principal set name=:name, firstName=:firstName, locale=:locale, enabled=:enabled, "
      + "username=:userName, email=:email, oe=:oe, passwordHash=:passwordHash where id=:id";

  private final static String INSERT_AUTHORITY = "insert into authorities(authority,principalId) values(?,?)";
  
  private final static String CHANGEME = "changeme";

  private SimpleJdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert insertPrincipal;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    this.insertPrincipal = new SimpleJdbcInsert(dataSource).withTableName("principal");
    this.insertPrincipal.usingGeneratedKeyColumns("id");
  }

  @Transactional(readOnly = true)
  public int getCount() {
    return jdbcTemplate.queryForInt("select count(*) from principal");
  }

  @Transactional(readOnly = true)
  public Principal loginPrincipal(String username, String password) {

    List<Principal> principalList = jdbcTemplate.query(
        "select * from principal where userName = ? and passwordHash = ?", new PrincipalMapper(), username, DigestUtils
            .shaHex(password));

    if (!principalList.isEmpty()) {
      Principal principal = principalList.get(0);
      readAuthorities(principal);
      ((HttpGraniteContext)GraniteContext.getCurrentInstance()).getSession().setAttribute("tt_principal",principal);
      return principal;
    }

    return null;
  }
  
  @Transactional(readOnly = true)
  private void readAuthorities(Principal principal) {
    List<Map<String,Object>> result = jdbcTemplate.queryForList("select authority from authorities where principalId = ?", 
        principal.getId());
        
    for (Map<String,Object> row : result) {      
      String authority = (String)row.get("authority");
      if (authority.equals(RoleEnum.ROLE_ADMIN.name())) {
        principal.setAdmin(true);
      } else if (authority.equals(RoleEnum.ROLE_AUTHOR.name())) {
        principal.setRoleAuthor(true);
      } else if (authority.equals(RoleEnum.ROLE_TESTER.name())) {
        principal.setRoleTester(true);
      } else if (authority.equals(RoleEnum.ROLE_DEVELOPER.name())) {
        principal.setRoleDeveloper(true);
      }
    }
  }
  
  @Transactional
  private void updateAuthorities(Principal principal) {
    jdbcTemplate.update("delete from authorities where principalId = ?", principal.getId());
    
    if (principal.isAdmin()) {
      jdbcTemplate.update(INSERT_AUTHORITY, RoleEnum.ROLE_ADMIN.name(), principal.getId());
    }
    
    if (principal.isRoleAuthor()) {
      jdbcTemplate.update(INSERT_AUTHORITY, RoleEnum.ROLE_AUTHOR.name(), principal.getId());
    }
    
    if (principal.isRoleTester()) {
      jdbcTemplate.update(INSERT_AUTHORITY, RoleEnum.ROLE_TESTER.name(), principal.getId());
    }
    
    if (principal.isRoleDeveloper()) {
      jdbcTemplate.update(INSERT_AUTHORITY, RoleEnum.ROLE_DEVELOPER.name(), principal.getId());
    }
  }
  
  public void logoutPrincipal() {
    ((HttpGraniteContext)GraniteContext.getCurrentInstance()).getSession().removeAttribute("tt_principal");
  }

  @Transactional(readOnly = true)
  public List<Principal> list() {
    List<Principal> principals = jdbcTemplate.query("select * from principal", new PrincipalMapper());
    for (Principal principal : principals) {
      readAuthorities(principal);
    }
    return principals;
  }

  @Transactional
  public void insert(Principal principal) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(principal);
    Number newKey = insertPrincipal.executeAndReturnKey(parameters);
    principal.setId(newKey.intValue());
    
    updateAuthorities(principal);
  }

  @Transactional
  public void update(Principal principal) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(principal);
    if (CHANGEME.equals(principal.getPasswordHash())) {
      jdbcTemplate.update(UPDATE_WO_PASSWORD, parameters);
    } else {
      jdbcTemplate.update(UPDATE_FULL, parameters);
    }

    updateAuthorities(principal);
  }

  @Transactional
  public int deletePrincipal(int id) {
    jdbcTemplate.update("delete from principal where id = ?", id);
    return id;
  }

  @Transactional
  public Principal save(Principal principal) {
    if (principal.getId() == 0) {
      insert(principal);
    } else {
      update(principal);
    }

    return principal;
  }

  static final class PrincipalMapper implements ParameterizedRowMapper<Principal> {
    @Override
    public Principal mapRow(ResultSet rs, @SuppressWarnings("unused")int rowNum) throws SQLException {
      Principal principal = new Principal();
      principal.setId(rs.getInt("id"));
      principal.setName(rs.getString("name"));
      principal.setFirstName(rs.getString("firstName"));
      principal.setLocale(rs.getString("locale"));
      principal.setEnabled(rs.getBoolean("enabled"));
      principal.setUserName(rs.getString("userName"));
      principal.setEmail(rs.getString("email"));
      principal.setOe(rs.getString("oe"));
      return principal;
    }
  }
}
