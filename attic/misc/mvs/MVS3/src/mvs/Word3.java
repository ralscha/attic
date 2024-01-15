/**
 * Cache' Java Class Generated for class mvs.Word3
 *
 * @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3</A>
**/

package mvs;
import com.intersys.cache.CacheObject;
import com.intersys.cache.Dataholder;
import com.intersys.cache.SysDatabase;
import com.intersys.classes.Persistent;
import com.intersys.classes.RegisteredObject;
import com.intersys.objects.CacheException;
import com.intersys.objects.CacheQuery;
import com.intersys.objects.Database;
import com.intersys.objects.Id;
import com.intersys.objects.InvalidClassException;
import com.intersys.objects.Oid;
import com.intersys.objects.reflect.CacheClass;


public class Word3 extends Persistent {

    private static String CACHE_CLASS_NAME = "mvs.Word3";
    /**
           NB: DO NOT USE IN APPLICATION!
           Use <code>Word3._open</code> instead.
           <p>
           Used to construct a Java object, corresponding to existing object
           in Cache database.

           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Id)
    */
    public Word3 (CacheObject ref) throws CacheException {
        super (ref);
    }
    /**
       Creates a new instance of object "<CacheClassName>" in Cache
       database and corresponding object of class
       <code>Word3</code>.

       @param _db <code>Database</code> object used for connection with
       Cache database.

       @throws CacheException in case of error.

              @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
              @see #_open(com.intersys.objects.Database, com.intersys.objects.Id)
     */
    public Word3 (Database db) throws CacheException {
        super (((SysDatabase)db).newCacheObject (CACHE_CLASS_NAME));
    }
    /**
       Runs method <code> %OpenId </code> in Cache to open an object
       from Cache database and creates corresponding object of class
       <code>Word3</code>.

       @return <code> RegisteredObject </code>, corresponding to opened
       object. This object may be of <code>Word3</code> or of
      any of its subclasses. Cast to <code>Word3</code> is
      guaranteed to pass without <code>ClassCastException</code> exception.

       @param _db <code>Database</code> object used for connection with
       Cache database.

       @param id ID as specified in Cache represented as
      <code>Id</code>.

       @throws CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #Word3
     */
    public static RegisteredObject _open (Database db, Id id) throws CacheException {
        CacheObject cobj = (((SysDatabase)db).openCacheObject(CACHE_CLASS_NAME, id.toString()));
        return (RegisteredObject)(cobj.newJavaInstance());
    }
    public static RegisteredObject _open (Database db, Id id, int concurrency) throws CacheException {
        CacheObject cobj = (((SysDatabase)db).openCacheObject(CACHE_CLASS_NAME, id.toString(), concurrency));
        return (RegisteredObject)(cobj.newJavaInstance());
    }
    /**
       Runs method <code> %Open </code> in Cache to open an object
       from Cache database and creates corresponding object of class
       <code>Word3</code>.

       @return <code> RegisteredObject </code>, corresponding to opened
       object. This object may be of <code>Word3</code> or of
      any of its subclasses. Cast to <code>Word3</code> is
      guaranteed to pass without <code>ClassCastException</code> exception.

       @param _db <code>Database</code> object used for connection with
       Cache database.
       @param oid Object ID as specified in Cache. represented as
      <code>Oid</code>.


       @throws CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Id)
           @see #Word3(com.intersys.objects.Database)
     */
    public static RegisteredObject _open (Database db, Oid oid) throws CacheException {
        CacheObject cobj = (((SysDatabase)db).openCacheObject(CACHE_CLASS_NAME, oid.getData()));
        return (RegisteredObject)(cobj.newJavaInstance());
    }
    public static RegisteredObject _open (Database db, Oid oid, int concurrency) throws CacheException {
        CacheObject cobj = (((SysDatabase)db).openCacheObject(CACHE_CLASS_NAME, oid.getData(), concurrency));
        return (RegisteredObject)(cobj.newJavaInstance());
    }
    public static void delete (Database db, Id id) throws CacheException {
        ((SysDatabase)db).deleteObject(CACHE_CLASS_NAME, id);
    }
    public static void delete (Database db, Id id, int concurrency) throws CacheException {
        ((SysDatabase)db).deleteObject(CACHE_CLASS_NAME, id, concurrency);
    }
    public static void _deleteId (Database db, Id id) throws CacheException {
        delete(db, id);
    }
    public static void _deleteId (Database db, Id id, int concurrency) throws CacheException {
        delete(db, id, concurrency);
    }
    public static boolean exists (Database db, Id id) throws CacheException {
        return ((SysDatabase)db).existsObject(CACHE_CLASS_NAME, id);
    }
    public static Boolean _existsId (Database db, Id id) throws CacheException {
        return new Boolean(exists(db, id));
    }
    /**
       Returns class name of the class Word3 as it is in
      Cache Database. Note, that this is a static method, so no
      object specific information can be returned. Use
      <code>getCacheClass().geName()</code> to get the class name
      for specific object.
       @return Cache class name as a <code>String</code>
      @see #getCacheClass()
      @see com.intersys.objects.reflect.CacheClass#getName()
     */
    public static String getCacheClassName( ) {
        return CACHE_CLASS_NAME;
    }

