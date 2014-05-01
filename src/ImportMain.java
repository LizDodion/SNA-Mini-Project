import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class ImportMain {
	private static int totalMessages = 517424;
	private static String[] msgIds = new String[totalMessages];
	private static String[] topics = new String[totalMessages];
	private static String[] dates = new String[totalMessages];
	private static String[] contents = new String[totalMessages];
	private static Set<String> allRecipients= new HashSet<String>();
//	private static Map<String, List<HashMap<String, Float>>> bccComms2 = new HashMap<String, List< HashMap<String,Float>>>();
	private static Map<String,List<String>> ccComms = new HashMap<String, List<String>>();
	private static Map<String,List<String>> toComms = new HashMap<String, List<String>>();
	private static Map<String,List<String>> bccComms = new HashMap<String, List<String>>();
	private static Map<String,List<String>> comms = new HashMap<String, List<String>>();
	private static Map<String,List<String>> msgLink = new HashMap<String, List<String>>();
	private static int fileNum = 0;
	private static String[] stopwords = {"enron", "a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
	private static Set<String> stopWordSet = new HashSet<String>(Arrays.asList(stopwords));
	private static boolean filesProcessed;
	private static File curDirectory;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static boolean isStopword(String word) {
		if(word.length() < 2) return true;
		if(word.charAt(0) >= '0' && word.charAt(0) <= '9') return true; //remove numbers, "25th", etc
		if(stopWordSet.contains(word)) {
			return true;
		}		
		else return false;
	}

	private static String removeStopWords(String string) {
		String result = "";
		string = string.replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase();
		String[] words = string.split("\\s+");
		for(String word : words) {
			if(word.isEmpty()) {continue;}
			if(isStopword(word)) {
				continue;
				} 
			result += (word+" ");
		}
		return result;
	}
	
	private static void processFiles(File[] files) {
		List<String> listStr = new ArrayList<String>();
		
	    for (File file : files) {
	        if (file.isDirectory()) {
	            System.out.println("Directory: " + file.getName());
	            processFiles(file.listFiles()); // Calls same method again.
	        } else {
	        	if(!file.toString().contains("DS_Store")){
			    	try {
			    	String	file_name = file.getAbsolutePath();
						ReadFile rf = new ReadFile(file_name);
						String[] aryLines = rf.OpenFile();
						String[] tmpStrings = rf.recipientsList();
						String sender = aryLines[2].substring(6).replace('-', '_');
						String[] str = new String[tmpStrings.length];
						for (int k = 0; k < tmpStrings.length;k++){
							str[k] = tmpStrings[k].substring(1);
							//allRecipients.add(tmpStrings[k].substring(1));
						//	System.out.println("added "+tmpStrings[k].substring(1)+" to recipients");
						}
						for (int l = 0;l < str.length;l++){
							if(comms.containsKey(sender)){listStr = comms.get(sender);
							//	List<HashMap<String, Float>> list = bccComms.get(sender);
							
							//to add to the toComms map
								if(toComms.containsKey(sender)){
									List<String> listTo = toComms.get(sender);
									listTo.add(str[l].replace('-', '_'));
									toComms.put(sender,listTo);
								}
								else{
									List<String> listTo = new ArrayList<String>();
									listTo.add(str[l].replace('-', '_'));
									toComms.put(sender,listTo);
								}
							
								//System.out.println("outside else");
								if(str[l].length()!=0){
									listStr.add(str[l].replace('-', '_'));								
								allRecipients.add(str[l].replace('-', '_'));
						//		System.out.println("added "+str[l].replace('-', '_')+" to recipients");
								comms.put(sender, listStr);
								}
							}
							else {
								if(str[l].length()!=0){
								if(toComms.containsKey(sender)){
									List<String> listTo = toComms.get(sender);
									listTo.add(str[l].replace('-', '_'));
									toComms.put(sender,listTo);
								}
								else{
									List<String> listTo = new ArrayList<String>();
									listTo.add(str[l].replace('-', '_'));
									toComms.put(sender,listTo);
									}
								listStr = new ArrayList<String>();
									listStr.add(str[l].replace('-', '_'));
									allRecipients.add(str[l].replace('-', '_'));
								//	System.out.println("added "+str[l].replace('-', '_')+" to recipients");
								
									comms.put(sender, listStr);	
								}
								}
							}
				
						
						if (rf.cCrecipients()!=null){
							String[] tmpcCStrings = rf.cCrecipients();
							String[] cCstr = new String[tmpcCStrings.length];
							for (int k = 0; k < tmpcCStrings.length;k++){
								cCstr[k] = tmpcCStrings[k].substring(1);
							}
							
							for (int k = 0; k < tmpcCStrings.length;k++){
								for (int l = 0;l < cCstr.length;l++){
									if(comms.containsKey(sender)){
										if(ccComms.containsKey(sender)){
											List<String> listTo = ccComms.get(sender);
											listTo.add(cCstr[l].replace('-', '_'));
											ccComms.put(sender,listTo);
										}
										else{
											List<String> listTo = new ArrayList<String>();
											listTo.add(cCstr[l].replace('-', '_'));
											ccComms.put(sender,listTo);
										}
										listStr = comms.get(sender);
										if(cCstr[l].length()!=0){
											listStr.add(cCstr[l].replace('-', '_'));
											comms.put(sender, listStr);
											}
										}
									else {
										if(cCstr[l].length()!=0){
											listStr = new ArrayList<String>();
											listStr.add(cCstr[l].replace('-', '_'));
											comms.put(sender, listStr);
											}
										}
									}
							}
						}
						
						if (rf.bcCrecipients()!=null){
							String[] tmpbcCStrings = rf.bcCrecipients();
							String[] bcCstr = new String[tmpbcCStrings.length];
							for (int k = 0; k < tmpbcCStrings.length;k++){
								bcCstr[k] = tmpbcCStrings[k].substring(1);
							}
							
							for (int k = 0; k < tmpbcCStrings.length;k++){
								for (int l = 0;l < bcCstr.length;l++){
									if(comms.containsKey(sender)){
										if(bcCstr[l].length()!=0){	
											if(bccComms.containsKey(sender)){
												List<String> listTo = bccComms.get(sender);
												listTo.add(bcCstr[l].replace('-', '_'));
												bccComms.put(sender,listTo);
											}
											else{
												List<String> listTo = new ArrayList<String>();
												listTo.add(bcCstr[l].replace('-', '_'));
												bccComms.put(sender,listTo);
											}
										listStr = comms.get(sender);
										listStr.add(bcCstr[l].replace('-', '_'));
										comms.put(sender, listStr);}
									}
									else {
										if(bcCstr[l].length()!=0){	
										listStr = new ArrayList<String>();
											listStr.add(bcCstr[l].replace('-', '_'));
											comms.put(sender, listStr);	
										}
										}
									}
							}
						}
						
						int contentValue = rf.mainContentStart();
						
						msgIds[fileNum] = aryLines[0].substring(13, aryLines[0].length() -1);
						dates[fileNum] = aryLines[1].substring(6);
						topics[fileNum] = aryLines[rf.subjectLine()].substring(9);
						List<String> msgIdsList = new ArrayList<String>();
						
						if(msgLink.containsKey(sender)){
							msgIdsList = msgLink.get(sender);
							msgIdsList.add(aryLines[0].substring(13, aryLines[0].length() -1));
							msgLink.remove(sender);
							msgLink.put(sender, msgIdsList);
						}
						else{
							msgIdsList.add(aryLines[0].substring(13, aryLines[0].length() -1));
							msgLink.put(sender, msgIdsList);
						}
						
						
						int i;
						contents[fileNum]="\n";
						for (i=contentValue+1; i <aryLines.length; i++){
							String tmp = "\n";
							if(aryLines[i].length()==0){
								tmp = contents[fileNum]+ "\n";
							}
							else{
								tmp = contents[fileNum]+aryLines[i]+"\n";
							}
								contents[fileNum] = tmp;
							
						}

					}
					catch (IOException e){
						System.out.println(e.getMessage());
					}
			    	fileNum++;
	        	}
	        }
	    }
	}
	public static void main(String[] args) throws IOException, InterruptedException{
		curDirectory = new File("/Users/DodionTwo/Downloads/enron_mail_20110402/maildir2");
		System.out.println("Enter query: ");
		while (true) {
			

			
				System.out.print("> "); System.out.flush();
			String input = in.readLine(); // waits for input
			
			
			List<String> argsInput = Arrays.asList(input.split("\\s+"));
			if (argsInput.contains("processFiles")){
				processFiles();
				
			}
			
			else if (argsInput.contains("help")){
				help();
				
			}
			
			else if (argsInput.contains("contacts")){
				contacts();
				
			}
			else if (argsInput.contains("getAllComms")){
				getAllCommsMIDs();
				}

			else if (argsInput.contains("getAllDates")){
				getAllDates();
				}
			
			else if (argsInput.contains("getDate")){
				getDate();
			}
			
			else if (argsInput.contains("getMID")){
				getMID();
				}
			
			
			else if (argsInput.contains("getContent")){
				getContent();
			}
			
			else if (argsInput.contains("contents")){
				contents();
				}
			
			else if (argsInput.contains("getAllMIDs")){
				getAllMIDs();
				
			}
			
			else if (argsInput.contains("currentDirectory")){
				currentDirectory();
				}
			else if (argsInput.contains("setDirectory")){
				setDirectory();
				}
			
			
			else if (argsInput.contains("splitSentence")){
				topWordsByEmail();
				
			}
			
				else {
					System.out.println("For help, type help");
					}
					
			}
			
		}
	private static void help() throws IOException {
		System.out.println("press the number corresponding to the corresponding function or return and type the corresponding function"
				+ "\n 0 = return"
				+ "\n 1 = contacts "
				+ "\n 2 = getAllCommsMIDs "
				+ "\n 3 = getAllDates "
				+ "\n 4 = getDate"
				+ "\n 5 = getMID"
				+ "\n 6 = getContent"
				+ "\n 7 = contents"
				+ "\n 8 = getAllMIDs"
				+ "\n 9 = currentDirectory"
				+ "\n 10 = setDirectory"
				+ "\n 11 = topWordsByEmail"
				+ "\n 12 = processFiles"
				+ "\n 13 = adjacencyMatrix");
		String input2 = in.readLine();
		List<String> argsInput2 = Arrays.asList(input2.split("\\s+"));
		
		if (argsInput2.contains("13")||argsInput2.contains("adjacencyMatrix")){
			System.out.println("adjacencyMatrix");
			adjacencyMatrix();
		}
		
		if (argsInput2.contains("12")||argsInput2.contains("processFiles")){
			System.out.println("processFiles ...");
			processFiles();
		}
		
		else if (argsInput2.contains("11")||argsInput2.contains("topWordsByEmail")){
			System.out.println("topWordsByEmail ...");
			topWordsByEmail();
		}			
		
		else if (argsInput2.contains("10")||argsInput2.contains("setDirectory")){
			System.out.println("setDirectory ...");
			setDirectory();
		}
		else if (argsInput2.contains("9")||argsInput2.contains("currentDirectory")){
			System.out.println("currentDirectory ...");
			currentDirectory();
		}
		else if (argsInput2.contains("8")||argsInput2.contains("getAllMIDs")){
			System.out.println("getAllMIDs ...");
			getAllMIDs();
		}
		else if (argsInput2.contains("7")||argsInput2.contains("contents")){
			System.out.println("contents ...");
			contents();
		}
		else if (argsInput2.contains("6")||argsInput2.contains("getContent")){
			System.out.println("getContent ...");
			getContent();
		}
		else if (argsInput2.contains("5")||argsInput2.contains("getMID")){
			System.out.println("getMID ...");
			getMID();
		}
		else if (argsInput2.contains("4")||argsInput2.contains("getDate")){
			System.out.println("getDate ...");
			getDate();
		}
		else if (argsInput2.contains("3")||argsInput2.contains("getAllDates")){
			System.out.println("getAllDates ...");
			getAllDates();
		}
		else if (argsInput2.contains("2")|| argsInput2.contains("getAllCommsMIDs")){
			System.out.println("getAllCommsMIDs ...");
			getAllCommsMIDs();
		}
		else if (argsInput2.contains("1")||argsInput2.contains("contacts")){
			System.out.println("contacts ...");
			contacts();
		}

		else{System.out.println("home ...");
		
		}
	
	}

	private static void adjacencyMatrix() throws IOException{
		// TODO Auto-generated method stub
		//comms.keySet();
		
		System.out.println("Please enter a directory and file name to save the Matrix to (with a .csv extension), or leave blank for the default directory");
		String input3 = in.readLine();
		List<String> argsInput3 = Arrays.asList(input3.split("\\s+"));
		String tempDir = "/Users/DodionTwo/Documents/EnronEmails/AdjacencyMatrix.csv";
		if (!(argsInput3.get(0).length()==0)){			
			tempDir = argsInput3.get(0);
		}
		FileWriter fw = new FileWriter(tempDir,false);
		fw.flush();
		Set<String> complete = new HashSet<String>();
		complete.addAll(comms.keySet());
		complete.addAll(allRecipients);
		String[] senders = comms.keySet().toArray(new String[0]);
		String[] recipients = allRecipients.toArray(new String[0]);
		Arrays.sort(senders);
		Arrays.sort(recipients);
		List<String> sendList = new ArrayList<String>();
		List<String> recipList = new ArrayList<String>();
		sendList.addAll(comms.keySet());
		List<String> completeList = new ArrayList<String>();
		completeList.addAll(complete);
		Collections.sort(completeList);
		String[] completeArray = completeList.toArray(new String[0]);
		recipList.addAll(allRecipients);
		Collections.sort(sendList);
		Collections.sort(recipList);
		
	//	int sendSize = senders.length;
	//	int recSize = recipients.length;
		int completeSize = complete.size();
		String[][] matrix = new String[completeSize+1][completeSize+1];
		matrix[0][0] = "";
		
		//set outside labels
	/*	for (int i = 0; i< sendSize;i++){
			matrix[i+1][0] = senders[i];
		}
		for (int i = 0; i< recSize;i++){
			matrix[0][i+1] = recipients[i];
		}*/
		for (int i = 0; i< completeSize;i++){
			matrix[i+1][0] = completeArray[i];
			matrix[0][i+1] = completeArray[i];
		}
		System.out.println("Adding entry");
		for (String s: toComms.keySet()){
		 List<String> st = toComms.get(s);
		 int len = st.size();
		 for (int i = 0; i < len; i++){
			 String rec = st.get(i);
			 if(rec.length()!=0){
			 int yIndex = completeList.indexOf(rec);
			 int xIndex = completeList.indexOf(s);
		//	 System.out.println("Processing ... s = "+s+", rec = "+rec+", x = "+xIndex +", y = "+yIndex);
			 String tmp =  matrix[xIndex+1][yIndex+1];
			 if (tmp == null){
			//	 System.out.print("old = 0, ");
				 tmp = Integer.toString(3);
			//	 System.out.println("new = "+tmp);
			 }
			 else {				 
			//	 System.out.print("old = "+tmp);
				 int tmpInt = Integer.parseInt(tmp);
				 tmp = Integer.toString(tmpInt +3);
				// System.out.println(", new = "+ tmp);
			 }
					 //Integer.toString();
			 matrix[xIndex+1][yIndex+1] = tmp;
			 }
		 }
		}
		

		for (int i = 0; i <completeSize; i++){
			
			for (int j = 0; j < completeSize;j++){
				if (matrix[i][j]!=null){
					fw.write(matrix[i][j]);
				}
				else{
					fw.write(Integer.toString(0));
				}
				fw.write(",");			

			}
			if(matrix[i][completeSize]!=null){
			fw.write(matrix[i][completeSize]);
			}
			else{
				fw.write(Integer.toString(0));
			}
			fw.write("\n");
			
		}
		for (int j = 0; j < completeSize;j++){
			if(matrix[completeSize][j]!= null){
				fw.write(matrix[completeSize][j]);
			}
			else{
				fw.write(Integer.toString(0));
			}
			fw.write(",");
		}
		if(matrix[completeSize][completeSize]!=null){
		fw.write(matrix[completeSize][completeSize]);
		}
		else{
			fw.write(Integer.toString(0));
		}
		fw.close();
	}

	private static void topWordsByEmail() throws IOException {
		System.out.println("Please indicate the file number of the email you wish to process ");
		String input2 = in.readLine();
		List<String> argsInput2 = Arrays.asList(input2.split("\\s+"));
		int index = (int) Float.parseFloat(argsInput2.get(0));
		System.out.println("Please indicate how many words you wish to return");
		String input3 = in.readLine();
		List<String> argsInput3 = Arrays.asList(input3.split("\\s+"));
		int number = (int) Float.parseFloat(argsInput3.get(0));

		String tempstr = contents[index];

		tempstr = removeStopWords(tempstr);
		String[] words = tempstr.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
		List<String> wordList = Arrays.asList(words);
		Set<String> mySet = new HashSet<String>(wordList);

		Map<String,Integer> temp = new HashMap<String, Integer>();
		for(String s: mySet){
			temp.put(s, Collections.frequency(wordList,s));
		}

		
		
		
		SortedSet<Entry<String, Integer>> sorted = entriesSortedByValues(temp);
		System.out.println(sorted);
		System.out.print("Top "+number+" entries for email "+index+" = [");
		
		Iterator<Entry<String, Integer>> it = sorted.iterator();
		for(int i = 0; i < number-1;i++){
			if (it.hasNext()){
			Entry<String, Integer> s = it.next();
			System.out.print(s.getKey()+", ");}
			
		}
		Entry<String, Integer> s = it.next();
		System.out.print(s.getKey());
		System.out.println("]");
	
		System.out.println("home ...");
	}

	private static void setDirectory() throws IOException {
		System.out.println("Please indicate the directory you wish to use ");
		String input2 = in.readLine();
		List<String> argsInput2 = Arrays.asList(input2.split("\\s+"));
		curDirectory = new File(argsInput2.get(0));
		System.out.println("new directory = "+curDirectory);
		
		System.out.println("home ...");		
	}

	private static void currentDirectory() {
		System.out.println(curDirectory);
		System.out.println("home ...");
	}

	private static void getAllMIDs() {
		System.out.print(fileNum +" MIDs = [");
		for (int i = 0; i < fileNum;i++){
				System.out.print(msgIds[i]+"; ");
		}
		System.out.println("]");
		System.out.println("home ...");
	}

	private static void contents() throws IOException {

		System.out.println("Please indicate where you would like the data to be stored\n 0 = home \n 1 = In-line \n 2 = To-file");
		String input2 = in.readLine();
		List<String> argsInput2 = Arrays.asList(input2.split("\\s+"));
		
		if(argsInput2.contains("1")){
		System.out.println("Total emails = "+fileNum);
		System.out.print(fileNum+" emails = [");
				for (int i = 0; i < fileNum;i++){
						System.out.print(contents[i]+"; ");
				}
				System.out.println("]");
		}
		
		else if(argsInput2.contains("2")){
			System.out.println("total number of senders = "+comms.size());
			System.out.println("Please enter a directory and file name to save the contacts to, or leave blank for the default directory");
			String input3 = in.readLine();
			List<String> argsInput3 = Arrays.asList(input3.split("\\s+"));
			String tempDir = "/Users/DodionTwo/Documents/EnronEmails/Contents";

			if (!(argsInput3.get(0).length()==0)){
				tempDir = argsInput3.get(0);
			}
			System.out.print(fileNum+" emails ");
			for (int i = 0; i < fileNum;i++){
				String curTempDir = tempDir+"/"+i+".txt";
				FileWriter fw = new FileWriter(curTempDir,false);
				fw.write(contents[i]);
				fw.close();
			}
		}
		

			System.out.println("home ...");


			
	}

	private static void getContent() throws IOException {
		System.out.println("Please indicate the file number of the date you wish to look up ");
		String input2 = in.readLine();
		List<String> argsInput2 = Arrays.asList(input2.split("\\s+"));
		int index = (int) Float.parseFloat(argsInput2.get(0));
		System.out.println("Content for message "+index+" = "+contents[index]);		
		System.out.println("home ...");
	}

	private static void getMID() throws IOException {
		System.out.println("Please indicate the file number of the date you wish to look up ");
		String input2 = in.readLine();
		List<String> argsInput2 = Arrays.asList(input2.split("\\s+"));
		int index = (int) Float.parseFloat(argsInput2.get(0));		
		System.out.println("MID["+index+"] = "+msgIds[index]);		
		System.out.println("home ...");
	}

	private static void getDate() throws IOException {
		System.out.println("Please indicate the file number of the date you wish to look up ");
		String input2 = in.readLine();
		List<String> argsInput2 = Arrays.asList(input2.split("\\s+"));
		int index = (int) Float.parseFloat(argsInput2.get(0));
		System.out.println("date["+index+"] = "+dates[index]);		
		System.out.println("home ...");
	}

	private static void getAllDates() {
		//dates = clean(dates);
		System.out.println("Date size = "+fileNum);
		System.out.print(fileNum+" dates = [");
				for (int i = 0; i < fileNum;i++){
				//	if (dates[i]!=null){
						System.out.print(dates[i]+"; ");
				//	}
				}
				System.out.println("]");
				System.out.println("home ...");		
	}

	private static void getAllCommsMIDs() throws IOException {
		System.out.println("Please indicate how you want the results to be saved by typing the corresponding number\n 0 = home \n 1= In-line results \n 2 = Save to File ");
		String input2 = in.readLine();
		List<String> argsInput2 = Arrays.asList(input2.split("\\s+"));
		
		if(argsInput2.contains("1")){
			for (String s:msgLink.keySet()){
			if (s.contains("enron")){
				System.out.println(s+" = "+ Arrays.deepToString(msgLink.get(s).toArray()));
			}
			
		}
		}
		
		if(argsInput2.contains("2")){
			for (String s:msgLink.keySet()){
			if (s.contains("enron")){
				System.out.println(s+" = "+ msgLink.get(s).size());
			}
			
		}
		}

			System.out.println("home ...");

	}

	private static void processFiles() throws IOException {
		if(filesProcessed==false){
			File[] files = curDirectory.listFiles();
			fileNum = 0;
			
			processFiles(files);

		filesProcessed = true;}
		else{
			System.out.println("Already sorted the files before. Are you sure you wish to reprocess the files?");
			String input2 = in.readLine();
			List<String> argsInput2 = Arrays.asList(input2.split("\\s+"));
			if(argsInput2.contains("yes")){
				//files2 = new ArrayList<File>();		
				msgIds = new String[totalMessages];
				topics = new String[totalMessages];
				dates = new String[totalMessages];
				contents = new String[totalMessages];
				comms = new HashMap<String, List<String>>();
				msgLink = new HashMap<String, List<String>>();
				fileNum = 0;
				File[] files = curDirectory.listFiles();
				processFiles(files);
			}
		}
		
		System.out.println("Processed "+fileNum+ " files");
		System.out.println("home ...");
	}

	private static void contacts() throws IOException {
		System.out.println("Please indicate how you want the results to be saved by typing the corresponding number\n 0 = home \n 1= Email communications in-line results \n 2 = Email communications save to File \n 3 = Contact names in-line \n 4 = Contact names to file");
		String input2 = in.readLine();
		List<String> argsInput2 = Arrays.asList(input2.split("\\s+"));
		
		if(argsInput2.contains("1")){
			for (String s:comms.keySet()){
				if (s.contains("enron")){

					System.out.print(comms.get(s).size()+ " Emails for "+s+" = [");
					List<String> recipients =  comms.get(s);
					for (String r : recipients){
						System.out.print(r+", ");
						}
					
					System.out.print("]");
					System.out.print("\n");
					}
				}
			}
		
		if(argsInput2.contains("2")){
			System.out.println("total number of senders = "+comms.size());
			System.out.println("Please enter a directory and file name to save the contacts to, or leave blank for the default directory");
			String input3 = in.readLine();
			List<String> argsInput3 = Arrays.asList(input3.split("\\s+"));
			String tempDir = "/Users/DodionTwo/Documents/EnronEmails/contactCommunications.txt";
			if (!(argsInput3.get(0).length()==0)){
				tempDir = argsInput3.get(0);
			}
		
			FileWriter fw = new FileWriter(tempDir,false);
			fw.flush();
			fw.write("Enron Email Communications \n \n");
			for (String s:comms.keySet()){
				if (s.contains("enron")){
					fw.append(comms.get(s).size()+ " Emails for "+s+" = [");
					List<String> recipients =  comms.get(s);
					for (String r : recipients){
						if(r.contains("enron")){
							fw.append(r+", ");
						}
					}
					fw.append("]");
					fw.append("\n");
				}
				
			}
			fw.close();
		}
		if (argsInput2.contains("3")){
			System.out.println(comms.keySet());
		}
		if (argsInput2.contains("4")){
			System.out.println("Please enter a directory and file name to save the contacts to, or leave blank for the default directory");
			String input3 = in.readLine();
			List<String> argsInput3 = Arrays.asList(input3.split("\\s+"));
			String tempDir = "/Users/DodionTwo/Documents/EnronEmails/ContactNames.txt";
			if (!(argsInput3.get(0).length()==0)){
				tempDir = argsInput3.get(0);
			}

			printCollection(comms.keySet(), tempDir,"Enron Email Contacts");
			
		}
		
		System.out.println("home ...");
	}

	// If we need to do remove null entries
/*	private static String[] clean(final String[] v) {
	    List<String> list = new ArrayList<String>(Arrays.asList(v));
	    list.removeAll(Collections.singleton(null));
	    return list.toArray(new String[list.size()]);
	} */
	
	 private static void printCollection(Collection<String> c, String tempDir, String FirstSentence) throws IOException{
		 FileWriter fw = new FileWriter(tempDir,false);
			fw.flush();   	      
			fw.write(FirstSentence+" \n \n");
			fw.write("[");
			Iterator<String> it = c.iterator();
	        while ( it.hasNext() ){
	        	
	            fw.write(it.next());
	            if (it.hasNext()){
	            	fw.write(", ");
	            }
	        }
	      fw.write("]");
	      fw.close();
	    }
		

	static <K,V extends Comparable<? super V>>	SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() {
	            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	            	int res = e2.getValue().compareTo(e1.getValue());
                    return res != 0 ? res : 1;
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}
	
	}


