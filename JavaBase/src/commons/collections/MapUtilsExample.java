package commons.collections;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 14345
 *	MapUtils 类的例子
 */
public class MapUtilsExample {

	private Map<String, Object> map1;
	
	@Before
	public void initData() {
		map1.put("str", "ABC");
		map1.put("int", 123);
		map1.put("doub", 1.23D);
		map1.put("long", 123L);
		map1.put("boo", true);
		map1.put("byte", 1);
	}
	
	/** 格式化打印 */
	@Test
	public void testDebugPrint() {
//		MapUtils.debugPrint(out, , map);
	}

	/** 判断map是否为null,如是null,则返回一个空map,否则返回参数本身  */
	@Test
	public void	testEmptyIfNull(){
		
	}

	/** 返回传入map的对应固定map */
	public void testFixedSizeMap(){
		
	}

	/**返回传入有序map对应的固定map */
	public void	testFixedSizeSortedMap() {
		
	}
	
	/** 返回map中key对应的boolean类型的值,如果不存在则返回null */
	public void testGetBoolean() {
		
	}

	/** 返回map中key对应的boolean类型的值,如果不存在则返回默认值 */
	public void testGetBoolean2() {
		
	}

	/** 返回map中key对应的boolean类型的值,如果不存在则返回null */
	public void	testGetBooleanValue() {
		
	}

	/** 返回map中key对应的boolean类型的值,如果不存在则返回默认值 */
	public void	testGetBooleanValue2() {
		
	}

	/** 返回map中key对应的byte类型的值,如果不存在则返回null */
	public void testGetByte() {
		
	}

	/** 返回map中key对应的byte类型的值,如果不存在则返回默认值 */
	public void	testGetByte2() {
		
	}

	public void	getByteValue() {
		
	}

	public void	getByteValue2() {
		
	}

	
	public void getDouble() {
		
	}

	public void	getDouble2() {
		
	}

	public void getDoubleValue() {
		
	}

	public void getDoubleValue2() {
		
	}

	public void	getFloat() {
		
	}

	public void	getFloat2() {
		
	}

	public void getFloatValue() {
		
	}

	public void getFloatValue2() {
		
	}

	public void getInteger() {
		
	}

	public void getInteger2() {
		
	}

	public void	getIntValue() {
		
	}

	public void	getIntValue2() {
		
	}

	public void	getLong() {
		
	}

	public void	getLong2() {
		
	}

	public void	getLongValue() {
		
	}

	public void getLongValue2() {
		
	}

	public void	getMap() {
		
	}

	public void	getMap() {
		
	}

	public void	getNumber() {
		
	}

	public void	getNumber() {
		
	}

	public void	getObject() {
		
	}

	public void	getObject2() {
		
	}

	public void	getShort() {
		
	}

	public void	getShort2() {
		
	}

	public void getShortValue() {
		
	}

	public void	getShortValue2() {
		
	}

	public void	getString() {
		
	}

	public void	getString2() {
		
	}

	
	//Inverts the supplied map returning a new HashMap such that the keys of the input are swapped with the values.
	public void	invertMap(){
		
	}

	//Null-safe check if the specified map is empty.
	public void	isEmpty() {
		
	}

	//Null-safe check if the specified map is not empty.
	public void	isNotEmpty() {
		
	}

	//Get the specified Map as an IterableMap.
	public void	iterableMap() {
		
	}

	//Get the specified SortedMap as an IterableSortedMap.
	public <K,V> IterableSortedMap<K,V>	iterableSortedMap(SortedMap<K,V> sortedMap)

	//Returns a "lazy" map whose values will be created on demand.
	public <K,V> IterableMap<K,V>	lazyMap(Map<K,V> map, Factory<? extends V> factory)

	//Returns a "lazy" map whose values will be created on demand.
	public <K,V> IterableMap<K,V>	lazyMap(Map<K,V> map, Transformer<? super K,? extends V> transformerFactory)

	//Returns a "lazy" sorted map whose values will be created on demand.
	public <K,V> SortedMap<K,V>	lazySortedMap(SortedMap<K,V> map, Factory<? extends V> factory)

	//Returns a "lazy" sorted map whose values will be created on demand.
	public <K,V> SortedMap<K,V>	lazySortedMap(SortedMap<K,V> map, 
	Transformer<? super K,? extends V> transformerFactory)

