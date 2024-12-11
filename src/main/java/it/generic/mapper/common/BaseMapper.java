package it.generic.mapper.common;

import org.mapstruct.MappingTarget;

/**
 * @author gchiavolotti
 * @param <S>
 * @param <D>
 */

public interface BaseMapper<S, D> {
	
	/**
	 * mapper from S to D
	 * 
	 * @param S
	 * @return D
	 * 
	 */
	D map(S s);
	
	/**
	 * Update the object.
	 * 
	 * @param D
	 * @param S
	 * @return D
	 */
	D update(S from, @MappingTarget D to);
	
}
