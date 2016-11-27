package @packageProject@.service;

import java.util.Map;


public interface PagedQueryFinder {
  Map<String, Object> find(Map<String, Object> filter, int first, int max, String order, boolean desc);
}