	//Deprecated. 
	//since 4.1, use MultiValuedMap instead
	public <K,V> MultiValueMap<K,V>	multiValueMap(Map<K,? super Collection<V>> map)

	//Deprecated. 
	//since 4.1, use MultiValuedMap instead
	public <K,V,C extends Collection<V>>
	MultiValueMap<K,V>	multiValueMap(Map<K,C> map, Class<C> collectionClass)

	//since 4.1, use MultiValuedMap instead
	//Deprecated. 
	public <K,V,C extends Collection<V>>
	MultiValueMap<K,V>	multiValueMap(Map<K,C> map, Factory<C> collectionFactory)

	//Returns a map that maintains the order of keys that are added backed by the given map.
	public <K,V> OrderedMap<K,V>	orderedMap(Map<K,V> map)

	//Populates a Map using the supplied Transformers to transform the elements into keys and values.
	public <K,V,E> void	populateMap(Map<K,V> map, Iterable<? extends E> elements, Transformer<E,K> keyTransformer, Transformer<E,V> valueTransformer)

	//Populates a Map using the supplied Transformer to transform the elements into keys, using the unaltered element as the value in the Map.
	public <K,V> void	populateMap(Map<K,V> map, Iterable<? extends V> elements, Transformer<V,K> keyTransformer)

	//Populates a MultiMap using the supplied Transformers to transform the elements into keys and values.
	public <K,V,E> void	populateMap(MultiMap<K,V> map, Iterable<? extends E> elements, Transformer<E,K> keyTransformer, Transformer<E,V> valueTransformer)

	//Populates a MultiMap using the supplied Transformer to transform the elements into keys, using the unaltered element as the value in the MultiMap.
	public <K,V> void	populateMap(MultiMap<K,V> map, Iterable<? extends V> elements, Transformer<V,K> keyTransformer)

	//Returns a predicated (validating) map backed by the given map.
	public <K,V> IterableMap<K,V>	predicatedMap(Map<K,V> map, Predicate<? super K> keyPred, Predicate<? super V> valuePred)

	//Returns a predicated (validating) sorted map backed by the given map.
	public <K,V> SortedMap<K,V>	predicatedSortedMap(SortedMap<K,V> map, Predicate<? super K> keyPred, Predicate<? super V> valuePred)

	//Puts all the keys and values from the specified array into the map.
	public <K,V> Map<K,V>	putAll(Map<K,V> map, Object[] array)

	//Protects against adding null values to a map.
	public <K> void	safeAddToMap(Map<? super K,Object> map, K key, Object value)

	//Gets the given map size or 0 if the map is null
	public int	size(Map<?,?> map)

	//Returns a synchronized map backed by the given map.
	public <K,V> Map<K,V>	synchronizedMap(Map<K,V> map)

	//Returns a synchronized sorted map backed by the given sorted map.
	public <K,V> SortedMap<K,V>	synchronizedSortedMap(SortedMap<K,V> map)

	//Creates a new HashMap using data copied from a ResourceBundle.
	public Map<String,Object>	toMap(ResourceBundle resourceBundle)

	//Gets a new Properties object initialised with the values from a Map.
	public <K,V> Properties	toProperties(Map<K,V> map)

	//Returns a transformed map backed by the given map.
	public <K,V> IterableMap<K,V>	transformedMap(Map<K,V> map, Transformer<? super K,? extends K> keyTransformer, Transformer<? super V,? extends V> valueTransformer)

	//Returns a transformed sorted map backed by the given map.
	public <K,V> SortedMap<K,V>	transformedSortedMap(SortedMap<K,V> map, Transformer<? super K,? extends K> keyTransformer, Transformer<? super V,? extends V> valueTransformer)

	//Returns an unmodifiable map backed by the given map.
	public <K,V> Map<K,V>	unmodifiableMap(Map<? extends K,? extends V> map)

	//Returns an unmodifiable sorted map backed by the given sorted map.
	public <K,V> SortedMap<K,V>	unmodifiableSortedMap(SortedMap<K,? extends V> map)

	//Prints the given map with nice line breaks.
	public void	verbosePrint(PrintStream out, Object label, Map<?,?> map)
}
