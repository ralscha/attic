package ch.rasc.todo.dto;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.todo.Application;

public class ExtRequest {

	private Integer page;

	private Integer start;

	private Integer limit;

	private String sort;

	private String filter;

	public List<Sort> getSortObjects(ObjectMapper om) {
		if (StringUtils.hasText(this.sort)) {
			try {
				return om.readValue(this.sort, new TypeReference<List<Sort>>() {
					/* nothing_here */
				});
			}
			catch (IOException e) {
				Application.logger.error("convert sort objects", e);
			}
		}
		return Collections.emptyList();
	}

	public List<Filter> getFilterObjects(ObjectMapper om) {
		if (StringUtils.hasText(this.filter)) {
			try {
				return om.readValue(this.filter, new TypeReference<List<Filter>>() {
					/* nothing_here */
				});
			}
			catch (IOException e) {
				Application.logger.error("convert filter objects", e);
			}
		}
		return Collections.emptyList();
	}

	public Integer getPage() {
		return this.page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStart() {
		return this.start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getFilter() {
		return this.filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
		result = prime * result + ((limit == null) ? 0 : limit.hashCode());
		result = prime * result + ((page == null) ? 0 : page.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtRequest other = (ExtRequest) obj;
		if (filter == null) {
			if (other.filter != null)
				return false;
		}
		else if (!filter.equals(other.filter))
			return false;
		if (limit == null) {
			if (other.limit != null)
				return false;
		}
		else if (!limit.equals(other.limit))
			return false;
		if (page == null) {
			if (other.page != null)
				return false;
		}
		else if (!page.equals(other.page))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		}
		else if (!sort.equals(other.sort))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		}
		else if (!start.equals(other.start))
			return false;
		return true;
	}

}
