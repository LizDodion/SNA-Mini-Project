import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
//import java.util.Arrays;

public class ReadFile {
	private String path;
	private int contentValue;
	private int recipientsStart = 0;
	private int recipientsEnd = 0;
	private int cCrecipientsStart = 0;
	private int cCrecipientsEnd = 0;
	private int bcCrecipientsStart = 0;
	private int bcCrecipientsEnd = 0;
	private String[] recipients;
	private String[] cCrecipients;
	private String[] bcCrecipients;
	public String[] textData;
	
	public ReadFile(String file_path){
		path = file_path;
	}
	
	int readLines() throws IOException{
		FileReader file_to_read = new FileReader(path);
		BufferedReader bf = new BufferedReader (file_to_read);
		
//		String line;
		int numberOfLines=0;
		while (( bf.readLine()) != null){
			numberOfLines++;
		}
		bf.close();
		return numberOfLines;
	}
	
	public String[] OpenFile() throws IOException{
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);
		int numberOfLines = readLines();
		textData = new String[numberOfLines];
		
		int i = 0;
		for (i=0;i < numberOfLines;i++){
			textData[i] = textReader.readLine();
			if(textData[i].contains("X-FileName:")){
				contentValue = i;
			}
			if(textData[i].contains("To:")&&i < 4){
				recipientsStart = i;
			//	recipients = textData[i].substring(3).split(",");
				
			}
			if(textData[i].contains("Cc:")&&i < 7){
				cCrecipientsStart = i;
				//cCrecipients = textData[i].substring(3).split(",");
			}
			if(textData[i].contains("Bcc:")&&bcCrecipientsStart==0){
				bcCrecipientsStart = i;
			//	System.out.println("Contains Bcc");
				//cCrecipients = textData[i].substring(3).split(",");
			}
			if(textData[i].contains("Subject:")&&recipientsEnd ==0){
				recipientsEnd = i;
			}
			if(textData[i].contains("Mime-Version:")&&cCrecipientsEnd ==0){
				cCrecipientsEnd = i;
			}
			if(textData[i].contains("X-From:")&&bcCrecipientsEnd ==0){
				bcCrecipientsEnd = i;
			}
		}
		textReader.close();
		return textData;
	}
	
	public int mainContentStart(){
		return contentValue;
	}
	
	public int subjectLine(){
		return recipientsEnd;
	}
	
	public String[] recipientsList(){
		int i = recipientsStart;
		int j = recipientsEnd;
		if(i ==0){
			recipients = new String[1];
			recipients[0] = " Undisclosed Recipients";
			return recipients;
		}
		else{recipients = textData[i].substring(3).split(",");
		
		for (int k = i+1;k < j;k++){
			String[] recipTemp = textData[k].split(",");
			String[] tmp1 = new String[recipients.length+recipTemp.length];
			System.arraycopy(recipTemp, 0, tmp1, 0, recipTemp.length);
			System.arraycopy(recipients, 0, tmp1, recipTemp.length, recipients.length);
			recipients = tmp1;
		}
		return recipients;}
	}

	public String[] cCrecipients(){
		
		int i = cCrecipientsStart;
		int j = cCrecipientsEnd;
		
		if(i ==0||j==0){
			return cCrecipients;
		}
		else{//System.out.println(path+" for cC, i = "+i+", j = "+j);
			cCrecipients = textData[i].substring(3).split(",");
				for (int k = i+1;k < j;k++){
			String[] recipTemp = textData[k].split(",");
			String[] tmp1 = new String[cCrecipients.length+recipTemp.length];
			System.arraycopy(recipTemp, 0, tmp1, 0, recipTemp.length);
			System.arraycopy(cCrecipients, 0, tmp1, recipTemp.length, cCrecipients.length);
			cCrecipients = tmp1;
		}
		return cCrecipients;}
	}
	public String[] bcCrecipients(){
		int i = bcCrecipientsStart;
		int j = bcCrecipientsEnd;
		
		if(i == 0||j==0){
			return bcCrecipients;
		}
		else{//System.out.println(path+" for bcC, i = "+i+", j = "+j);
			bcCrecipients = textData[i].substring(3).split(",");
				for (int k = i+1;k < j;k++){
			String[] recipTemp = textData[k].split(",");
			String[] tmp1 = new String[bcCrecipients.length+recipTemp.length];
			System.arraycopy(recipTemp, 0, tmp1, 0, recipTemp.length);
			System.arraycopy(bcCrecipients, 0, tmp1, recipTemp.length, bcCrecipients.length);
			bcCrecipients = tmp1;
		}
		return bcCrecipients;}
	}
}
