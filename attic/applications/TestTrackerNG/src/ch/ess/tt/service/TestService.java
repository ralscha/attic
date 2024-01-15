package ch.ess.tt.service;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.tt.model.Fall;
import ch.ess.tt.model.Schritt;
import ch.ess.tt.model.Test;

@Service
public class TestService {

  private final static String UPDATE_SQL = "update test set name=:name, status=:status, f1=:f1, f2=:f2, f3=:f3, f4=:f4, datum=:datum,"
      + "versionTest=:versionTest,beschreibung=:beschreibung,f1text=:f1text,f2text=:f2text,f3text=:f3text,f4text=:f4text,system=:system,"
      + "informationen=:informationen,sprache=:sprache,kurzbezeichnung=:kurzbezeichnung where id=:id";

  private final static String SELECT_SQL = "SELECT id,name,status,f1,f2,f3,f4,datum,versionTest,beschreibung,f1text,f2text,"
      + "f3text,f4text,system,informationen,sprache,kurzbezeichnung,dateiFileName1,dateiFileName2,dateiFileName3,"
      + "dateiContentSize1,dateiContentSize2,dateiContentSize3 FROM test";

  private SimpleJdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert insertTest;
  private SimpleJdbcInsert insertSchritt;
  private SimpleJdbcInsert insertFall;

