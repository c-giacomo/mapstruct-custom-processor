package it.generic.mapper.model;


public interface GenericAbstractMapper {
	
	/**
	 * Maps from an object to a new instance of an object.
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	<T> T map(Object from, Class<T> to);

	/**
	 * Maps from an exsisting objec TO an existing object.
	 * 
	 * @param from
	 * @param to
	 * @return
	 * */
	 
	<T> T map(Object from, Object to);
	
	
	@SuppressWarnings("rawtypes")
	boolean hasRegisteredMapper(Class from, Class to);
	
	@SuppressWarnings("deprecation")
	static GenericAbstractMapper getInstance() {
		try {
			return (GenericAbstractMapper)Class.forName( "it.generic.mapper.common.GenericBeansMapper" ).newInstance();
		}
		catch ( ClassNotFoundException | InstantiationException | IllegalAccessException ex ) {
			throw new IllegalStateException( ex );
		}
	}


}
