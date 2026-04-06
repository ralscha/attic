package ch.rasc.httpcopy.client;

import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class WorkJob {
	private String filename;

	private final Path file;

	public WorkJob(Path baseDir, Path file) {
		this.filename = StreamSupport.stream(file.spliterator(), false)
				.map(Path::toString).collect(Collectors.joining("/"));

		this.file = baseDir.resolve(file);
	}

	public Path getFile() {
		return this.file;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.file == null ? 0 : this.file.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WorkJob other = (WorkJob) obj;
		if (this.file == null) {
			if (other.file != null) {
				return false;
			}
		}
		else if (!this.file.equals(other.file)) {
			return false;
		}
		return true;
	}

}