   /**
           Allows access metadata information about type of this object
           in Cache database. Also can be used for dynamic binding (accessing
           properties and calling methods without particular class known).

           @return <code>CacheClass</code> object for this object type.
   */
    public CacheClass getCacheClass( ) throws CacheException {
        return mInternal.getCacheClass();
    }

    /**
       Verifies that all fields from Cache class are exposed with
       accessor methods in Java class and that values for indexes in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p>But if there is any inconsistency in zObjVal indexes this is fatal and class can not work correctly and must be regenerated

       @param _db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws InvalidClassException if any inconsistency is found.
       @throws CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see com.intersys.objects.InvalidPropertyException

     */
    public static void checkAllFieldsValid(Database db ) throws CacheException {
        checkAllFieldsValid(db, CACHE_CLASS_NAME, Word3.class);
    }

    public static boolean exists (Database db, Oid oid) throws CacheException {
        return exists (db, oid, CACHE_CLASS_NAME);
    }

    /**
       Verifies that all fields from Cache class are exposed with
       accessor methods in Java class and that values for indexes in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p>But if there is any inconsistency in zObjVal indexes this is fatal and class can not work correctly and must be regenerated

       @param _db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws InvalidClassException if any inconsistency is found.
       @throws CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see com.intersys.objects.InvalidPropertyException

     */
    public static void checkAllMethods(Database db ) throws CacheException {
        checkAllMethods(db, CACHE_CLASS_NAME, Word3.class);
    }
    private static int ii_hits = 3;
    private static int jj_hits = 0;
    private static int kk_hits = 3;
    /**
       Verifies that indexes for property <code>hits</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param _db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws InvalidClassException if any inconsistency is found.
       @throws CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkhitsValid (Database db) throws CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "hits",ii_hits, jj_hits, kk_hits);
    }
    /**
       Returns value of property <code>hits</code>
       @return current value of <code>hits</code> represented as
       <code>java.lang.Integer</code>

       @throws CacheException if any error occurred during value retrieval.
       @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#hits"> hits</A>
    */
    public java.lang.Integer gethits() throws CacheException {
        Dataholder dh = mInternal.getProperty(ii_hits,
                                                jj_hits,
                                                Database.RET_PRIM,
                                                "hits");
       return dh.getInteger();
    }

    /**
       Sets new value for <code>hits</code>.
       @param value new value to be set represented as
       <code>java.lang.Integer</code>.
       @throws CacheException if any error occurred during value setting.
       @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#hits"> hits</A>
    */
    public void sethits(java.lang.Integer value) throws CacheException {
        Dataholder dh = new Dataholder (value);
        mInternal.setProperty(ii_hits, jj_hits,kk_hits, Database.RET_PRIM, "hits", dh);
        return;
    }

