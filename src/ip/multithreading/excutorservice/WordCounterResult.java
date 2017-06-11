package ip.multithreading.excutorservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WordCounterResult {
	
	private Map<String, Integer> wordCount = new HashMap<>();
	private long totalCount;
	
	public void addWord(String word){
		totalCount++;
		Integer existingCount = wordCount.get(word);
		wordCount.put(word, (existingCount == null ? 1 : ++existingCount));
	}
	
	public WordCounterResult merge(WordCounterResult result){
		WordCounterResult returnResult = new WordCounterResult();
		returnResult.totalCount = totalCount + result.totalCount;
		returnResult.wordCount = result.wordCount;
		Iterator<String> iterator = wordCount.keySet().iterator();
		while(iterator.hasNext()){
			String word = iterator.next();
			Integer existingCount = result.wordCount.get(word);
			returnResult.wordCount.put(word, (existingCount == null ? wordCount.get(word) : wordCount.get(word) + existingCount));
		}
		return returnResult;
	}

	public Map<String, Integer> getWordCount() {
		return wordCount;
	}

	public long getTotalCount() {
		return totalCount;
	}
	
	public Map<String, Integer> getSortedWordCount(final boolean ascending){
		
		List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCount.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				if(ascending){
					return o1.getValue().compareTo(o2.getValue());
				}else{
					return o2.getValue().compareTo(o1.getValue());
				}
			}
		});
		
		Map<String, Integer> sortedMap = new LinkedHashMap<>();
		for(Map.Entry<String, Integer> mapEntry : list){
			sortedMap.put(mapEntry.getKey(), mapEntry.getValue());
		}
		
		return sortedMap;
	}
	
}
