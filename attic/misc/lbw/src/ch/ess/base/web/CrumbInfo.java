package ch.ess.base.web;

import com.cc.framework.adapter.struts.ActionContext;

public interface CrumbInfo {
  String getTitle(String id, ActionContext ctx);
}