    private static int ii_ref = 4;
    private static int jj_ref = 0;
    private static int kk_ref = 4;
    /**
       Verifies that indexes for property <code>ref</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param _db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws InvalidClassException if any inconsistency is found.
       @throws CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkrefValid (Database db) throws CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "ref",ii_ref, jj_ref, kk_ref);
    }
    /**
       Returns value of property <code>ref</code>
       @return current value of <code>ref</code> represented as
       <code>java.lang.Integer</code>

       @throws CacheException if any error occurred during value retrieval.
       @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#ref"> ref</A>
    */
    public java.lang.Integer getref() throws CacheException {
        Dataholder dh = mInternal.getProperty(ii_ref,
                                                jj_ref,
                                                Database.RET_PRIM,
                                                "ref");
       return dh.getInteger();
    }

    /**
       Sets new value for <code>ref</code>.
       @param value new value to be set represented as
       <code>java.lang.Integer</code>.
       @throws CacheException if any error occurred during value setting.
       @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#ref"> ref</A>
    */
    public void setref(java.lang.Integer value) throws CacheException {
        Dataholder dh = new Dataholder (value);
        mInternal.setProperty(ii_ref, jj_ref,kk_ref, Database.RET_PRIM, "ref", dh);
        return;
    }

    private static int ii_word3 = 5;
    private static int jj_word3 = 0;
    private static int kk_word3 = 5;
    /**
       Verifies that indexes for property <code>word3</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param _db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws InvalidClassException if any inconsistency is found.
       @throws CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkword3Valid (Database db) throws CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "word3",ii_word3, jj_word3, kk_word3);
    }
    /**
       Returns value of property <code>word3</code>
       @return current value of <code>word3</code> represented as
       <code>java.lang.Integer</code>

       @throws CacheException if any error occurred during value retrieval.
       @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#word3"> word3</A>
    */
    public java.lang.Integer getword3() throws CacheException {
        Dataholder dh = mInternal.getProperty(ii_word3,
                                                jj_word3,
                                                Database.RET_PRIM,
                                                "word3");
       return dh.getInteger();
    }

    /**
       Sets new value for <code>word3</code>.
       @param value new value to be set represented as
       <code>java.lang.Integer</code>.
       @throws CacheException if any error occurred during value setting.
       @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#word3"> word3</A>
    */
    public void setword3(java.lang.Integer value) throws CacheException {
        Dataholder dh = new Dataholder (value);
        mInternal.setProperty(ii_word3, jj_word3,kk_word3, Database.RET_PRIM, "word3", dh);
        return;
    }