  @Autowired 
  LobHandler lobHandler;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);

    this.insertTest = new SimpleJdbcInsert(dataSource).withTableName("test");
    this.insertTest.usingGeneratedKeyColumns("id");

    this.insertSchritt = new SimpleJdbcInsert(dataSource).withTableName("schritt");
    this.insertSchritt.usingGeneratedKeyColumns("id");

    this.insertFall = new SimpleJdbcInsert(dataSource).withTableName("fall");
    this.insertFall.usingGeneratedKeyColumns("id");
  }

  @Transactional(readOnly = true)
  public List<Test> list() {
    List<Test> tests = jdbcTemplate.query(SELECT_SQL, new TestMapper());
    //todo
//    for (Test test : tests) {
//      readSchritte(test);
//    }
    return tests;
  }

  @Transactional
  public Test save(Test test) {
    if (test.getId() == 0) {
      insert(test);
    } else {
      update(test);
    }

    return test;
  }

  @Transactional
  public void insertAttachment(int no, final int id, final String fileName, final String mimeType, final long size,
      final InputStream inputStream) {

    jdbcTemplate.getJdbcOperations().execute(
        "update test set dateiFileName" + no + "=?,dateiContentType" + no + "=?,dateiContentSize" + no
            + "=?,dateiContent" + no + "=? where id = ?", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {

          @Override
          protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
            ps.setString(1, fileName);
            ps.setString(2, mimeType);
            ps.setInt(3, (int)size);

            lobCreator.setBlobAsBinaryStream(ps, 4, inputStream, (int)size);

            ps.setInt(5, id);

          }
        });
  }

  @Transactional(readOnly = true)
  public Attachment getAttachment(int no, final int testId) {
    Attachment attachment = jdbcTemplate.queryForObject("select dateiContentType" + no + ",dateiContent" + no
        + " from test where id = ?", new ParameterizedRowMapper<Attachment>() {

      public Attachment mapRow(ResultSet rs, @SuppressWarnings("unused")int rowNum) throws SQLException {
        Attachment result = new Attachment();
        result.setMimeType(rs.getString(1));
        result.setAttachmentInputStream(lobHandler.getBlobAsBinaryStream(rs, 2));        
        return result;
      }
    }, testId);
    
    return attachment;
  }

  @Transactional
  public void insert(Test test) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(test);
    Number newKey = insertTest.executeAndReturnKey(parameters);
    test.setId(newKey.intValue());
  }

  @Transactional
  public void update(Test test) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(test);
    jdbcTemplate.update(UPDATE_SQL, parameters);
  }

  @Transactional(readOnly = true)
  public void readSchritte(Test test) {
    List<Schritt> schritte = jdbcTemplate.query("select * from schritt where testId = ?", new SchrittMapper(), test
        .getId());
    for (Schritt schritt : schritte) {
      readFall(schritt);
    }
    test.getSchrittList().addAll(schritte);
  }

  @Transactional(readOnly = true)
  public void readFall(Schritt schritt) {
    List<Fall> falle = jdbcTemplate.query("select * from fall where schrittId = ?", new FallMapper(), schritt.getId());
    schritt.getFallList().addAll(falle);
  }

  @Transactional
  public int deleteTest(int id) {
    jdbcTemplate.update("delete from fall where schrittId in (select id from schritt where testId = ?)", id);
    jdbcTemplate.update("delete from schritt where testId = ?", id);
    jdbcTemplate.update("delete from test where id = ?", id);
    return id;
  }

  @Transactional
  public void deleteAttachment(int no, int id) {
    jdbcTemplate.update("update test set dateiFileName" + no + "=null, dateiContentType" + no + "=null, "
        + "dateiContentSize" + no + "=null, dateiContent" + no + "=null where id = ?", id);
  }

  static final class FallMapper implements ParameterizedRowMapper<Fall> {

    @Override
    public Fall mapRow(ResultSet rs, @SuppressWarnings("unused")int rowNum) throws SQLException {
      Fall fall = new Fall();
      fall.setId(rs.getInt("id"));
      fall.setNummer(rs.getInt("nummer"));
      fall.setTestNummer(rs.getInt("testNummer"));
      fall.setAnweisung(rs.getString("anweisung"));
      fall.setEreigniss(rs.getString("ereigniss"));
      fall.setNavigation(rs.getString("navigation"));

      return fall;
    }
  }

  static final class SchrittMapper implements ParameterizedRowMapper<Schritt> {

    @Override
    public Schritt mapRow(ResultSet rs, @SuppressWarnings("unused")
    int rowNum) throws SQLException {
      Schritt schritt = new Schritt();
      schritt.setFallList(new ArrayList<Fall>());
      schritt.setId(rs.getInt("id"));
      schritt.setNummer(rs.getInt("nummer"));
      schritt.setTitel(rs.getString("titel"));
      return schritt;
    }
  }
  
  static final class TestMapper implements ParameterizedRowMapper<Test> {

    @Override
    public Test mapRow(ResultSet rs, @SuppressWarnings("unused")
    int rowNum) throws SQLException {
      Test test = new Test();
      test.setSchrittList(new ArrayList<Schritt>());
      test.setId(rs.getInt("id"));
      test.setBeschreibung(rs.getString("beschreibung"));
      test.setDatum(rs.getDate("datum"));
      test.setName(rs.getString("name"));
      test.setStatus(rs.getString("status"));

      test.setF1(rs.getInt("f1"));
      test.setF2(rs.getInt("f2"));
      test.setF3(rs.getInt("f3"));
      test.setF4(rs.getInt("f4"));

      test.setVersionTest(rs.getString("versionTest"));

      test.setF1text(rs.getString("f1text"));
      test.setF2text(rs.getString("f2text"));
      test.setF3text(rs.getString("f3text"));
      test.setF4text(rs.getString("f4text"));

      test.setSystem(rs.getString("system"));
      test.setInformationen(rs.getString("informationen"));

      test.setSprache(rs.getString("sprache"));
      test.setKurzbezeichnung(rs.getString("kurzbezeichnung"));

      test.setDateiContentSize1(rs.getInt("dateiContentSize1"));
      test.setDateiContentSize2(rs.getInt("dateiContentSize2"));
      test.setDateiContentSize3(rs.getInt("dateiContentSize3"));

      test.setDateiFileName1(rs.getString("dateiFileName1"));
      test.setDateiFileName2(rs.getString("dateiFileName2"));
      test.setDateiFileName3(rs.getString("dateiFileName3"));
      return test;
    }
  }

}
