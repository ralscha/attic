package ch.ralscha.mycustomers.server;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericDaoService<T, ID extends Serializable> {

  private Class<T> entityType;
  private EntityManager entityManager;

  @SuppressWarnings("unchecked")
  public GenericDaoService() {
    entityType = (Class<T>)getTypeArguments(GenericDaoService.class, getClass()).get(0);
  }

  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

//  public FullTextEntityManager getFullTextEntityManager() {
//    return Search.getFullTextEntityManager(getEntityManager());
//  }

  protected Class<T> getEntityType() {
    return entityType;
  }

  public T find(ID id) {
    return getEntityManager().find(getEntityType(), id);
  }

  public T getReference(ID id) {
    return getEntityManager().getReference(getEntityType(), id);
  }

  @SuppressWarnings("unchecked")
  public List<T> findAll() {
    Query query = getEntityManager().createQuery("from " + getEntityType().getName());
    return query.getResultList();
  }

  public T merge(T entity) {
    return getEntityManager().merge(entity);
  }

  public void remove(T entity) {
    T mergedEntity = merge(entity);
    getEntityManager().remove(mergedEntity);
  }

  public void flush() {
    getEntityManager().flush();
  }

  @SuppressWarnings("unchecked")
  public List<T> findByCriteria(Criterion... criterions) {
    Criteria criteria = createCriteria(criterions);
    return criteria.list();
  }

  public Criteria createCriteria(Criterion... criterions) {
    Session session = (Session)getEntityManager().getDelegate();
    Criteria criteria = session.createCriteria(getEntityType());
    for (Criterion criterion : criterions) {
      criteria.add(criterion);
    }
    return criteria;
  }

  @SuppressWarnings("unchecked")
  public static <T> List<Class< ? >> getTypeArguments(Class<T> baseClass, Class< ? extends T> childClass) {
    Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
    Type type = childClass;
    // start walking up the inheritance hierarchy until we hit baseClass
    while (!getClass(type).equals(baseClass)) {
      if (type instanceof Class) {
        // there is no useful information for us in raw types, so just keep going.
        type = ((Class)type).getGenericSuperclass();
      } else {
        ParameterizedType parameterizedType = (ParameterizedType)type;
        Class< ? > rawType = (Class)parameterizedType.getRawType();

        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        TypeVariable< ? >[] typeParameters = rawType.getTypeParameters();
        for (int i = 0; i < actualTypeArguments.length; i++) {
          resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
        }

        if (!rawType.equals(baseClass)) {
          type = rawType.getGenericSuperclass();
        }
      }
    }

    // finally, for each actual type argument provided to baseClass, determine (if possible)
    // the raw class for that type argument.
    Type[] actualTypeArguments;
    if (type instanceof Class) {
      actualTypeArguments = ((Class)type).getTypeParameters();
    } else {
      actualTypeArguments = ((ParameterizedType)type).getActualTypeArguments();
    }
    List<Class< ? >> typeArgumentsAsClasses = new ArrayList<Class< ? >>();
    // resolve types by chasing down type variables.
    for (Type baseType : actualTypeArguments) {
      while (resolvedTypes.containsKey(baseType)) {
        baseType = resolvedTypes.get(baseType);
      }
      typeArgumentsAsClasses.add(getClass(baseType));
    }
    return typeArgumentsAsClasses;
  }

  @SuppressWarnings("unchecked")
  public static Class< ? > getClass(Type type) {
    if (type instanceof Class) {
      return (Class)type;
    } else if (type instanceof ParameterizedType) {
      return getClass(((ParameterizedType)type).getRawType());
    } else if (type instanceof GenericArrayType) {
      Type componentType = ((GenericArrayType)type).getGenericComponentType();
      Class< ? > componentClass = getClass(componentType);
      if (componentClass != null) {
        return Array.newInstance(componentClass, 0).getClass();
      }
      return null;
    } else {
      return null;
    }
  }
}