    /**
     Runs method sys_BMEBuilt in Cache
     @param db represented as Database
     @param bmeName represented as com.intersys.objects.StringHolder
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%BMEBuilt"> Method %BMEBuilt</A>
    */
    public static java.lang.Boolean sys_BMEBuilt (Database db, com.intersys.objects.StringHolder bmeName) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        int[] _refs = new int[1];
        args[0] = Dataholder.create (bmeName.value);
        _refs[0] = 1;
        Dataholder[] res=db.runClassMethod(CACHE_CLASS_NAME,"%BMEBuilt",_refs,args,Database.RET_PRIM);
        bmeName.set(res[1].getString());
        return res[0].getBoolean();
    }

    /**
     Runs method sys_BuildIndices in Cache
     @param db represented as Database
     @param idxlist set to ""
     @throws CacheException if any error occured while running the method.
     @see #sys_BuildIndices(Database,com.intersys.objects.SList)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%BuildIndices"> Method %BuildIndices</A>
    */
    public static void sys_BuildIndices (Database db) throws CacheException {
        Dataholder[] args = new Dataholder[0];
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%BuildIndices",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     Runs method sys_BuildIndices in Cache
     @param db represented as Database
     @param idxlist represented as com.intersys.objects.SList
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%BuildIndices"> Method %BuildIndices</A>
    */
    public static void sys_BuildIndices (Database db, com.intersys.objects.SList idxlist) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(idxlist);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%BuildIndices",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }

    /**
     Runs method sys_CheckUnique in Cache
     @param db represented as Database
     @param idxlist set to ""
     @throws CacheException if any error occured while running the method.
     @see #sys_CheckUnique(Database,com.intersys.objects.SList)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%CheckUnique"> Method %CheckUnique</A>
    */
    public static void sys_CheckUnique (Database db) throws CacheException {
        Dataholder[] args = new Dataholder[0];
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%CheckUnique",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     Runs method sys_CheckUnique in Cache
     @param db represented as Database
     @param idxlist represented as com.intersys.objects.SList
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%CheckUnique"> Method %CheckUnique</A>
    */
    public static void sys_CheckUnique (Database db, com.intersys.objects.SList idxlist) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(idxlist);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%CheckUnique",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }

    /**
     Runs method sys_ClassName in Cache
     @param db represented as Database
     @param fullname represented as java.lang.Boolean
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%ClassName"> Method %ClassName</A>
    */
    public static java.lang.String sys_ClassName (Database db, java.lang.Boolean fullname) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(fullname);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%ClassName",args,Database.RET_PRIM);
        return res.getString();
    }

    /**
     Runs method sys_Delete in Cache
     @param db represented as Database
     @param oid set to ""
     @param concurrency set to -1
     @throws CacheException if any error occured while running the method.
     @see #sys_Delete(Database,com.intersys.objects.Oid,java.lang.Integer)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%Delete"> Method %Delete</A>
    */
    public static void sys_Delete (Database db) throws CacheException {
        Dataholder[] args = new Dataholder[0];
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%Delete",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     Runs method sys_Delete in Cache
     @param db represented as Database
     @param oid represented as com.intersys.objects.Oid
     @param concurrency set to -1
     @throws CacheException if any error occured while running the method.
     @see #sys_Delete(Database,com.intersys.objects.Oid,java.lang.Integer)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%Delete"> Method %Delete</A>
    */
    public static void sys_Delete (Database db, com.intersys.objects.Oid oid) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(oid);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%Delete",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     Runs method sys_Delete in Cache
     @param db represented as Database
     @param oid represented as com.intersys.objects.Oid
     @param concurrency represented as java.lang.Integer
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%Delete"> Method %Delete</A>
    */
    public static void sys_Delete (Database db, com.intersys.objects.Oid oid, java.lang.Integer concurrency) throws CacheException {
        Dataholder[] args = new Dataholder[2];
        args[0] = new Dataholder(oid);
        args[1] = new Dataholder(concurrency);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%Delete",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }

    /**
     Runs method sys_DeleteExtent in Cache
     @param db represented as Database
     @param concurrency represented as java.lang.Integer
     @param deletecount represented as com.intersys.objects.StringHolder
     @param instancecount represented as com.intersys.objects.StringHolder
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%DeleteExtent"> Method %DeleteExtent</A>
    */
    public static void sys_DeleteExtent (Database db, java.lang.Integer concurrency, com.intersys.objects.StringHolder deletecount, com.intersys.objects.StringHolder instancecount) throws CacheException {
        Dataholder[] args = new Dataholder[3];
        int[] _refs = new int[2];
        args[0] = new Dataholder(concurrency);
        args[1] = Dataholder.create (deletecount.value);
        _refs[0] = 2;
        args[2] = Dataholder.create (instancecount.value);
        _refs[1] = 3;
        Dataholder[] res=db.runClassMethod(CACHE_CLASS_NAME,"%DeleteExtent",_refs,args,Database.RET_PRIM);
        deletecount.set(res[1].getString());
        instancecount.set(res[2].getString());
        db.parseStatus(res[0]);
        return;
    }

    /**
     Runs method sys_DeleteId in Cache
     @param db represented as Database
     @param id represented as java.lang.String
     @param concurrency set to -1
     @throws CacheException if any error occured while running the method.
     @see #sys_DeleteId(Database,java.lang.String,java.lang.Integer)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%DeleteId"> Method %DeleteId</A>
    */
    public static void sys_DeleteId (Database db, java.lang.String id) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(id);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%DeleteId",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     Runs method sys_DeleteId in Cache
     @param db represented as Database
     @param id represented as java.lang.String
     @param concurrency represented as java.lang.Integer
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%DeleteId"> Method %DeleteId</A>
    */
    public static void sys_DeleteId (Database db, java.lang.String id, java.lang.Integer concurrency) throws CacheException {
        Dataholder[] args = new Dataholder[2];
        args[0] = new Dataholder(id);
        args[1] = new Dataholder(concurrency);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%DeleteId",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }

    /**
     Runs method sys_Exists in Cache
     @param db represented as Database
     @param oid set to ""
     @throws CacheException if any error occured while running the method.
     @see #sys_Exists(Database,com.intersys.objects.Oid)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%Exists"> Method %Exists</A>
    */
    public static java.lang.Boolean sys_Exists (Database db) throws CacheException {
        Dataholder[] args = new Dataholder[0];
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%Exists",args,Database.RET_PRIM);
        return res.getBoolean();
    }
    /**
     Runs method sys_Exists in Cache
     @param db represented as Database
     @param oid represented as com.intersys.objects.Oid
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%Exists"> Method %Exists</A>
    */
    public static java.lang.Boolean sys_Exists (Database db, com.intersys.objects.Oid oid) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(oid);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%Exists",args,Database.RET_PRIM);
        return res.getBoolean();
    }

    /**
     Runs method sys_ExistsId in Cache
     @param db represented as Database
     @param id represented as java.lang.String
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%ExistsId"> Method %ExistsId</A>
    */
    public static java.lang.Boolean sys_ExistsId (Database db, java.lang.String id) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(id);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%ExistsId",args,Database.RET_PRIM);
        return res.getBoolean();
    }

    /**
     Runs method sys_Extends in Cache
     @param db represented as Database
     @param isclass represented as java.lang.String
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%Extends"> Method %Extends</A>
    */
    public static java.lang.Integer sys_Extends (Database db, java.lang.String isclass) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(isclass);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%Extends",args,Database.RET_PRIM);
        return res.getInteger();
    }

    /**
     Runs method sys_GetParameter in Cache
     @param db represented as Database
     @param paramname set to ""
     @throws CacheException if any error occured while running the method.
     @see #sys_GetParameter(Database,java.lang.String)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%GetParameter"> Method %GetParameter</A>
    */
    public static java.lang.String sys_GetParameter (Database db) throws CacheException {
        Dataholder[] args = new Dataholder[0];
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%GetParameter",args,Database.RET_PRIM);
        return res.getString();
    }
    /**
     Runs method sys_GetParameter in Cache
     @param db represented as Database
     @param paramname represented as java.lang.String
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%GetParameter"> Method %GetParameter</A>
    */
    public static java.lang.String sys_GetParameter (Database db, java.lang.String paramname) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(paramname);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%GetParameter",args,Database.RET_PRIM);
        return res.getString();
    }

    /**
     Runs method sys_IsA in Cache
     @param db represented as Database
     @param isclass represented as java.lang.String
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%IsA"> Method %IsA</A>
    */
    public static java.lang.Integer sys_IsA (Database db, java.lang.String isclass) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(isclass);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%IsA",args,Database.RET_PRIM);
        return res.getInteger();
    }

    /**
     Runs method sys_KillExtent in Cache
     @param db represented as Database
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%KillExtent"> Method %KillExtent</A>
    */
    public static void sys_KillExtent (Database db) throws CacheException {
        Dataholder[] args = new Dataholder[0];
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%KillExtent",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }

    /**
     Runs method sys_PackageName in Cache
     @param db represented as Database
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%PackageName"> Method %PackageName</A>
    */
    public static java.lang.String sys_PackageName (Database db) throws CacheException {
        Dataholder[] args = new Dataholder[0];
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%PackageName",args,Database.RET_PRIM);
        return res.getString();
    }

    /**
     Runs method sys_PurgeIndices in Cache
     @param db represented as Database
     @param idxlist set to ""
     @throws CacheException if any error occured while running the method.
     @see #sys_PurgeIndices(Database,com.intersys.objects.SList)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%PurgeIndices"> Method %PurgeIndices</A>
    */
    public static void sys_PurgeIndices (Database db) throws CacheException {
        Dataholder[] args = new Dataholder[0];
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%PurgeIndices",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     Runs method sys_PurgeIndices in Cache
     @param db represented as Database
     @param idxlist represented as com.intersys.objects.SList
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%PurgeIndices"> Method %PurgeIndices</A>
    */
    public static void sys_PurgeIndices (Database db, com.intersys.objects.SList idxlist) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(idxlist);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%PurgeIndices",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }

    /**
     Runs method sys_SortBegin in Cache
     @param db represented as Database
     @param idxlist set to ""
     @param excludeunique set to 0
     @throws CacheException if any error occured while running the method.
     @see #sys_SortBegin(Database,com.intersys.objects.SList,java.lang.Integer)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%SortBegin"> Method %SortBegin</A>
    */
    public static void sys_SortBegin (Database db) throws CacheException {
        Dataholder[] args = new Dataholder[0];
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%SortBegin",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     Runs method sys_SortBegin in Cache
     @param db represented as Database
     @param idxlist represented as com.intersys.objects.SList
     @param excludeunique set to 0
     @throws CacheException if any error occured while running the method.
     @see #sys_SortBegin(Database,com.intersys.objects.SList,java.lang.Integer)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%SortBegin"> Method %SortBegin</A>
    */
    public static void sys_SortBegin (Database db, com.intersys.objects.SList idxlist) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(idxlist);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%SortBegin",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     Runs method sys_SortBegin in Cache
     @param db represented as Database
     @param idxlist represented as com.intersys.objects.SList
     @param excludeunique represented as java.lang.Integer
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%SortBegin"> Method %SortBegin</A>
    */
    public static void sys_SortBegin (Database db, com.intersys.objects.SList idxlist, java.lang.Integer excludeunique) throws CacheException {
        Dataholder[] args = new Dataholder[2];
        args[0] = new Dataholder(idxlist);
        args[1] = new Dataholder(excludeunique);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%SortBegin",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }

    /**
     Runs method sys_SortEnd in Cache
     @param db represented as Database
     @param idxlist set to ""
     @param commit set to 1
     @throws CacheException if any error occured while running the method.
     @see #sys_SortEnd(Database,com.intersys.objects.SList,java.lang.Integer)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%SortEnd"> Method %SortEnd</A>
    */
    public static void sys_SortEnd (Database db) throws CacheException {
        Dataholder[] args = new Dataholder[0];
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%SortEnd",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     Runs method sys_SortEnd in Cache
     @param db represented as Database
     @param idxlist represented as com.intersys.objects.SList
     @param commit set to 1
     @throws CacheException if any error occured while running the method.
     @see #sys_SortEnd(Database,com.intersys.objects.SList,java.lang.Integer)
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%SortEnd"> Method %SortEnd</A>
    */
    public static void sys_SortEnd (Database db, com.intersys.objects.SList idxlist) throws CacheException {
        Dataholder[] args = new Dataholder[1];
        args[0] = new Dataholder(idxlist);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%SortEnd",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     Runs method sys_SortEnd in Cache
     @param db represented as Database
     @param idxlist represented as com.intersys.objects.SList
     @param commit represented as java.lang.Integer
     @throws CacheException if any error occured while running the method.
     @see <a href = "http://fast:1972/apps/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=MVS&CLASSNAME=mvs.Word3#%SortEnd"> Method %SortEnd</A>
    */
    public static void sys_SortEnd (Database db, com.intersys.objects.SList idxlist, java.lang.Integer commit) throws CacheException {
        Dataholder[] args = new Dataholder[2];
        args[0] = new Dataholder(idxlist);
        args[1] = new Dataholder(commit);
        Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%SortEnd",args,Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }

    public static CacheQuery query_Extent (Database db) throws CacheException {
        return new CacheQuery(db, "mvs.Word3_Extent", 0, 0);
    }

}
