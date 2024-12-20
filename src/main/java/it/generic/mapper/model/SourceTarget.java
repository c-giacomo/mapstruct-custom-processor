package it.generic.mapper.model;

public class SourceTarget {
	
	@SuppressWarnings("rawtypes")
	public final Class source;
	@SuppressWarnings("rawtypes")
	public final Class target;
	
	@SuppressWarnings("rawtypes")
	public SourceTarget(Class source, Class target) {
		this.source = source;
		this.target = target;
	}

	public String toString() {
		return source.getSimpleName() + ":" + target.getSimpleName();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
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
		SourceTarget other = (SourceTarget) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

}
