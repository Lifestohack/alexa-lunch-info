package DBLunchInfo;

import java.util.HashSet;
import java.util.Set;
import DBLunchInfo.DBSpeechlet;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public class DBSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

	private static final Set<String> supportedApplicationIds;
	static {
		/*
		 * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit"
		 * the relevant Alexa Skill and put the relevant Application Ids in this Set.
		 */
		supportedApplicationIds = new HashSet<String>();
		supportedApplicationIds.add("amzn1.ask.skill.c1b1895d-73e2-4590-9ce2-d01bfbe9b247");
		
	}

	public DBSpeechletRequestStreamHandler() {
		super(new DBSpeechlet(), supportedApplicationIds);
	}

}
